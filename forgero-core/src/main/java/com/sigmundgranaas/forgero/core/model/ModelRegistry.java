package com.sigmundgranaas.forgero.core.model;

import com.google.common.collect.ImmutableList;
import com.sigmundgranaas.forgero.core.resource.ResourceListener;
import com.sigmundgranaas.forgero.core.resource.data.v2.data.DataResource;
import com.sigmundgranaas.forgero.core.resource.data.v2.data.ModelData;
import com.sigmundgranaas.forgero.core.resource.data.v2.data.PaletteData;
import com.sigmundgranaas.forgero.core.state.Identifiable;
import com.sigmundgranaas.forgero.core.state.State;
import com.sigmundgranaas.forgero.core.type.TypeTree;
import com.sigmundgranaas.forgero.core.util.match.Context;

import java.util.*;

import static com.sigmundgranaas.forgero.core.resource.data.v2.data.ResourceType.MODEL;

public class ModelRegistry {
    private final HashMap<String, ModelMatcher> modelMap;
    private final Map<String, PaletteTemplateModel> textures;

    private final HashMap<String, ArrayList<ModelData>> delayedModels;

    private final HashMap<String, ModelData> generationModels;
    private TypeTree tree;

    public ModelRegistry(TypeTree tree) {
        this.tree = tree;
        this.modelMap = new HashMap<>();
        this.textures = new HashMap<>();
        this.delayedModels = new HashMap<>();
        this.generationModels = new HashMap<>();
    }

    public ModelRegistry() {
        this.tree = new TypeTree();
        this.modelMap = new HashMap<>();
        this.textures = new HashMap<>();
        this.delayedModels = new HashMap<>();
        this.generationModels = new HashMap<>();
    }

    public ResourceListener<List<DataResource>> modelListener() {
        return (resources, tree, idMapper) -> {
            this.tree = tree;
            resources.stream().filter(resource -> resource.resourceType() == MODEL).forEach(this::register);
        };
    }

    public ResourceListener<List<DataResource>> paletteListener() {
        return (resources, tree, idMapper) -> {
            resources.stream().filter(res -> res.palette().isPresent()).forEach(res -> tree.find(res.type()).ifPresent(node -> node.addResource(res.palette().get(), PaletteData.class)));
        };
    }

    public void setTree(TypeTree tree) {
        this.tree = tree;
    }

    public ModelRegistry register(DataResource data) {
        var converter = new ModelConverter(tree, modelMap, textures, delayedModels, generationModels);
        converter.register(data);
        return this;
    }

    public Optional<ModelTemplate> find(State state) {
        var context = Context.of();
        if (modelMap.containsKey(state.identifier())) {
            return modelMap.get(state.identifier()).get(state, this::provider, Context.of());
        } else {
            var modelEntries = tree.find(state.type().typeName()).map(node -> node.getResources(ModelMatcher.class)).orElse(ImmutableList.<ModelMatcher>builder().build());
            return modelEntries.stream().sorted(ModelMatcher::comparator).filter(entry -> entry.match(state, context)).map(modelMatcher -> modelMatcher.get(state, this::provider, context)).flatMap(Optional::stream).findFirst();
        }
    }


    public Optional<ModelMatcher> provider(Identifiable id) {
        if (modelMap.containsKey(id.identifier())) {
            return Optional.ofNullable(modelMap.get(id.identifier()));
        } else if (modelMap.containsKey(id.name())) {
            return Optional.ofNullable(modelMap.get(id.name()));
        } else if (id instanceof State state) {
            return Optional.of(MultipleModelMatcher.of(tree.find(state.type().typeName()).map(node -> node.getResources(ModelMatcher.class)).orElse(ImmutableList.<ModelMatcher>builder().build())));
        }
        return Optional.empty();
    }

    public Map<String, PaletteTemplateModel> getTextures() {
        return textures;
    }
}
