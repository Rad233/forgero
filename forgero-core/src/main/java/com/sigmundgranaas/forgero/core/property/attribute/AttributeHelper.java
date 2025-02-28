package com.sigmundgranaas.forgero.core.property.attribute;

import com.sigmundgranaas.forgero.core.property.AttributeType;
import com.sigmundgranaas.forgero.core.property.Property;
import com.sigmundgranaas.forgero.core.property.Target;
import com.sigmundgranaas.forgero.core.state.State;

import java.util.List;

public class AttributeHelper {
    private final State state;

    public AttributeHelper(State state) {
        this.state = state;
    }

    public static AttributeHelper of(State state) {
        return new AttributeHelper(state);
    }

    public float attribute(AttributeType type) {
        return switch (type) {
            case RARITY -> rarity();
            case MINING_LEVEL -> miningLevel();
            case DURABILITY -> durability();
            default -> 0f;
        };
    }

    public int rarity() {
        int rarity = (int) state.stream().applyAttribute(AttributeType.RARITY);
        return rarity;
    }

    public int miningLevel() {
        return (int) state.stream().applyAttribute(AttributeType.MINING_LEVEL);
    }

    public int durability() {
        return (int) state.stream().applyAttribute(AttributeType.DURABILITY);
    }

    public List<Property> attributes() {
        return state.applyProperty(Target.EMPTY);
    }
}
