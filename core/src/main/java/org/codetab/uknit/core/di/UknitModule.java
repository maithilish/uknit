package org.codetab.uknit.core.di;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.make.model.When;
import org.codetab.uknit.core.tree.TreeFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class UknitModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();

        install(new FactoryModuleBuilder().implement(Object.class, Object.class)
                .build(ModelFactory.class));
        install(new FactoryModuleBuilder().implement(Object.class, Object.class)
                .build(TreeFactory.class));
    }

    @Provides
    public List<Pack> createPacks() {
        return new ArrayList<>();
    }

    @Provides
    public List<Patch> createPatches() {
        return new ArrayList<>();
    }

    @Provides
    public List<When> createWhens() {
        return new ArrayList<>();
    }

    @Provides
    public List<Verify> createVerify() {
        return new ArrayList<>();
    }

    @Provides
    public List<String> createStringList() {
        return new ArrayList<>();
    }

}
