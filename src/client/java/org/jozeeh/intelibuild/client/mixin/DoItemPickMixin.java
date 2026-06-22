package org.jozeeh.intelibuild.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import org.jozeeh.intelibuild.client.feature.blockstatecopier.BlockStateCopier;
import org.jozeeh.intelibuild.client.keybinding.ModKeyBindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class DoItemPickMixin {

    @Inject(method = "doItemPick", at = @At("HEAD"), cancellable = true)
    private void intelibuild$onDoItemPick(CallbackInfo ci) {
        if (!ModKeyBindings.COPY_BLOCK_STATE.isPressed()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null || client.world == null) {
            return;
        }

        if (!client.interactionManager.getCurrentGameMode().isCreative()) {
            return;
        }

        if (!(client.crosshairTarget instanceof BlockHitResult blockHit)) {
            return;
        }

        if (BlockStateCopier.copyFromCrosshair(blockHit.getBlockPos())) {
            ci.cancel();
        }
    }
}
