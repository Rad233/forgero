package com.sigmundgranaas.forgero.minecraft.common.item.tooltip.writer;

import com.sigmundgranaas.forgero.minecraft.common.item.tooltip.AttributeWriter;
import com.sigmundgranaas.forgero.minecraft.common.item.tooltip.StateWriter;
import com.sigmundgranaas.forgero.core.property.AttributeType;
import com.sigmundgranaas.forgero.core.property.attribute.AttributeHelper;
import com.sigmundgranaas.forgero.core.state.State;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.text.Text;

import java.util.List;

public class PartWriter extends StateWriter {
    public PartWriter(State state) {
        super(state);
    }

    @Override
    public void write(List<Text> tooltip, TooltipContext context) {
        super.write(tooltip, context);
        AttributeWriter.of(AttributeHelper.of(state))
                .addAttribute(AttributeType.DURABILITY)
                .addAttribute(AttributeType.RARITY)
                .write(tooltip, context);
    }
}
