package org.jozeeh.intelibuild.client.keybinding;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public final class ModKeyBindings {
    public static KeyBinding COPY_BLOCK_STATE;

    private static final KeyBinding.Category INTELIBUILD_CATEGORY =
        KeyBinding.Category.create(Identifier.of("intelibuild", "category"));

    private ModKeyBindings() {
    }

    public static void register() {
        COPY_BLOCK_STATE = new KeyBinding(
            "key.intelibuild.copy_block_state",
            InputUtil.GLFW_KEY_LEFT_CONTROL,
            INTELIBUILD_CATEGORY
        );
        KeyBindingHelper.registerKeyBinding(COPY_BLOCK_STATE);
    }
}
