package com.sigmundgranaas.forgero.core.material.material;

import com.sigmundgranaas.forgero.core.constructable.Upgrade;
import com.sigmundgranaas.forgero.core.constructable.Upgradeable;
import com.sigmundgranaas.forgero.core.property.Property;

import java.util.Collections;
import java.util.List;

public interface SecondaryMaterial extends ForgeroMaterial, Upgrade {
    default List<Property> getSecondaryProperties() {
        return Collections.emptyList();
    }
}
