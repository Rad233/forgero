package com.sigmundgranaas.forgero.minecraft.common.item.nbt.v2;

import com.sigmundgranaas.forgero.core.ForgeroStateRegistry;
import com.sigmundgranaas.forgero.core.registry.StateFinder;
import com.sigmundgranaas.forgero.core.state.State;
import com.sigmundgranaas.forgero.core.state.composite.Construct;
import com.sigmundgranaas.forgero.core.type.Type;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class CompositeParser implements CompoundParser<State> {
    private final StateFinder supplier;

    public CompositeParser(StateFinder supplier) {
        this.supplier = supplier;
    }

    @Override
    public Optional<State> parse(NbtCompound compound) {
        if (!compound.contains(NbtConstants.STATE_TYPE_IDENTIFIER)) {
            return Optional.empty();
        }
        Construct.ConstructBuilder builder = Construct.builder();

        if (compound.contains(NbtConstants.ID_IDENTIFIER)) {
            var id = compound.getString(NbtConstants.ID_IDENTIFIER);
            var stateOpt = supplier.find(id);

            if (stateOpt.isPresent() && stateOpt.get() instanceof Construct construct) {
                builder = Construct.builder(construct.slots());
            } else if (ForgeroStateRegistry.CONTAINER_TO_STATE.containsKey(id)) {
                return supplier.find(ForgeroStateRegistry.CONTAINER_TO_STATE.get(id));
            }
            builder.id(id);
        } else {
            if (compound.contains(NbtConstants.NAME_IDENTIFIER)) {
                builder.name(compound.getString(NbtConstants.NAME_IDENTIFIER));
            }

            if (compound.contains(NbtConstants.NAMESPACE_IDENTIFIER)) {
                builder.nameSpace(compound.getString(NbtConstants.NAMESPACE_IDENTIFIER));
            }
        }
        if (compound.contains(NbtConstants.TYPE_IDENTIFIER)) {
            builder.type(Type.of(compound.getString(NbtConstants.TYPE_IDENTIFIER)));
        }
        if (compound.contains(NbtConstants.INGREDIENTS_IDENTIFIER)) {
            parseEntries(compound.getList(NbtConstants.INGREDIENTS_IDENTIFIER, NbtElement.COMPOUND_TYPE)).forEach(builder::addIngredient);
        }
        if (compound.contains(NbtConstants.INGREDIENTS_IDENTIFIER)) {
            parseEntries(compound.getList(NbtConstants.INGREDIENTS_IDENTIFIER, NbtElement.STRING_TYPE)).forEach(builder::addIngredient);
        }
        if (compound.contains(NbtConstants.UPGRADES_IDENTIFIER)) {
            parseEntries(compound.getList(NbtConstants.UPGRADES_IDENTIFIER, NbtElement.COMPOUND_TYPE)).forEach(builder::addUpgrade);
        }
        if (compound.contains(NbtConstants.UPGRADES_IDENTIFIER)) {
            parseEntries(compound.getList(NbtConstants.UPGRADES_IDENTIFIER, NbtElement.STRING_TYPE)).forEach(builder::addUpgrade);
        }
        return Optional.of(builder.build());
    }


    private List<State> parseEntries(List<NbtElement> elements) {
        return elements
                .stream()
                .map(this::parseEntry)
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<State> parseEntry(NbtElement element) {
        if (element.getType() == NbtElement.STRING_TYPE) {
            return supplier.find(element.asString());
        } else if (element.getType() == NbtElement.COMPOUND_TYPE) {
            if (element instanceof NbtCompound compound) {
                return parseCompound(compound, supplier::find);
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Optional<State> parseCompound(NbtCompound compound, Function<String, Optional<State>> supplier) {
        if (compound.contains(NbtConstants.STATE_TYPE_IDENTIFIER)) {
            if (compound.getString(NbtConstants.STATE_TYPE_IDENTIFIER).equals(NbtConstants.STATE_IDENTIFIER)) {
                return supplier.apply(compound.getString(NbtConstants.ID_IDENTIFIER));
            } else if (compound.getString(NbtConstants.STATE_TYPE_IDENTIFIER).equals(NbtConstants.COMPOSITE_IDENTIFIER)) {
                return parse(compound);
            } else if (compound.getString(NbtConstants.STATE_TYPE_IDENTIFIER).equals(NbtConstants.LEVELED_IDENTIFIER)) {
                return new StateParser(this.supplier).parse(compound);
            }
        }
        return parse(compound);
    }
}
