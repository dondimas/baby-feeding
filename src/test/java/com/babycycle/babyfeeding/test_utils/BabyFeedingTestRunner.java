package com.babycycle.babyfeeding.test_utils;

import android.app.Application;
import com.babycycle.babyfeeding.BabyFeedingApplication;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricConfig;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import org.junit.runners.model.InitializationError;
import roboguice.RoboGuice;
import roboguice.inject.ContextScope;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/1/13
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class BabyFeedingTestRunner extends RobolectricTestRunner {

    /**
     * Instantiates a new colleagues test runner.
     *
     * @param testClass
     *            the test class
     * @throws org.junit.runners.model.InitializationError
     *             the initialization error
     */
    public BabyFeedingTestRunner(final Class<?> testClass) throws InitializationError {
        super(testClass,getConfiguration());
    }

    private static RobolectricConfig getConfiguration() {
        File baseEventsProjectDirectory = new File(System.getProperty("user.dir"));
        try {
            Properties props = new Properties();
            InputStream configuration = BabyFeedingTestRunner.class.getClassLoader().getResourceAsStream("test.properties");
            if( configuration != null ){
                props.load(configuration);
                String basedir = (String) props.get("project.basedir");
                File possible = new File(basedir);
                if( possible.exists() && possible.isDirectory()){
                    baseEventsProjectDirectory = possible;
                }
            }
        } catch (IOException e) {
            // ignore, use default
        }
        RobolectricConfig configuration = new RobolectricConfig(baseEventsProjectDirectory);
        return configuration;
    }

    @Override
    public void prepareTest(final Object test) {

        Application application = Robolectric.application;
        RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(application)).with(new FakeModules()));

        final Injector injector = RoboGuice.getInjector(application);
        ContextScope scope = injector.getInstance(ContextScope.class);
        scope.enter(application);
        injector.injectMembers(test);

    }

    @Override
    protected void bindShadowClasses() {
        Robolectric.bindShadowClass(ShadowActivity.class);
    }
}
