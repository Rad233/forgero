package com.sigmundgranaas.forgero.core.property;

import com.sigmundgranaas.forgero.core.property.attribute.Category;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interface for attributes which can affect the attributes of tools.
 * Attributes are designed to be pooled together and calculated in a chain.
 */
public interface Attribute extends Property, Comparable<Attribute> {
    Predicate<Target> DEFAULT_CONDITION = (target) -> true;
    Function<Float, Float> DEFAULT_ATTRIBUTE_CALCULATION = (currentFloat) -> currentFloat;

    @Override
    default PropertyTypes getType() {
        return PropertyTypes.ATTRIBUTES;

    }

    default CalculationOrder getOrder() {
        return CalculationOrder.BASE;
    }

    @Override
    default int compareTo(@NotNull Attribute o) {
        int order = getOrder().getValue() - o.getOrder().getValue();
        if (order == 0) {
            if (getOperation() == NumericOperation.ADDITION) {
                return -1;
            } else {
                return 1;
            }
        }
        return order;
    }

    AttributeType getAttributeType();

    default Function<Float, Float> getCalculation() {
        return DEFAULT_ATTRIBUTE_CALCULATION;
    }

    default Predicate<Target> getCondition() {
        return DEFAULT_CONDITION;
    }

    NumericOperation getOperation();

    float getValue();

    Category getCategory();

    int getLevel();


    int getPriority();

    String getId();


    default float applyAttribute(Target target, float currentAttribute) {
        if (this.getCondition().test(target)) {
            return this.getCalculation().apply(currentAttribute);
        }
        return currentAttribute;
    }

    @Override
    default boolean applyCondition(Target target) {
        return this.getCondition().test(target);
    }


}