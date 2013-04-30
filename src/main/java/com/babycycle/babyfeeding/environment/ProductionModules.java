package com.babycycle.babyfeeding.environment;

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

        overrideBinding();
    }

    protected void overrideBinding() {
    }
}
