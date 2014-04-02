package com.akka.system;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.util.List;

/**
 * let's give it some guice power
 * Created by kobi on 4/3/14.
 */
public class IocInitializer {
    private Injector  injector;
    private List<Module> modules;

    private static IocInitializer instance = new IocInitializer();
//
    public static IocInitializer getInstance(){
        return instance;
    }

    public Injector getInjector(){
        if (this.injector != null){
            return this.injector;
        }else{
            this.injector = Guice.createInjector(modules);
        }
        return this.injector;
    }

    public void setModules(List<Module> modules){
        this.modules = modules;
    }
}
