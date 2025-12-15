package net.rywir.ravenousmaw.content.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.rywir.ravenousmaw.RavenousMaw;
import net.rywir.ravenousmaw.content.gui.custom.ConfigurationButton;
import net.rywir.ravenousmaw.content.gui.menu.ConfigurationMenu;
import net.rywir.ravenousmaw.registry.Mutations;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationScreen extends AbstractContainerScreen<ConfigurationMenu> {
    private static final int COLUMNS = 2;
    private static final int ROWS = 6;

    private static final int TEXTURE_SIZE = 16;

    private static final Font font = Minecraft.getInstance().font;

    private final Map<String, StringWidget> widgets;

    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(
        RavenousMaw.MOD_ID,
        "textures/gui/configuration_menu.png"
    );

    public ConfigurationScreen(ConfigurationMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.widgets = new HashMap<>();
    }

    @Override
    protected void init() {
        super.init();
        widgets.clear();

        int cellWidth = this.imageWidth / COLUMNS;
        int cellHeight = this.imageHeight / ROWS;

        int index = 0;
        for (var parameter : this.menu.parameters()) {
            int col = index % COLUMNS;
            int row = index / COLUMNS;

            int rowPos = this.leftPos + (col * cellWidth);
            int colPos = this.topPos + (row * cellHeight);

            int confval = this.menu.configVal(parameter);

            int MARGIN = (cellHeight - TEXTURE_SIZE) / 2;

            Button button = new ConfigurationButton(
                parameter.key(),
                rowPos + MARGIN,
                colPos + MARGIN,
                TEXTURE_SIZE,
                TEXTURE_SIZE,
                Component.literal(String.valueOf(confval)),
                b -> {},
                parameter.icon()
            );

            button.setTooltip(Tooltip.create(
                Component.empty()
                    .append(parameter.title().copy().withStyle(ChatFormatting.GOLD))
                    .append("\n")
                    .append(parameter.description())
            ));

            this.addRenderableWidget(
                button
            );

            StringWidget widget = new StringWidget(
                rowPos + TEXTURE_SIZE + MARGIN * 3,
                colPos + MARGIN,
                TEXTURE_SIZE * 3,
                TEXTURE_SIZE,
                parameter.type() == Mutations.Parameters.Type.TOGGLE ? (confval == 1 ? Component.translatable("screen.ravenousmaw.active") : Component.translatable("screen.ravenousmaw.inactive")) : Component.literal(String.valueOf(confval)),
                font
            );

            this.addRenderableWidget(widget);

            widgets.put(parameter.key(), widget);

            index++;
        }
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {}

    public void update(String key, int value) {
        StringWidget widget = widgets.get(key);

        Mutations.Parameters parameter = Mutations.Parameters.byKey(key);

        if (widget != null) {
            widget.setMessage(parameter.type() == Mutations.Parameters.Type.TOGGLE ? (value == 1 ? Component.translatable("screen.ravenousmaw.active") : Component.translatable("screen.ravenousmaw.inactive")) : Component.literal(String.valueOf(value)));
        }
    }
}