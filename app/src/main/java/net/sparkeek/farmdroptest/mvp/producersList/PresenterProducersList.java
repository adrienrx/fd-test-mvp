package net.sparkeek.farmdroptest.mvp.producersList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import net.sparkeek.farmdroptest.ApplicationAndroidStarter;
import net.sparkeek.farmdroptest.persistence.entities.ProducersEntity;
import net.sparkeek.farmdroptest.rest.queries.QueryFactory;
import net.sparkeek.farmdroptest.rest.queries.QueryGetProducers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import javax.inject.Inject;

import autodagger.AutoInjector;
import hugo.weaving.DebugLog;
import io.requery.Persistable;
import io.requery.rx.SingleEntityStore;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Adrien on 22/11/2016.
 */
@AutoInjector(ApplicationAndroidStarter.class)
public class PresenterProducersList extends MvpBasePresenter<ProducersListMvp.View> implements ProducersListMvp.Presenter{

    //region inject fields
    @Inject
    Context context;
    @Inject
    EventBus eventBus;
    @Inject
    SingleEntityStore<Persistable> dataStore;
    @Inject
    QueryFactory queryFactory;
    //endregion

    //region fields
    private Subscription mSubscriptionGetProducers;
    //endregion

    //region Constructor
    public PresenterProducersList(){
        ApplicationAndroidStarter.sharedApplication().componentApplication().inject(this);
    }
    //endregion

    //region methode surchargée
    @Override
    public void attachView(final ProducersListMvp.View poView){
        super.attachView(poView);
        eventBus.register(this);
    }

    public void detachView(final boolean pbRetainInstance){
        super.detachView(pbRetainInstance);

        if(!pbRetainInstance) {
            unsubscribe();
        }

        eventBus.unregister(this);
    }
    //endregion

    //region ProducersListMvp.Presenter
    @Override
    public void loadProducers(boolean pbPullToRefresh, String fullOrNot, String pbPage) {
        startQueryGetProducers(pbPullToRefresh,fullOrNot, pbPage);
    }
    //endregion

    //region Reactive hob
    private void getProducers(final boolean pbPullToRefresh) {
        unsubscribe();

        final ProducersListMvp.View loView = getView();
        if(loView == null){
            return;
        }


        final ArrayList<ProducersEntity> lloProducers = new ArrayList<>();
        mSubscriptionGetProducers = dataStore.select(ProducersEntity.class)
                .get()
                .toObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onNext
                        (final ProducersEntity poProducers) -> lloProducers.add(poProducers),
                        //onError
                        (final Throwable poException) -> {
                            if(isViewAttached()) {
                                loView.showError(poException, pbPullToRefresh);
                            }
                            unsubscribe();
                        },
                        //onCompleted
                        () -> {
                            if(isViewAttached()) {
                                loView.setData(new ProducersListMvp.Model(lloProducers));
                                if(lloProducers.isEmpty()) {
                                    loView.showEmpty();
                                } else {
                                    loView.showContent();
                                }
                            }
                            unsubscribe();
                        }
                );
    }

    //region network job
    private void startQueryGetProducers(final boolean pbPullToRefresh, final String fullOrNot, final String pbPage){
        final ProducersListMvp.View loView = getView();
        if(isViewAttached() && loView != null) {
            loView.showLoading(pbPullToRefresh);
        }
        queryFactory.startQueryGetProducers(context,fullOrNot, pbPage, pbPullToRefresh);
    }
    //endregion

    //region event management
    @DebugLog
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventQueryGetProducers(@NonNull final QueryGetProducers.EventQueryGetProducersFinish poEvent) {
        if(poEvent.success){
            getProducers(poEvent.pullToRefresh);
        } else {
            final ProducersListMvp.View loView = getView();
            if(isViewAttached() && loView != null){
                loView.showError(poEvent.throwable, poEvent.pullToRefresh);
            }
        }
    }
    //endregion

    //region specific
    private void unsubscribe() {
        if(mSubscriptionGetProducers != null && !mSubscriptionGetProducers.isUnsubscribed()) {
            mSubscriptionGetProducers.unsubscribe();
        }
        mSubscriptionGetProducers = null;
    }
    //endregion
}
