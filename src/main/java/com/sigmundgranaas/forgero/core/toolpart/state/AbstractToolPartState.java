package com.sigmundgranaas.forgero.core.toolpart.state;

import com.sigmundgranaas.forgero.core.gem.Gem;
import com.sigmundgranaas.forgero.core.material.material.PrimaryMaterial;
import com.sigmundgranaas.forgero.core.material.material.SecondaryMaterial;
import com.sigmundgranaas.forgero.core.property.Property;
import com.sigmundgranaas.forgero.core.property.PropertyContainer;
import com.sigmundgranaas.forgero.core.property.Target;
import com.sigmundgranaas.forgero.core.property.attribute.ToolPartTarget;
import com.sigmundgranaas.forgero.core.schematic.Schematic;
import com.sigmundgranaas.forgero.core.toolpart.ForgeroToolPartTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractToolPartState implements PropertyContainer {
    final PrimaryMaterial primaryMaterial;
    final SecondaryMaterial secondaryMaterial;
    final Gem gem;
    final Schematic schematic;

    public AbstractToolPartState(PrimaryMaterial primaryMaterial, SecondaryMaterial secondaryMaterial, Gem gem, Schematic schematic) {
        this.primaryMaterial = primaryMaterial;
        this.secondaryMaterial = secondaryMaterial;
        this.gem = gem;
        this.schematic = schematic;
    }


    public PrimaryMaterial getPrimaryMaterial() {
        return primaryMaterial;
    }

    public SecondaryMaterial getSecondaryMaterial() {
        return secondaryMaterial;
    }

    public Gem getGem() {
        return gem;
    }

    public Schematic getSchematic() {
        return schematic;
    }

    public abstract ForgeroToolPartTypes getToolPartType();

    public @NotNull List<Property> getProperties(Target target) {
        return Stream.of(primaryMaterial.getPrimaryProperties(),
                        secondaryMaterial.getSecondaryProperties(),
                        gem.getProperties(),
                        schematic.getProperties(target))
                .flatMap(Collection::stream)
                .filter(property -> property.applyCondition(target.combineTarget(getToolPartConditionTarget())))
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull List<Property> getRootProperties() {
        return getProperties(Target.createEmptyTarget());
    }

    @Override
    public @NotNull List<Property> getProperties() {
        return getRootProperties();
    }

    private Target getToolPartConditionTarget() {
        return new ToolPartTarget(Set.of(getToolPartType().toString()));
    }
}
