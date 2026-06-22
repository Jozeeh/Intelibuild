package org.jozeeh.intelibuild.client.feature.blockstatecopier;

import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.jozeeh.intelibuild.client.util.McClient;

import java.util.LinkedHashMap;
import java.util.Map;

public final class BlockStateCopier {
    private BlockStateCopier() {
    }

    public static boolean copyFromCrosshair(BlockPos pos) {
        ClientWorld world = McClient.getWorld();
        ClientPlayerEntity player = McClient.getPlayer();
        ClientPlayerInteractionManager interactionManager = McClient.getInteractionManager();

        BlockState state = world.getBlockState(pos);
        if (state.getProperties().isEmpty()) {
            return false;
        }

        ItemStack stack = state.getPickStack(world, pos, false);
        if (stack.isEmpty()) {
            return false;
        }

        Map<String, String> properties = new LinkedHashMap<>();
        for (Property<?> prop : state.getProperties()) {
            String propName = prop.getName();
            String valueName = nameOf(prop, state.get(prop));
            properties.put(propName, valueName);
        }
        stack.set(DataComponentTypes.BLOCK_STATE, new BlockStateComponent(properties));

        LoreComponent lore = stack.getOrDefault(DataComponentTypes.LORE, LoreComponent.DEFAULT);
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String propName = entry.getKey();
            String valueName = entry.getValue();

            Formatting valueColor;
            if (valueName.equals("true")) {
                valueColor = Formatting.AQUA;
            } else if (valueName.equals("false")) {
                valueColor = Formatting.RED;
            } else {
                valueColor = Formatting.YELLOW;
            }

            Text line = Text.literal(propName + ": ")
                .styled(s -> s.withColor(Formatting.GRAY).withItalic(false))
                .append(Text.literal(valueName)
                    .styled(s -> s.withColor(valueColor).withItalic(false)));

            if (!lore.lines().contains(line)) {
                lore = lore.with(line);
            }
        }
        stack.set(DataComponentTypes.LORE, lore);

        int hotbarSlot = player.getInventory().getSelectedSlot();
        player.getInventory().setStack(hotbarSlot, stack);
        interactionManager.clickCreativeStack(stack, 36 + hotbarSlot);
        return true;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> String nameOf(Property<T> prop, Comparable<?> value) {
        return prop.name((T) value);
    }
}
