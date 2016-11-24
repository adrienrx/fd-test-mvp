package net.sparkeek.farmdroptest.mvp.producersDetail;

import android.app.backup.RestoreObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;

import net.sparkeek.farmdroptest.mvp.producersList.ProducersListMvp;
import net.sparkeek.farmdroptest.persistence.entities.Producers;

import java.io.Serializable;

import icepick.Icepick;
import icepick.Icicle;

/**
 * Created by Adrien on 23/11/2016.
 */

public interface ProducerDetailMvp {
    //region Model
    final class Model implements Serializable {
        public final Producers producer;

        public Model(final Producers poProducer) {
            producer = poProducer;
        }
        //endregion
    }
        //region View
        interface View extends MvpLceView<Model> {
            void showEmpty();
        }
        //endregion

        //region Presenter
        interface Presenter extends MvpPresenter<View> {
            void loadProducer(final long plProducerId, final boolean pbPullToRefresh);
        }
        //endregion

        //region ViewState
        final class ViewState implements RestorableViewState<View> {

            //region data to restore
            @Icicle
            public Serializable data;
            //endregion

            //region ViewState
            @Override
            public void apply(final View poView, final boolean pbRetained) {
                if(data instanceof Model) {
                    final Model loData = (Model) data;
                    poView.setData(loData);
                    if( loData.producer == null) {
                        poView.showEmpty();
                    } else {
                        poView.showContent();
                    }
                }
            }
            //endregion

            //region RestorableViewState
            @Override
            public void saveInstanceState(@NonNull final Bundle poOut) {
                Icepick.saveInstanceState(this, poOut);
            }

            @Override
            public RestorableViewState<View> restoreInstanceState(final Bundle poIn) {
                if(poIn == null) {
                    return null;
                }
                Icepick.restoreInstanceState(this, poIn);
                return this;
            }

        }
        //endregion
    }
