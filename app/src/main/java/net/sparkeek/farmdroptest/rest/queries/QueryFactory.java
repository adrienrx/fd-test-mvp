package net.sparkeek.farmdroptest.rest.queries;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import net.sparkeek.farmdroptest.services.ServiceQueryExecutorIntentBuilder;

@Singleton
public class QueryFactory {
    //region Build methods
    public QueryGetRepos buildQueryGetRepos(@NonNull final String psUser, final boolean pbPullToRefresh) {
        return new QueryGetRepos(psUser, pbPullToRefresh);
    }

    public QueryGetProducers buildQueryProducers(@NonNull final String page, final boolean pbPullToRefresh) {
        return new QueryGetProducers(page, pbPullToRefresh);
    }
    //endregion

    //region Start methods
    public void startQuery(@NonNull final Context poContext, @NonNull final AbstractQuery poQuery) {
        final Intent loIntent = new ServiceQueryExecutorIntentBuilder(poQuery).build(poContext);
        poContext.startService(loIntent);
    }

    public void startQueryGetRepos(@NonNull final Context poContext, @NonNull final String psUser, final boolean pbPullToRefresh) {
        final QueryGetRepos loQuery = buildQueryGetRepos(psUser, pbPullToRefresh);
        startQuery(poContext, loQuery);
    }

    public void startQueryGetProducers(@NonNull final Context poContext, @NonNull final String page, final boolean pbPullToRefresh) {
        final QueryGetProducers loQuery = buildQueryProducers(page, pbPullToRefresh);
        startQuery(poContext, loQuery);
    }
    //endregion
}
