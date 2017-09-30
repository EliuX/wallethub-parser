package com.ef.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public class GuiceBeansConfig {

    public void init()
    {
        Injector injector = Guice.createInjector(
                new JpaPersistModule("request-log")
        );

        initPersistService(injector);
    }

    private void initPersistService(Injector injector) {
        PersistService instance = injector.getInstance(PersistService.class);
        instance.start();
    }
}
