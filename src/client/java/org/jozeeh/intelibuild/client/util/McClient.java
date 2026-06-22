package org.jozeeh.intelibuild.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.world.ClientWorld;

public final class McClient {
    private McClient() {
    }

    public static MinecraftClient getClient() {
        return MinecraftClient.getInstance();
    }

    public static ClientPlayerEntity getPlayer() {
        return getClient().player;
    }

    public static ClientWorld getWorld() {
        return getClient().world;
    }

    public static Window getWindow() {
        return getClient().getWindow();
    }

    public static ClientPlayerInteractionManager getInteractionManager() {
        return getClient().interactionManager;
    }

    public static TextRenderer getTextRenderer() {
        return getClient().textRenderer;
    }

    public static Screen getCurrentScreen() {
        return getClient().currentScreen;
    }
}
