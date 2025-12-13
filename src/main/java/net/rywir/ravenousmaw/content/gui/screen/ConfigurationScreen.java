package net.rywir.ravenousmaw.content.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import net.rywir.ravenousmaw.RavenousMaw;
import net.rywir.ravenousmaw.content.gui.menu.ConfigurationMenu;
import net.rywir.ravenousmaw.content.payload.ConfigurationPayload;

public class ConfigurationScreen extends AbstractContainerScreen<ConfigurationMenu> {
    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(
        RavenousMaw.MOD_ID,
        "textures/gui/configuration_menu.png"
    );

    private StringWidget lootExchangeWidget;
    private StringWidget silkExchangeWidget;
    private StringWidget areaWidget;

    public ConfigurationScreen(ConfigurationMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void init() {
        super.init();

        Font font = Minecraft.getInstance().font;
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.addRenderableWidget(
            Button.builder(
                Component.translatable("configuration_menu_string.ravenousmaw.loot"),
                button -> switchLootExchange()
            ).bounds(x + 10, y + 20, 73, 20).build()
        );

        this.lootExchangeWidget = new StringWidget(
            x + 86, y + 20, 73, 20,
            this.menu.getLootExchangeComponent(),
            font
        );
        this.addRenderableWidget(lootExchangeWidget);

        this.addRenderableWidget(
            Button.builder(
                Component.translatable("configuration_menu_string.ravenousmaw.silk_touch"),
                button -> switchSilk()
            ).bounds(x + 10, y + 45, 73, 20).build()
        );

        this.silkExchangeWidget = new StringWidget(
            x + 86, y + 45, 73, 20,
            this.menu.getSilkExchangeComponent(),
            font
        );
        this.addRenderableWidget(silkExchangeWidget);

        this.addRenderableWidget(
            Button.builder(
                Component.translatable("configuration_menu_string.ravenousmaw.area"),
                button -> switchArea()
            ).bounds(x + 10, y + 70, 73, 20).build()
        );

        this.areaWidget = new StringWidget(
            x + 86, y + 70, 73, 20,
            this.menu.getAreaComponent(),
            font
        );
        this.addRenderableWidget(areaWidget);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float partialTick) {
        super.render(graphics, pMouseX, pMouseY, partialTick);
        this.renderTooltip(graphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {}

    private void switchLootExchange() {
        PacketDistributor.sendToServer(new ConfigurationPayload(0));
        this.menu.toggleLoot();
        updateWidget(lootExchangeWidget, this.menu.getLootExchangeComponent());
    }

    private void switchSilk() {
        PacketDistributor.sendToServer(new ConfigurationPayload(1));
        this.menu.toggleSilk();
        updateWidget(silkExchangeWidget, this.menu.getSilkExchangeComponent());
    }

    private void switchArea() {
        if (hasShiftDown()) {
            PacketDistributor.sendToServer(new ConfigurationPayload(3));
            this.menu.resetArea();
        } else {
            PacketDistributor.sendToServer(new ConfigurationPayload(2));
            this.menu.configureArea();
        }
        updateWidget(areaWidget, this.menu.getAreaComponent());
    }

    private void updateWidget(StringWidget widget, Component component) {
        widget.setMessage(component);
    }
}