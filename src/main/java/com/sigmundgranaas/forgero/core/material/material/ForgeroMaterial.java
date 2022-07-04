package com.sigmundgranaas.forgero.core.material.material;

import com.sigmundgranaas.forgero.core.data.v1.pojo.IngredientPojo;
import com.sigmundgranaas.forgero.core.data.v1.pojo.MaterialPojo;
import com.sigmundgranaas.forgero.core.property.Property;
import com.sigmundgranaas.forgero.core.property.PropertyContainer;
import com.sigmundgranaas.forgero.core.property.Target;
import com.sigmundgranaas.forgero.core.resource.ForgeroResource;
import com.sigmundgranaas.forgero.core.resource.ForgeroResourceType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public interface ForgeroMaterial extends ForgeroResource, PropertyContainer {
    int getRarity();

    @NotNull
    String getResourceName();

    MaterialType getType();

    @NotNull
    List<Property> getProperties();

    IngredientPojo getIngredient();

    @Override
    default ForgeroResourceType getResourceType() {
        return ForgeroResourceType.MATERIAL;
    }

    @Override
    default String getStringIdentifier() {
        return getResourceName();
    }

    @Override
    @NotNull
    default List<Property> getProperties(Target target) {
        return Collections.emptyList();
    }
}
