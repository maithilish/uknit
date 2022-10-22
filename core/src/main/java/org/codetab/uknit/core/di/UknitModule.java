package org.codetab.uknit.core.di;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.tree.TreeFactory;
import org.codetab.uknit.core.zap.make.model.ExpVar;
import org.codetab.uknit.core.zap.make.model.Insert;
import org.codetab.uknit.core.zap.make.model.Invoke;
import org.codetab.uknit.core.zap.make.model.Patch;
import org.codetab.uknit.core.zap.make.model.Verify;
import org.codetab.uknit.core.zap.make.model.When;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class UknitModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();

        install(new FactoryModuleBuilder().implement(Object.class, Object.class)
                .build(org.codetab.uknit.core.zap.make.model.ModelFactory.class));
        install(new FactoryModuleBuilder().implement(Object.class, Object.class)
                .build(ModelFactory.class));
        install(new FactoryModuleBuilder().implement(Object.class, Object.class)
                .build(TreeFactory.class));
    }

    @Provides
    public List<Pack> newPacks() {
        return new ArrayList<>();
    }

    // FIXME - pack branch remove any old items
    @Provides
    public List<IVar> newVars() {
        return new ArrayList<>();
    }

    @Provides
    public List<Invoke> newMethodInvokes() {
        return new ArrayList<>();
    }

    @Provides
    public List<ExpVar> newExpVar() {
        return new ArrayList<>();
    }

    @Provides
    public List<Patch> newPatches() {
        return new ArrayList<>();
    }

    @Provides
    public List<When> newWhens() {
        return new ArrayList<>();
    }

    @Provides
    public List<Verify> newVerify() {
        return new ArrayList<>();
    }

    @Provides
    public List<String> newStringList() {
        return new ArrayList<>();
    }

    @Provides
    public List<Insert> newInsert() {
        return new ArrayList<>();
    }

}
