package org.jozeeh.intelibuild.client.feature.blockiddisplay;

public enum IdPanelTab {
    HOTBAR("intelibuild.id_panel.tab.hotbar", "Hotbar"),
    INVENTORY("intelibuild.id_panel.tab.inventory", "Inventory");

    private final String translationKey;
    private final String displayName;

    IdPanelTab(String translationKey, String displayName) {
        this.translationKey = translationKey;
        this.displayName = displayName;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public String getDisplayName() {
        return displayName;
    }
}
