package net.sparkeek.farmdroptest.tests.mock;

import net.sparkeek.farmdroptest.ApplicationAndroidStarter;
import net.sparkeek.farmdroptest.di.modules.ModuleContext;

import net.sparkeek.farmdroptest.DaggerApplicationAndroidStarterComponent;
import net.sparkeek.farmdroptest.di.modules.ModuleAsync;
import net.sparkeek.farmdroptest.di.modules.ModuleBus;
import net.sparkeek.farmdroptest.di.modules.ModuleEnvironment;

public class MockApplication extends ApplicationAndroidStarter {

    //region Fields
    private ModuleBus mModuleBus;
    private MockModuleRest mModuleRest;
    private ModuleEnvironment mModuleEnvironment;
    //endregion

    //region Singleton
    protected static MockApplication sSharedMockApplication;

    public static MockApplication sharedMockApplication() {
        return sSharedMockApplication;
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate() {
        super.onCreate();
        sSharedMockApplication = this;
    }
    //endregion

    //region Overridden method
    @Override
    protected void buildComponent() {
        mModuleBus = new ModuleBus();
        mModuleRest = new MockModuleRest();
        mModuleEnvironment = new MockModuleEnvironment();

        mComponentApplication = DaggerApplicationAndroidStarterComponent.builder()
                .moduleAsync(new ModuleAsync())
                .moduleBus(mModuleBus)
                .moduleContext(new ModuleContext(getApplicationContext()))
                .moduleDatabase(new MockModuleDatabase())
                .moduleEnvironment(mModuleEnvironment)
                .moduleRest(mModuleRest)
                .build();
    }
    //endregion

    //region Getters
    public MockModuleRest getModuleRest() {
        return mModuleRest;
    }

    public ModuleBus getModuleBus() {
        return mModuleBus;
    }

    public ModuleEnvironment getModuleEnvironment() {
        return mModuleEnvironment;
    }
    //endregion
}
