package net.sparkeek.farmdroptest.mvp.producersList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hannesdorfmann.mosby.conductor.viewstate.MvpViewStateController;

import net.sparkeek.farmdroptest.R;
import net.sparkeek.farmdroptest.mvp.repoList.RepoListMvp;
import net.sparkeek.farmdroptest.persistence.entities.Producers;
import net.sparkeek.farmdroptest.persistence.entities.ProducersEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icepick;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;
import pl.aprilapps.switcher.Switcher;

/**
 * Created by Adrien on 22/11/2016.
 */

public class ControllerProducersList
        extends MvpViewStateController<ProducersListMvp.View, ProducersListMvp.Presenter, ProducersListMvp.ViewState>
        implements ProducersListMvp.View, ViewEventListener<Producers>, SwipeRefreshLayout.OnRefreshListener{

    //region inject views
    @Bind(R.id.ControllerProducersList_ProgressBar_Loading)
    ProgressBar mProgressBarLoading;
    @Bind(R.id.ControllerProducersList_RecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.ControllerProducersList_SwipeRefreshLayout_Empty)
    SwipeRefreshLayout mSwipeRefreshLayoutEmpty;
    @Bind(R.id.ControllerProducersList_SwipeRefreshLayout_Error)
    SwipeRefreshLayout mSwipeRefreshLayoutError;
    @Bind(R.id.ControllerProducersList_SwipeRefreshLayout_Content)
    SwipeRefreshLayout mSwipeRefreshLayoutContent;
    @Bind({
            R.id.ControllerProducersList_SwipeRefreshLayout_Empty,
            R.id.ControllerProducersList_SwipeRefreshLayout_Error,
            R.id.ControllerProducersList_SwipeRefreshLayout_Content
    })
    List<SwipeRefreshLayout> mSwipeRefreshLayouts;
    //endregion

    static final ButterKnife.Setter<SwipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener> SET_LISTENER =
            (@NonNull final SwipeRefreshLayout poView, @NonNull final SwipeRefreshLayout.OnRefreshListener poListener, final int piIndex)
                ->
                    poView.setOnRefreshListener(poListener);

    static final ButterKnife.Action<SwipeRefreshLayout> STOP_REFRESHING =
            (@NonNull final SwipeRefreshLayout poView, final int piIndex)
            ->
                    poView.setRefreshing(false);

    private Switcher mSwitcher;

    public ControllerProducersList(){

    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater poInflater, @NonNull ViewGroup poContainer) {
        final View loView = poInflater.inflate(R.layout.controller_producers_list, poContainer, false);
        ButterKnife.bind(this, loView);

        ButterKnife.apply(mSwipeRefreshLayouts, SET_LISTENER, this);

        mSwitcher = new Switcher.Builder()
                .withEmptyView(mSwipeRefreshLayoutEmpty)
                .withContentView(mSwipeRefreshLayoutContent)
                .withProgressView(mProgressBarLoading)
                .withErrorView(mSwipeRefreshLayoutError)
                .build();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return loView;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull final Bundle poSavedInstanceState) {
        super.onRestoreInstanceState(poSavedInstanceState);
        Icepick.restoreInstanceState(this, poSavedInstanceState);
    }

    @Override
    protected void onRestoreViewState(@NonNull final View poView, @NonNull final Bundle poSavedViewState) {
        super.onRestoreViewState(poView, poSavedViewState);
        Icepick.restoreInstanceState(this, poSavedViewState);
    }

    @Override
    protected void onAttach(@NonNull final View poView) { super.onAttach(poView);}

    @Override
    protected void onDestroyView(final View poView) {
        ButterKnife.unbind(this);
        super.onDestroyView(poView);
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle poOutState) {
        super.onSaveInstanceState(poOutState);
        Icepick.saveInstanceState(this, poOutState);
    }


    @Override
    public void onViewEvent(final int piActionID, final Producers producers, final int poPosition, final View poView) {
        //TODO : click to nav
    }



    @NonNull
    @Override
    public ProducersListMvp.ViewState createViewState() {
        return new ProducersListMvp.ViewState();
    }

    @Override
    public void onViewStateInstanceRestored(boolean instanceStateRetained) {

    }

    @Override
    public void onNewViewStateInstance() {
        loadData(false);
    }

    @NonNull
    @Override
    public ProducersListMvp.Presenter createPresenter() {
        return new PresenterProducersList();
    }

    @Override
    public void showEmpty() {
        ButterKnife.apply(mSwipeRefreshLayouts, STOP_REFRESHING);
        mSwitcher.showEmptyView();
    }

    @Override
    public void showLoading(final boolean pbPullToRefresh) {
        if(!pbPullToRefresh) {
            mSwitcher.showProgressView();
        }
    }

    @Override
    public void showContent() {
        ButterKnife.apply(mSwipeRefreshLayouts, STOP_REFRESHING);
        mSwitcher.showContentView();
    }

    @Override
    public void showError(Throwable throwable, boolean b) {
        ButterKnife.apply(mSwipeRefreshLayouts, STOP_REFRESHING);
        mSwitcher.showErrorView();
    }

    @Override
    public void setData(ProducersListMvp.Model poData) {
        viewState.data = poData;

        SmartAdapter.items(poData.producers)
                .map(ProducersEntity.class, CellProducers.class)
                .listener(ControllerProducersList.this)
                .into(mRecyclerView);
    }

    @Override
    public void loadData(final boolean pbPullToRefresh) {
        getPresenter().loadProducers(pbPullToRefresh);
    }


    @Override
    public void onRefresh() {
        loadData(true);
    }
}
