package net.sparkeek.farmdroptest.di.modules;


import net.sparkeek.farmdroptest.BuildConfig;
import net.sparkeek.farmdroptest.IEnvironment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ModuleEnvironment {

    @Provides
    @Singleton
    public IEnvironment provideEnvironment() {
        return BuildConfig.ENVIRONMENT;
    }
}
