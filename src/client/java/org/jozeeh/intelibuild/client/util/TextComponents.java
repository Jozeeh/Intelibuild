package org.jozeeh.intelibuild.client.util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class TextComponents {
    private TextComponents() {
    }

    public static Text literal(String text) {
        return Text.literal(text);
    }

    public static Text translatable(String key, Object... args) {
        return Text.translatable(key, args);
    }

    public static Text literal(String text, Formatting formatting) {
        return Text.literal(text).formatted(formatting);
    }
}
