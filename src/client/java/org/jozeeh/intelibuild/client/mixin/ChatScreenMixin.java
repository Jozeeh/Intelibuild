package org.jozeeh.intelibuild.client.mixin;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.input.KeyInput;
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

    @Inject(method = "render", at = @At("RETURN"))
    private void intelibuild$onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (idPanel != null && IdPanelWidget.isPanelVisible()) {
            idPanel.render(context, mouseX, mouseY, delta);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void intelibuild$onMouseClicked(Click click, boolean hasShiftDown, CallbackInfoReturnable<Boolean> cir) {
        if (idPanel == null || !IdPanelWidget.isPanelVisible()) {
            return;
        }
        if (idPanel.isMouseOver(click.x(), click.y())) {
            idPanel.mouseClicked(click, hasShiftDown);
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void intelibuild$onKeyPressed(KeyInput keyInput, CallbackInfoReturnable<Boolean> cir) {
        if (ModKeyBindings.TOGGLE_ID_PANEL.matchesKey(keyInput)) {
            IdPanelWidget.toggleVisible();
            cir.setReturnValue(true);
        }
    }
}
