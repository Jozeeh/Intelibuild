package org.jozeeh.intelibuild.client;

import net.fabricmc.api.ClientModInitializer;
import org.jozeeh.intelibuild.client.feature.Features;
import org.jozeeh.intelibuild.client.hud.HudElements;
import org.jozeeh.intelibuild.client.keybinding.ModKeyBindings;

public class IntelibuildClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModKeyBindings.register();
        Features.initializeAll();
        HudElements.registerAll();
    }
}
