package com.sigmundgranaas.forgero.core.material.material.implementation;

import com.sigmundgranaas.forgero.core.data.factory.PropertyBuilder;
import com.sigmundgranaas.forgero.core.data.v1.pojo.MaterialPojo;
import com.sigmundgranaas.forgero.core.material.material.AbstractForgeroMaterial;
import com.sigmundgranaas.forgero.core.property.Property;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleSecondaryMaterialImpl extends AbstractForgeroMaterial implements SimpleSecondaryMaterial {

    private final List<Property> properties;

    public SimpleSecondaryMaterialImpl(MaterialPojo material) {
        super(material);

        this.properties = PropertyBuilder.createPropertyListFromPOJO(material.secondary.properties);

    }


    @Override
    public List<Property> getSecondaryProperties() {
        return Stream.of(super.getProperties(), properties)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getValidPlacements() {
        return Set.of("MATERIAL");
    }
}
