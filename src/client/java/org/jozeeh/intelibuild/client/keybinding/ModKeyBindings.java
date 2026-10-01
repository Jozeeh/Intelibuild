package org.jozeeh.intelibuild.client.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public final class ModKeyBindings {
    public static KeyMapping COPY_BLOCK_STATE;
    public static KeyMapping TOGGLE_ID_PANEL;

    private static final KeyMapping.Category INTELIBUILD_CATEGORY =
        KeyMapping.Category.register(Identifier.fromNamespaceAndPath("intelibuild", "category"));

    private ModKeyBindings() {
    }

    public static void register() {
        COPY_BLOCK_STATE = new KeyMapping(
            "key.intelibuild.copy_block_state",
            InputConstants.KEY_LCONTROL,
            INTELIBUILD_CATEGORY
        );
        KeyMappingHelper.registerKeyMapping(COPY_BLOCK_STATE);

        TOGGLE_ID_PANEL = new KeyMapping(
            "key.intelibuild.toggle_id_panel",
            InputConstants.KEY_F6,
            INTELIBUILD_CATEGORY
        );
        KeyMappingHelper.registerKeyMapping(TOGGLE_ID_PANEL);
    }
}