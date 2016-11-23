package net.sparkeek.farmdroptest.mvp.producersList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import net.sparkeek.farmdroptest.ApplicationAndroidStarter;
import net.sparkeek.farmdroptest.R;
import net.sparkeek.farmdroptest.persistence.entities.ProducersEntity;

import javax.inject.Inject;

import autodagger.AutoInjector;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableFrameLayout;

/**
 * Created by Adrien on 22/11/2016.
 */
@AutoInjector(ApplicationAndroidStarter.class)
public class CellProducers extends BindableFrameLayout<ProducersEntity> {
    public static final int ROW_PRESSED = 0;
    //region Injected members
    @Inject
    Picasso mPicasso;
    //endregion

    //region Injected views
    @Bind(R.id.CellProducers_TextView)
    TextView mTextView;
    @Bind(R.id.CellProducers_ImageView_Avatar)
    ImageView mImageViewAvatar;
    //endregion

    //region Constructor
    public CellProducers(@NonNull final Context poContext) {
        super(poContext);
        ApplicationAndroidStarter.sharedApplication().componentApplication().inject(this);
    }
    //endregion

    @Override
    public int getLayoutId() {
        return R.layout.cell_producers;
    }

    @Override
    public void bind(ProducersEntity poProducers) {
        mTextView.setText(poProducers.getName());

        final RequestCreator lorequest = mPicasso.load(poProducers.getImages());
        if(lorequest != null) {
            lorequest.placeholder(R.drawable.git_icon)
                    .resize(100,100)
                    .error(R.drawable.git_icon)
                    .into(mImageViewAvatar);
        }
        setOnClickListener((final View poView) -> notifyItemAction(ROW_PRESSED));
    }

    @Override
    public void onViewInflated() {
        super.onViewInflated();
        ButterKnife.bind(this);
    }
}
