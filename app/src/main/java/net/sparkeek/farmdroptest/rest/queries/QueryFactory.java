package net.sparkeek.farmdroptest.rest.queries;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import net.sparkeek.farmdroptest.services.ServiceQueryExecutorIntentBuilder;

@Singleton
public class QueryFactory {
    //region Build methods

    public QueryGetProducers buildQueryProducers(@NonNull final String fullOrNot, final String page,@NonNull final boolean pbPullToRefresh) {
        return new QueryGetProducers(fullOrNot, page, pbPullToRefresh);
    }
    //endregion

    //region Start methods
    public void startQuery(@NonNull final Context poContext, @NonNull final AbstractQuery poQuery) {
        final Intent loIntent = new ServiceQueryExecutorIntentBuilder(poQuery).build(poContext);
        poContext.startService(loIntent);
    }


    public void startQueryGetProducers(@NonNull final Context poContext,@NonNull final String fullOrNot,  @NonNull final String page, final boolean pbPullToRefresh) {
        final QueryGetProducers loQuery = buildQueryProducers(fullOrNot, page, pbPullToRefresh);
        startQuery(poContext, loQuery);
    }
    //endregion
}
