package com.akka;

import com.akka.system.IocInitializer;
import com.akka.system.SystemModule;
import com.google.inject.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kobi on 3/31/14.
 */
public class AkkaRunner {

    public static void main(String[] args) {
        List<Module> moduleList = new ArrayList<Module>(1);
        moduleList.add(new SystemModule());
        IocInitializer.getInstance().setModules(moduleList);
        new AkkaServer();
    }

}
