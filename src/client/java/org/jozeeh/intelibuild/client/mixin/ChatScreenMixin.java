package org.jozeeh.intelibuild.client.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.jozeeh.intelibuild.client.feature.blockiddisplay.IdPanelWidget;
import org.jozeeh.intelibuild.client.keybinding.ModKeyBindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    @Shadow
    public abstract void insertText(String text, boolean overrideSelection);

    @Unique
    private IdPanelWidget idPanel;

    @Inject(method = "init", at = @At("RETURN"))
    private void intelibuild$onInit(CallbackInfo ci) {
        idPanel = new IdPanelWidget(id -> this.insertText(id, false));
    }

    // Since 26.1 the GUI is built in two phases; `extractRenderState` replaces `render`.
    @Inject(method = "extractRenderState", at = @At("RETURN"))
    private void intelibuild$onExtractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (idPanel != null && IdPanelWidget.isPanelVisible()) {
            idPanel.render(context, mouseX, mouseY, delta);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void intelibuild$onMouseClicked(MouseButtonEvent click, boolean hasShiftDown, CallbackInfoReturnable<Boolean> cir) {
        if (idPanel == null || !IdPanelWidget.isPanelVisible()) {
            return;
        }
        if (idPanel.isMouseOver(click.x(), click.y())) {
            idPanel.mouseClicked(click, hasShiftDown);
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void intelibuild$onKeyPressed(KeyEvent keyEvent, CallbackInfoReturnable<Boolean> cir) {
        if (ModKeyBindings.TOGGLE_ID_PANEL.matches(keyEvent)) {
            IdPanelWidget.toggleVisible();
            cir.setReturnValue(true);
        }
    }
}