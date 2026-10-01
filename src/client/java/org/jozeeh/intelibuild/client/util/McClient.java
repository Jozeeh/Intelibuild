package org.jozeeh.intelibuild.client.util;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;

public final class McClient {
    private McClient() {
    }

    public static Minecraft getClient() {
        return Minecraft.getInstance();
    }

    public static LocalPlayer getPlayer() {
        return getClient().player;
    }

    public static ClientLevel getWorld() {
        return getClient().level;
    }

    public static Window getWindow() {
        return getClient().getWindow();
    }

    public static MultiPlayerGameMode getInteractionManager() {
        return getClient().gameMode;
    }

    public static Font getTextRenderer() {
        return getClient().font;
    }
}