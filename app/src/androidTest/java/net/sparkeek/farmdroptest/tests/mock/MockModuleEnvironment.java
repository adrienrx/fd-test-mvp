package net.sparkeek.farmdroptest.tests.mock;

import net.sparkeek.farmdroptest.di.modules.ModuleEnvironment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import net.sparkeek.farmdroptest.Environment;

@Module
public class MockModuleEnvironment extends ModuleEnvironment {

    @Provides
    @Singleton
    public Environment provideEnvironment() {
        return Environment.TEST;
    }
}
