package org.jozeeh.intelibuild.client.feature.blockstatecopier;

import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jozeeh.intelibuild.client.util.McClient;

import java.util.LinkedHashMap;
import java.util.Map;

public final class BlockStateCopier {
    private BlockStateCopier() {
    }

    public static boolean copyFromCrosshair(BlockPos pos) {
        ClientLevel world = McClient.getWorld();
        LocalPlayer player = McClient.getPlayer();
        MultiPlayerGameMode gameMode = McClient.getInteractionManager();

        BlockState state = world.getBlockState(pos);
        if (state.getProperties().isEmpty()) {
            return false;
        }

        ItemStack stack = state.getCloneItemStack(world, pos, false);
        if (stack.isEmpty()) {
            return false;
        }

        Map<String, String> properties = new LinkedHashMap<>();
        for (Property<?> prop : state.getProperties()) {
            String propName = prop.getName();
            String valueName = nameOf(prop, state.getValue(prop));
            properties.put(propName, valueName);
        }
        stack.set(DataComponents.BLOCK_STATE, new BlockItemStateProperties(properties));

        ItemLore lore = stack.getOrDefault(DataComponents.LORE, ItemLore.EMPTY);
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String propName = entry.getKey();
            String valueName = entry.getValue();

            ChatFormatting valueColor;
            if (valueName.equals("true")) {
                valueColor = ChatFormatting.AQUA;
            } else if (valueName.equals("false")) {
                valueColor = ChatFormatting.RED;
            } else {
                valueColor = ChatFormatting.YELLOW;
            }

            Component line = Component.literal(propName + ": ")
                .withStyle(s -> s.withColor(ChatFormatting.GRAY).withItalic(false))
                .append(Component.literal(valueName)
                    .withStyle(s -> s.withColor(valueColor).withItalic(false)));

            if (!lore.lines().contains(line)) {
                lore = lore.withLineAdded(line);
            }
        }
        stack.set(DataComponents.LORE, lore);

        int hotbarSlot = player.getInventory().getSelectedSlot();
        player.getInventory().setItem(hotbarSlot, stack);
        gameMode.handleCreativeModeItemAdd(stack, 36 + hotbarSlot);
        return true;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> String nameOf(Property<T> prop, Comparable<?> value) {
        return prop.getName((T) value);
    }
}