package com.sigmundgranaas.forgero.fabric.resources.dynamic;

import com.sigmundgranaas.forgero.core.ForgeroStateRegistry;
import com.sigmundgranaas.forgero.core.state.composite.Construct;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class PartToSchematicGenerator implements DynamicResourceGenerator {

    @Override
    public void generate(RuntimeResourcePack pack) {
        parts().forEach(comp -> createRecipe(comp).ifPresent(recipe -> pack.addRecipe(id(comp), recipe)));
    }

    private List<Construct> parts() {
        return ForgeroStateRegistry.STATES.all().stream()
                .map(Supplier::get)
                .filter(Construct.class::isInstance)
                .map(Construct.class::cast)
                .filter(comp -> comp.ingredients().stream().anyMatch(ingredient -> ingredient.name().contains("schematic")))
                .toList();
    }

    private Optional<JRecipe> createRecipe(Construct construct) {
        var schematic = construct.ingredients().stream().filter(ingredient -> ingredient.name().contains("schematic")).findFirst();
        if (schematic.isPresent()) {
            var ingredients = JIngredients.ingredients().add(JIngredient.ingredient().item(Items.PAPER)).add(JIngredient.ingredient().item(construct.identifier()));
            return Optional.of(JShapelessRecipe.shapeless(ingredients, JResult.result(schematic.get().identifier())));
        }
        return Optional.empty();

    }

    private Identifier id(Construct construct) {
        return new Identifier(construct.nameSpace(), construct.name() + "-schematic_recipe");
    }
}
