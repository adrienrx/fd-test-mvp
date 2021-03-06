package net.sparkeek.farmdroptest.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.squareup.picasso.Picasso;

import net.sparkeek.farmdroptest.IEnvironment;
import net.sparkeek.farmdroptest.rest.FarmDropService;
import net.sparkeek.farmdroptest.rest.errorHandling.ErrorHandlingExecutorCallAdapterFactory;
import net.sparkeek.farmdroptest.rest.queries.QueryFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.palaima.debugdrawer.picasso.PicassoModule;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@Module
public class ModuleRest {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(@NonNull final IEnvironment poEnvironment) {
        final HttpLoggingInterceptor loHttpLoggingInterceptor = new HttpLoggingInterceptor();
        loHttpLoggingInterceptor.setLevel(poEnvironment.getHttpLoggingInterceptorLevel());
        return new OkHttpClient.Builder()
                .addInterceptor(loHttpLoggingInterceptor)
                .build();
    }


    @Provides
    @Singleton
    public FarmDropService provideFarmDropService(@NonNull final OkHttpClient poOkHttpClient){
        final Retrofit loRetrofit = new Retrofit.Builder()
                .baseUrl("https://fd-v5-api-release.herokuapp.com")
                .client(poOkHttpClient)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addCallAdapterFactory(new ErrorHandlingExecutorCallAdapterFactory(new ErrorHandlingExecutorCallAdapterFactory.MainThreadExecutor()))
                .build();
        return loRetrofit.create(FarmDropService.class);
    }

    @Provides
    @Singleton
    public QueryFactory provideQueryFactory() {
        return new QueryFactory();
    }

    @Provides
    @Singleton
    public Merlin provideMerlin(@NonNull final Context poContext) {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .withLogging(true)
                .build(poContext);
    }

    @Provides
    @Singleton
    public MerlinsBeard provideMerlinsBeard(@NonNull final Context poContext) {
        return MerlinsBeard.from(poContext);
    }

    @Provides
    @Singleton
    public Picasso providePicasso(@NonNull final Context poContext) {
        final Picasso loPicasso = Picasso.with(poContext);
        return loPicasso;
    }

    @Provides
    @Singleton
    public PicassoModule providePicassoModule(@NonNull final Picasso poPicasso) {
        return new PicassoModule(poPicasso);
    }
}
