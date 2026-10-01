package org.jozeeh.intelibuild.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.BlockHitResult;
import org.jozeeh.intelibuild.client.feature.blockstatecopier.BlockStateCopier;
import org.jozeeh.intelibuild.client.keybinding.ModKeyBindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class DoItemPickMixin {

    // Since 26.1 the middle-click pick logic lives in `pickBlockOrEntity` (`doItemPick`).
    @Inject(method = "pickBlockOrEntity", at = @At("HEAD"), cancellable = true)
    private void intelibuild$onPickBlockOrEntity(CallbackInfo ci) {
        if (!ModKeyBindings.COPY_BLOCK_STATE.isDown()) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.gameMode == null || client.level == null) {
            return;
        }

        if (!client.gameMode.getPlayerMode().isCreative()) {
            return;
        }

        if (!(client.hitResult instanceof BlockHitResult blockHit)) {
            return;
        }

        if (BlockStateCopier.copyFromCrosshair(blockHit.getBlockPos())) {
            ci.cancel();
        }
    }
}