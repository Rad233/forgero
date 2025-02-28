package com.sigmundgranaas.forgero.fabric.mixins;

import com.google.gson.JsonElement;
import com.sigmundgranaas.forgero.fabric.registry.RecipeRegistry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**
 * RecipeManager mixin
 * <p>
 * Forgero does not come with pre-made recipes for tools and tool parts.
 * Recipes are generated from templates at startup.
 */
@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Inject(method = "apply", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        RecipeRegistry.INSTANCE.registerRecipes(map);
    }
}