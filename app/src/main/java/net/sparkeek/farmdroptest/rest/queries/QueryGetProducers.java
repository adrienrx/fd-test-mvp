package net.sparkeek.farmdroptest.rest.queries;

import net.sparkeek.farmdroptest.ApplicationAndroidStarter;
import net.sparkeek.farmdroptest.bus.event.AbstractEventQueryDidFinish;
import net.sparkeek.farmdroptest.persistence.entities.ProducersEntity;
import net.sparkeek.farmdroptest.rest.FarmDropService;
import net.sparkeek.farmdroptest.rest.dto.DTOProducers;
import net.sparkeek.farmdroptest.rest.dto.DTOProducersResponseItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import autodagger.AutoInjector;
import io.requery.Persistable;
import io.requery.rx.SingleEntityStore;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Adrien on 22/11/2016.
 */
@AutoInjector(ApplicationAndroidStarter.class)
public class QueryGetProducers extends AbstractQuery{

    //region injected
     @Inject
     transient FarmDropService farmDropService;
    @Inject
    transient EventBus eventBus;
    @Inject
    transient SingleEntityStore<Persistable> dataStore;
    //endregion

    //region fields
    public final boolean pullToRefresh;
    public final String page;
    transient public DTOProducersResponseItem results;
    //endregion

    //region constructor > super
    protected QueryGetProducers(final String psPage, final boolean pbPullToRefresh) {
        super(Priority.MEDIUM);
        page = psPage;
        pullToRefresh = pbPullToRefresh;
    }
    //endregion


    @Override
    public void inject() {
        ApplicationAndroidStarter.sharedApplication().componentApplication().inject(this);
    }

    @Override
    protected void execute() throws Exception {
        inject();

        final Call<DTOProducersResponseItem> loCall = farmDropService.listProducers(page);
        final Response<DTOProducersResponseItem> loExecute = loCall.execute();
        results = loExecute.body();

        //TODO : Implement HASH verification
        final int liDeleted = dataStore.delete(ProducersEntity.class).get().value();


        final ArrayList<ProducersEntity> lloEntities = new ArrayList<>();
            for(final DTOProducers loDTOProducers : results.response){
                final ProducersEntity loProducers = new ProducersEntity();
                loProducers.setName(loDTOProducers.name);
                loProducers.setDescription(loDTOProducers.description);
                loProducers.setShort_Description(loDTOProducers.shortDescription);
                loProducers.setImages(loDTOProducers.images.get(0).path);
                lloEntities.add(loProducers);
            }

        dataStore.insert(lloEntities).toBlocking().value();
    }

    @Override
    protected void postEventQueryFinished() {
        final EventQueryGetProducersFinish loEvent = new EventQueryGetProducersFinish(this, mSuccess, mErrorType, mThrowable, pullToRefresh, results);
        eventBus.post(loEvent);
    }

    @Override
    public void postEventQueryFinishedNoNetwork() {
        final EventQueryGetProducersFinish loEvent = new EventQueryGetProducersFinish(this, false, AbstractEventQueryDidFinish.ErrorType.NETWORK_UNREACHABLE, null, pullToRefresh, null);
        eventBus.post(loEvent);
    }

    public static final class EventQueryGetProducersFinish extends AbstractEventQueryDidFinish<QueryGetProducers> {
        public final boolean pullToRefresh;
        public final DTOProducersResponseItem results;
        public EventQueryGetProducersFinish(final QueryGetProducers poQuery, final boolean pbSucess, final ErrorType poErrorType, final Throwable poThrowable, final boolean pbPullToRefresh, final DTOProducersResponseItem ploResults) {
            super(poQuery, pbSucess, poErrorType, poThrowable);
            pullToRefresh = pbPullToRefresh;
            results = ploResults;
        }
    }
}
