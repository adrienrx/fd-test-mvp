package net.sparkeek.farmdroptest.mvp.producersDetail;

import android.graphics.Bitmap;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.squareup.picasso.Picasso;

import net.sparkeek.farmdroptest.ApplicationAndroidStarter;
import net.sparkeek.farmdroptest.persistence.entities.Producers;
import net.sparkeek.farmdroptest.persistence.entities.ProducersEntity;

import java.io.IOException;

import javax.inject.Inject;

import autodagger.AutoInjector;
import io.requery.Persistable;
import io.requery.rx.SingleEntityStore;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Adrien on 23/11/2016.
 */
@AutoInjector(ApplicationAndroidStarter.class)
public class PresenterProducerDetail extends MvpBasePresenter<ProducerDetailMvp.View> implements ProducerDetailMvp.Presenter{
    //region Injected Fields
    @Inject
    SingleEntityStore<Persistable> dataStore;
    @Inject
    Picasso mPicasso;
    //endregion

    //region fields
    private Subscription mSubscriptionGetProducer;
    //endregion

    //region Constructor
    public PresenterProducerDetail(){
        ApplicationAndroidStarter.sharedApplication().componentApplication().inject(this);
    }
    //endregion

    //region Overridden method
    @Override
    public void detachView(boolean pbRetainInstance) {
        super.detachView(pbRetainInstance);
        if(!pbRetainInstance) {
            unsubscribe();
        }
    }
    //endregion

    //region Visible
    @Override
    public void loadProducer(long plProducerId, boolean pbPullToRefresh) {
        final ProducerDetailMvp.View loView = getView();
        if (isViewAttached() && loView != null) {
            loView.showLoading(pbPullToRefresh);
        }
        getProducers(plProducerId);
    }

    //region reactive job
    private void getProducers(final long plProducersId) {
        unsubscribe();

        final ProducerDetailMvp.View loview = getView();
        if (loview == null){
            return;
        }

        mSubscriptionGetProducer = dataStore.select(ProducersEntity.class)
                .where(ProducersEntity.BASE_ID.eq(plProducersId))
                .get()
                .toObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onNext
                        (final Producers poProducers) -> {
                            if(isViewAttached()) {
                                loview.setData(new ProducerDetailMvp.Model(poProducers));
                                if(poProducers == null) {
                                    loview.showEmpty();
                                } else {
                                    loview.showContent();
                                }
                            }
                        },
                        //onError
                        (final Throwable poException) -> {
                            if(isViewAttached()) {
                                loview.showError(poException, false);
                            }
                            unsubscribe();
                        },
                        //onCompleted
                        this::unsubscribe
                );
    }
    //endregion

    //region specific unsubscribre
    private void unsubscribe() {
        if(mSubscriptionGetProducer != null && !mSubscriptionGetProducer.isUnsubscribed()) {
            mSubscriptionGetProducer.unsubscribe();
        }
        mSubscriptionGetProducer = null;
    }
}
