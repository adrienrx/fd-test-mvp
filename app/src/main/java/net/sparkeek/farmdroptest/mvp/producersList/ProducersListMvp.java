package net.sparkeek.farmdroptest.mvp.producersList;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;

import net.sparkeek.farmdroptest.persistence.entities.ProducersEntity;

import java.io.Serializable;
import java.util.List;

import icepick.Icepick;
import icepick.Icicle;

/**
 * Created by Adrien on 22/11/2016.
 */

public interface ProducersListMvp {

    //region model
    final class Model implements Serializable{
        public final List<ProducersEntity> producers;

        public Model(final List<ProducersEntity> ploProducers){
            producers = ploProducers;
        }
    }
    //endregion

    //region View
    interface View extends MvpLceView<Model>{
        void showEmpty();
    }
    //endregion

    //region Presenter
    interface Presenter extends MvpPresenter<View>{
        void loadProducers(final boolean pbPullToRefresh);
    }
    //endregion

    //region Viewstate
    final class ViewState implements RestorableViewState<View>{

        //region data to persist
        @Icicle
        public Serializable data;
        //endregion

        //region Viewstate
        @Override
        public void apply(final ProducersListMvp.View poView, final boolean pbRetained){
            if (data instanceof ProducersListMvp.Model){
                final ProducersListMvp.Model loData = (ProducersListMvp.Model) data;
                poView.setData(loData);
                if(loData.producers == null || loData.producers.isEmpty()){
                    poView.showEmpty();
                } else {
                    poView.showContent();
                }
            } else {
                poView.showError(null, false);
            }
        }

        //region RestorableViewState
        @Override
        public void saveInstanceState(@NonNull final Bundle poOut){
            Icepick.saveInstanceState(this, poOut);
        }

        @Override
        public RestorableViewState<ProducersListMvp.View> restoreInstanceState(final Bundle poIn){
            if(poIn == null){
                return null;
            }
            Icepick.restoreInstanceState(this, poIn);
            return this;
        }
        //endregion

    }
    //endregion




}
