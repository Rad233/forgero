package com.sigmundgranaas.forgero.item.items;

import com.sigmundgranaas.forgero.ForgeroInitializer;
import com.sigmundgranaas.forgero.core.ForgeroRegistry;
import com.sigmundgranaas.forgero.core.data.v1.pojo.SchematicPojo;
import com.sigmundgranaas.forgero.core.property.Property;
import com.sigmundgranaas.forgero.core.property.PropertyContainer;
import com.sigmundgranaas.forgero.core.property.Target;
import com.sigmundgranaas.forgero.core.resource.ForgeroResourceType;
import com.sigmundgranaas.forgero.core.schematic.Schematic;
import com.sigmundgranaas.forgero.item.ForgeroItem;
import com.sigmundgranaas.forgero.item.adapter.DescriptionWriter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SchematicItem extends Item implements ForgeroItem<SchematicItem>, PropertyContainer {
    private final Schematic schematic;

    public SchematicItem(Settings settings, Schematic pattern) {
        super(settings);
        this.schematic = pattern;
    }

    @Override
    public Text getName() {
        MutableText text = new TranslatableText(String.format("item.%s.%s", ForgeroInitializer.MOD_NAMESPACE, getSchematic().getResourceName())).append(" ");

        text.append(new TranslatableText(String.format("item.%s.%s", ForgeroInitializer.MOD_NAMESPACE, "schematic")));
        return text;
    }

    @Override
    public Text getName(ItemStack stack) {
        return getName();
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        new DescriptionWriter(tooltip).writeSchematicDescription(getSchematic());
        new DescriptionWriter(tooltip).addToolPartProperties(Property.stream(getSchematic().getProperties(Target.createEmptyTarget())));
    }

    public Schematic getSchematic() {
        return ForgeroRegistry.SCHEMATIC.getResource(schematic);
    }

    @Override
    public String getStringIdentifier() {
        return getSchematic().getStringIdentifier();
    }

    @Override
    public String getResourceName() {
        return getSchematic().getResourceName();
    }

    @Override
    public ForgeroResourceType getResourceType() {
        return ForgeroResourceType.SCHEMATIC;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        int containerResult = PropertyContainer.super.compareTo(o);
        if (containerResult != 0) {
            return containerResult;
        } else {
            return ForgeroItem.super.compareTo(o);
        }
    }

    @Override
    public SchematicItem getItem() {
        return this;
    }

    @Override
    public @NotNull List<Property> getProperties() {
        return getSchematic().getProperties();
    }
}
