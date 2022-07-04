package com.sigmundgranaas.forgero.registry.impl;

import com.google.common.collect.ImmutableList;
import com.sigmundgranaas.forgero.core.data.ForgeroDataResource;
import com.sigmundgranaas.forgero.core.registry.impl.ConcurrentResourceRegistry;
import com.sigmundgranaas.forgero.item.ForgeroItem;
import com.sigmundgranaas.forgero.registry.RegistryHandler;

import java.util.Map;

public abstract class ConcurrentItemResourceRegistry<T extends ForgeroItem<?>, R extends ForgeroDataResource> extends ConcurrentResourceRegistry<T> {
    protected ConcurrentItemResourceRegistry(Map<String, T> resources) {
        super(resources);
    }

    public void register(RegistryHandler helper) {
        getResourcesAsList().forEach(resource -> helper.register(resource.getItem(), resource.getIdentifier()));
    }

    @Override
    public ImmutableList<T> getResourcesAsList() {
        return super.getResourcesAsList().stream().sorted().collect(ImmutableList.toImmutableList());
    }
}
