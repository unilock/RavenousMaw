package net.rywir.ravenousmaw.content.gui.menu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.rywir.ravenousmaw.registry.MenuTypes;
import net.rywir.ravenousmaw.registry.Mutations;
import net.rywir.ravenousmaw.registry.Stages;
import net.rywir.ravenousmaw.system.AdaptiveShiftHandler;
import net.rywir.ravenousmaw.system.MutationHandler;
import net.rywir.ravenousmaw.system.StageHandler;

public class ConfigurationMenu extends AbstractContainerMenu {
    private static final Component ACTIVE = Component.translatable("configuration_menu_string.ravenousmaw.active");
    private static final Component INACTIVE = Component.translatable("configuration_menu_string.ravenousmaw.inactive");
    private static final Component UNAVAILABLE = Component.translatable("configuration_menu_string.ravenousmaw.unavailable");

    private final ItemStack stack;
    private final Level level;

    public ConfigurationMenu(int id, Inventory inv, ItemStack stack) {
        super(MenuTypes.CONFIGURATION_MENU.get(), id);
        this.stack = stack;
        this.level = inv.player.level();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public Component getLootExchangeComponent() {
        MutationHandler mutationHandler = new MutationHandler(stack);
        if (!mutationHandler.has(Mutations.ADAPTIVE_SHIFT)) {
            return UNAVAILABLE;
        }

        AdaptiveShiftHandler handler = new AdaptiveShiftHandler(stack);
        return handler.getConfigBool(Mutations.Parameters.LOOT) ? ACTIVE : INACTIVE;
    }

    public Component getSilkExchangeComponent() {
        MutationHandler mutationHandler = new MutationHandler(stack);
        if (!mutationHandler.has(Mutations.ADAPTIVE_SHIFT)) {
            return UNAVAILABLE;
        }

        AdaptiveShiftHandler handler = new AdaptiveShiftHandler(stack);
        return handler.getConfigBool(Mutations.Parameters.SILK_TOUCH) ? ACTIVE : INACTIVE;
    }

    public Component getAreaComponent() {
        MutationHandler mutationHandler = new MutationHandler(stack);
        if (!mutationHandler.has(Mutations.TECTONIC_BITE)) {
            return UNAVAILABLE;
        }

        int area = mutationHandler.getConfigVal(Mutations.TECTONIC_BITE);
        return Component.literal(area + "x" + area);
    }

    // Configuration actions
    public void toggleLoot() {
        MutationHandler mutationHandler = new MutationHandler(stack);
        if (!mutationHandler.has(Mutations.ADAPTIVE_SHIFT)) {
            return;
        }

        AdaptiveShiftHandler handler = new AdaptiveShiftHandler(stack);
        handler.toggle(Mutations.Parameters.LOOT);
    }

    public void toggleSilk() {
        MutationHandler mutationHandler = new MutationHandler(stack);
        if (!mutationHandler.has(Mutations.ADAPTIVE_SHIFT)) {
            return;
        }

        AdaptiveShiftHandler handler = new AdaptiveShiftHandler(stack);
        handler.toggle(Mutations.Parameters.SILK_TOUCH);
        Mutations.ADAPTIVE_SHIFT.getAbility().onUpdate(stack, level);
    }

    public void resetArea() {
        MutationHandler mutationHandler = new MutationHandler(stack);
        if (!mutationHandler.has(Mutations.TECTONIC_BITE)) {
            return;
        }

        mutationHandler.configure(Mutations.TECTONIC_BITE, 1);
    }

    public void configureArea() {
        MutationHandler mutationHandler = new MutationHandler(stack);
        if (!mutationHandler.has(Mutations.TECTONIC_BITE)) {
            return;
        }

        int currentArea = mutationHandler.getConfigVal(Mutations.TECTONIC_BITE);
        StageHandler stageHandler = new StageHandler(stack);
        Stages stage = stageHandler.getStage();

        int newArea = switch (stage) {
            case LATENT -> currentArea == 1 ? 3 : 1;
            case ADVANCED -> switch (currentArea) {
                case 1 -> 3;
                case 3 -> 5;
                default -> 1;
            };
            case NOBLE -> switch (currentArea) {
                case 1 -> 3;
                case 3 -> 5;
                case 5 -> 7;
                default -> 1;
            };
            case EXCELSIOR -> switch (currentArea) {
                case 1 -> 3;
                case 3 -> 5;
                case 5 -> 7;
                case 7 -> 9;
                default -> 1;
            };
        };

        mutationHandler.configure(Mutations.TECTONIC_BITE, newArea);
    }
}