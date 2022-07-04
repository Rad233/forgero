package com.sigmundgranaas.forgero.core.material.material;

import com.sigmundgranaas.forgero.core.data.v1.pojo.IngredientPojo;
import com.sigmundgranaas.forgero.core.data.v1.pojo.MaterialPojo;
import com.sigmundgranaas.forgero.core.material.material.implementation.SimpleSecondaryMaterial;
import com.sigmundgranaas.forgero.core.property.Property;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class EmptySecondaryMaterial implements SecondaryMaterial, SimpleSecondaryMaterial {
    @Override
    public int getRarity() {
        return 0;
    }

    @Override
    public @NotNull
    String getResourceName() {
        return "empty";
    }



    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public @NotNull
    List<Property> getProperties() {
        return Collections.emptyList();
    }

    @Override
    public IngredientPojo getIngredient() {
        return new IngredientPojo();
    }

    @Override
    public Set<String> getValidPlacements() {
        return null;
    }
}
