package org.jozeeh.intelibuild.client;

import net.fabricmc.api.ClientModInitializer;
import org.jozeeh.intelibuild.client.feature.Features;
import org.jozeeh.intelibuild.client.feature.blockstatecopier.BlockStateCopierFeature;
import org.jozeeh.intelibuild.client.feature.blockiddisplay.BlockIdDisplayFeature;
import org.jozeeh.intelibuild.client.hud.HudElements;
import org.jozeeh.intelibuild.client.keybinding.ModKeyBindings;

public class IntelibuildClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModKeyBindings.register();
        Features.register(new BlockStateCopierFeature());
        Features.register(new BlockIdDisplayFeature());
        Features.initializeAll();
        HudElements.registerAll();
    }
}
