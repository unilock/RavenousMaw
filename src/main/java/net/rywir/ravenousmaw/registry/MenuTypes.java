package net.rywir.ravenousmaw.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rywir.ravenousmaw.RavenousMaw;
import net.rywir.ravenousmaw.content.gui.menu.ConfigurationMenu;
import net.rywir.ravenousmaw.content.gui.menu.MutationMatrixMenu;

import java.util.function.Supplier;

public class MenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, RavenousMaw.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<MutationMatrixMenu>> MUTATION_MATRIX_MENU = MENU_TYPES.register("mutation_matrix_menu",
        () -> IMenuTypeExtension.create(MutationMatrixMenu::new));

    public static final Supplier<MenuType<ConfigurationMenu>> CONFIGURATION_MENU = MENU_TYPES.register(
        "configuration_menu",
        () -> IMenuTypeExtension.create((id, inv, data) -> {
            ItemStack stack = inv.player.getMainHandItem();
            return new ConfigurationMenu(id, inv, stack);
        })
    );

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
