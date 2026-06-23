package org.jozeeh.intelibuild.client.feature.blockiddisplay;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import org.jozeeh.intelibuild.client.util.McClient;

import java.util.function.Consumer;

public class IdPanelWidget extends ClickableWidget {
    private static final int PANEL_WIDTH = 170;
    private static final int PANEL_HEIGHT = 200;
    private static final int TAB_HEIGHT = 18;
    private static final int PADDING = 4;
    private static final int ITEM_SIZE = 16;
    private static final int ITEM_SPACING = 2;
    private static final int CELL_SIZE = ITEM_SIZE + ITEM_SPACING;
    private static final int HOTBAR_TEXT_OFFSET = 6;

    private static final int COLOR_PANEL_BG = 0x90000000;
    private static final int COLOR_TAB_ACTIVE = 0x80404040;
    private static final int COLOR_TAB_HOVER = 0x60303030;
    private static final int COLOR_TAB_INACTIVE = 0x40202020;
    private static final int COLOR_TAB_TEXT_ACTIVE = 0xFFFFFFFF;
    private static final int COLOR_TAB_TEXT_INACTIVE = 0xFFAAAAAA;
    private static final int COLOR_HOVER = 0x40FFFFFF;
    private static final int COLOR_ID_YELLOW = 0xFFFFE800;

    private static IdPanelTab currentTab = IdPanelTab.HOTBAR;
    private static boolean panelVisible = true;

    private final Consumer<String> insertCallback;

    public IdPanelWidget(Consumer<String> insertCallback) {
        super(0, 5, PANEL_WIDTH, PANEL_HEIGHT, Text.literal("ID Panel"));
        this.insertCallback = insertCallback;
    }

    public static void toggleVisible() {
        panelVisible = !panelVisible;
    }

    public static boolean isPanelVisible() {
        return panelVisible;
    }

    private void reposition() {
        Window window = McClient.getWindow();
        setX(window.getScaledWidth() - PANEL_WIDTH);
        setY(5);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!panelVisible) {
            return;
        }

        reposition();

        int px = getX();
        int py = getY();

        context.fill(px, py, px + PANEL_WIDTH, py + PANEL_HEIGHT, COLOR_PANEL_BG);

        renderTabs(context, mouseX, mouseY, px, py);

        int contentY = py + TAB_HEIGHT + PADDING;
        int contentX = px + PADDING;

