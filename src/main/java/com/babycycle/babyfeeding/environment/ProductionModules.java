package com.babycycle.babyfeeding.environment;

import com.babycycle.babyfeeding.ui.navigation.ITabHistoryContentStack;
import com.babycycle.babyfeeding.ui.navigation.TabHistoryContentStack;
import com.google.inject.AbstractModule;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/29/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProductionModules extends AbstractModule {

    public ProductionModules() {
        super();
    }

    @Override
    protected void configure() {
//        bind(IPersistanceFacade.class).to(PersistenceFacade.class);
        bind(ITabHistoryContentStack.class).to(TabHistoryContentStack.class);
        overrideBinding();
    }

    protected void overrideBinding() {
    }
}
