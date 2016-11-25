package net.sparkeek.farmdroptest.mvp.producersDetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.conductor.viewstate.MvpViewStateController;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import net.sparkeek.farmdroptest.R;
import net.sparkeek.farmdroptest.mvp.producersList.ControllerProducersList;
import net.sparkeek.farmdroptest.persistence.entities.Producers;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import pl.aprilapps.switcher.Switcher;

/**
 * Created by Adrien on 23/11/2016.
 */

public class ControllerProducerDetail
        extends MvpViewStateController<ProducerDetailMvp.View, PresenterProducerDetail, ProducerDetailMvp.ViewState>
        implements ProducerDetailMvp.View{

    Bitmap mProducerBitmap;

    @Inject
    Context mContext;

    //region Injected Views
    @Bind(R.id.ControllerProducerDetail_TextView_Description)
    TextView mTextViewDescription;
    @Bind(R.id.ControllerProducerDetail_Name)
    TextView mTextViewName;
    @Bind(R.id.ControllerProducerDetail_TextView_Url)
    TextView mTextViewUrl;
    @Bind(R.id.ControllerProducerDetail_TextView_Empty)
    TextView mTextViewEmpty;
    @Bind(R.id.ControllerProducerDetail_TextView_Error)
    TextView mTextViewError;
    @Bind(R.id.ControllerProducerDetail_ProgressBar_Loading)
    ProgressBar mProgressBarLoading;
    @Bind(R.id.ControllerProducerDetail_ImageView)
    ImageView mImageViewProducer;
    @Bind(R.id.ControllerProducerDetail_Location)
    TextView mTextViewLocation;
    @Bind(R.id.ControllerProducerDetail_ContentView)
    RelativeLayout mContentView;
    //endregion

    private final long mProducerId;
    private Switcher mSwitcher;
    //region Default constructor
    public ControllerProducerDetail(final long plProducerId) {
        mProducerId = plProducerId;
    }

    public ControllerProducerDetail() {mProducerId = -1;}

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        final View loView = inflater.inflate(R.layout.controller_producer_detail, container, false);

        ButterKnife.bind(this, loView);

        mSwitcher = new Switcher.Builder()
                .withEmptyView(mTextViewEmpty)
                .withProgressView(mProgressBarLoading)
                .withErrorView(mTextViewError)
                .withContentView(mContentView)
                .build();


        return loView;
    }

    @NonNull
    @Override
    public ProducerDetailMvp.ViewState createViewState() {
        return new ProducerDetailMvp.ViewState();
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
    public PresenterProducerDetail createPresenter() {
        return new PresenterProducerDetail();
    }

    @Override
    public void showEmpty() {
        mSwitcher.showEmptyView();
    }

    @Override
    public void showLoading(boolean pbPullToRefresh) {
        mSwitcher.showProgressView();
    }

    @Override
    public void showContent() {
        mSwitcher.showContentView();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        mSwitcher.showErrorView();
    }

    @Override
    public void setData(ProducerDetailMvp.Model data) {

        configureViewWithProducer(data.producer);
    }

    @Override
    public void loadData(boolean pbPullToRefresh) {
        if(mProducerId == -1){
            mSwitcher.showErrorView();
        } else {
            getPresenter().loadProducer(mProducerId, pbPullToRefresh);
        }
    }

    //region specific
    @DebugLog
    private void configureViewWithProducer(@NonNull final Producers poProducer){
        mTextViewName.setText(poProducer.getName());
        mTextViewDescription.setText(poProducer.getDescription());
        if(poProducer.getLocation() != null){
            mTextViewLocation.setText(poProducer.getLocation());
        }
        Picasso.with(mContext)
                .load(poProducer.getImages())
                .resize(1024, 768)
                .into(mImageViewProducer);
    }
    //endregion
}