        switch (currentTab) {
            case HOTBAR -> renderHotbar(context, mouseX, mouseY, contentX, contentY);
            case INVENTORY -> renderInventory(context, mouseX, mouseY, contentX, contentY);
        }
    }

    private void renderTabs(DrawContext context, int mouseX, int mouseY, int px, int py) {
        TextRenderer tr = McClient.getTextRenderer();
        int tabWidth = PANEL_WIDTH / 2;

        for (IdPanelTab tab : IdPanelTab.values()) {
            int tabX = px + tab.ordinal() * tabWidth;
            boolean isActive = currentTab == tab;
            boolean isHovered = mouseX >= tabX && mouseX < tabX + tabWidth
                && mouseY >= py && mouseY < py + TAB_HEIGHT;

            int bgColor = isActive ? COLOR_TAB_ACTIVE : (isHovered ? COLOR_TAB_HOVER : COLOR_TAB_INACTIVE);
            context.fill(tabX, py, tabX + tabWidth, py + TAB_HEIGHT, bgColor);

            String label = tab.getDisplayName();
            int textWidth = tr.getWidth(label);
            int textColor = isActive ? COLOR_TAB_TEXT_ACTIVE : COLOR_TAB_TEXT_INACTIVE;
            context.drawTextWithShadow(tr, label, tabX + (tabWidth - textWidth) / 2, py + (TAB_HEIGHT - 8) / 2, textColor);
        }
    }

    private void renderHotbar(DrawContext context, int mouseX, int mouseY, int x, int y) {
        ClientPlayerEntity player = McClient.getPlayer();
        if (player == null) {
            return;
        }

        TextRenderer tr = McClient.getTextRenderer();
        int maxWidth = PANEL_WIDTH - 2 * PADDING;
        int row = 0;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isEmpty()) {
                continue;
            }

            int rowY = y + row * CELL_SIZE;
            if (rowY + ITEM_SIZE > getY() + PANEL_HEIGHT) {
                break;
            }

            boolean isHovered = mouseX >= x && mouseX < x + maxWidth
                && mouseY >= rowY && mouseY < rowY + ITEM_SIZE;
            if (isHovered) {
                context.fill(x - 1, rowY - 1, x + maxWidth + 1, rowY + ITEM_SIZE + 1, COLOR_HOVER);
            }

            context.drawItem(stack, x, rowY);
            context.drawStackOverlay(tr, stack, x, rowY);

            String id = Registries.ITEM.getId(stack.getItem()).toString();
            context.drawTextWithShadow(tr, id, x + ITEM_SIZE + HOTBAR_TEXT_OFFSET, rowY + (ITEM_SIZE - 8) / 2, COLOR_ID_YELLOW);

            row++;
        }
    }

    private void renderInventory(DrawContext context, int mouseX, int mouseY, int x, int y) {
        ClientPlayerEntity player = McClient.getPlayer();
        if (player == null) {
            return;
        }

        int[][] slotMap = {
            {9, 10, 11, 12, 13, 14, 15, 16, 17},
            {18, 19, 20, 21, 22, 23, 24, 25, 26},
            {27, 28, 29, 30, 31, 32, 33, 34, 35},
            {0, 1, 2, 3, 4, 5, 6, 7, 8}
        };

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 9; col++) {
                int slot = slotMap[row][col];
                ItemStack stack = player.getInventory().getStack(slot);
                int itemX = x + col * CELL_SIZE;
                int itemY = y + row * CELL_SIZE;

                boolean isHovered = mouseX >= itemX && mouseX < itemX + ITEM_SIZE
                    && mouseY >= itemY && mouseY < itemY + ITEM_SIZE;
                if (isHovered) {
                    context.fill(itemX - 1, itemY - 1, itemX + ITEM_SIZE + 1, itemY + ITEM_SIZE + 1, COLOR_HOVER);
                }

                if (!stack.isEmpty()) {
                    TextRenderer tr = McClient.getTextRenderer();
                    context.drawItem(stack, itemX, itemY);
                    context.drawStackOverlay(tr, stack, itemX, itemY);
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.gui.Click click, boolean hasShiftDown) {
        if (!panelVisible) {
            return false;
        }

        reposition();

        double mouseX = click.x();
        double mouseY = click.y();
        int px = getX();
        int py = getY();

        if (mouseX < px || mouseX >= px + PANEL_WIDTH || mouseY < py || mouseY >= py + PANEL_HEIGHT) {
            return false;
        }

        if (mouseY >= py && mouseY < py + TAB_HEIGHT) {
            int tabWidth = PANEL_WIDTH / 2;
            int tabIndex = (int) ((mouseX - px) / tabWidth);
            if (tabIndex >= 0 && tabIndex < IdPanelTab.values().length) {
                currentTab = IdPanelTab.values()[tabIndex];
            }
            return true;
        }

        int contentX = px + PADDING;
        int contentY = py + TAB_HEIGHT + PADDING;

        switch (currentTab) {
            case HOTBAR -> handleHotbarClick(mouseX, mouseY, contentX, contentY);
            case INVENTORY -> handleInventoryClick(mouseX, mouseY, contentX, contentY);
        }

        return true;
    }

    private void handleHotbarClick(double mouseX, double mouseY, int x, int y) {
        ClientPlayerEntity player = McClient.getPlayer();
        if (player == null) {
            return;
        }

        int maxWidth = PANEL_WIDTH - 2 * PADDING;
        int row = 0;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isEmpty()) {
                continue;
            }

            int rowY = y + row * CELL_SIZE;
            if (mouseY >= rowY && mouseY < rowY + ITEM_SIZE && mouseX >= x && mouseX < x + maxWidth) {
                String id = Registries.ITEM.getId(stack.getItem()).toString();
                insertCallback.accept(id);
                return;
            }
            row++;
        }
    }

    private void handleInventoryClick(double mouseX, double mouseY, int x, int y) {
        ClientPlayerEntity player = McClient.getPlayer();
        if (player == null) {
            return;
        }

        int[][] slotMap = {
            {9, 10, 11, 12, 13, 14, 15, 16, 17},
            {18, 19, 20, 21, 22, 23, 24, 25, 26},
            {27, 28, 29, 30, 31, 32, 33, 34, 35},
            {0, 1, 2, 3, 4, 5, 6, 7, 8}
        };

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 9; col++) {
                int slot = slotMap[row][col];
                ItemStack stack = player.getInventory().getStack(slot);
                if (stack.isEmpty()) {
                    continue;
                }

                int itemX = x + col * CELL_SIZE;
                int itemY = y + row * CELL_SIZE;
                if (mouseX >= itemX && mouseX < itemX + ITEM_SIZE
                    && mouseY >= itemY && mouseY < itemY + ITEM_SIZE) {
                    String id = Registries.ITEM.getId(stack.getItem()).toString();
                    insertCallback.accept(id);
                    return;
                }
            }
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return panelVisible && super.isMouseOver(mouseX, mouseY);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }
}
