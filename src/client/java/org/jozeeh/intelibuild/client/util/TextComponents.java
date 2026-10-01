package org.jozeeh.intelibuild.client.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public final class TextComponents {
    private TextComponents() {
    }

    public static Component literal(String text) {
        return Component.literal(text);
    }

    public static Component translatable(String key, Object... args) {
        return Component.translatable(key, args);
    }

    public static Component literal(String text, ChatFormatting formatting) {
        return Component.literal(text).withStyle(formatting);
    }
}