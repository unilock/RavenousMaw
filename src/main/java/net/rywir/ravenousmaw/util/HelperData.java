package net.rywir.ravenousmaw.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.rywir.ravenousmaw.content.item.FeastItem;
import net.rywir.ravenousmaw.registry.Mutations;
import net.rywir.ravenousmaw.registry.RavenousItems;
import net.rywir.ravenousmaw.registry.Stages;

import java.util.Map;

public class HelperData {
    private static final Map<Stages, DeferredHolder<Item, ? extends Item>> MAW_HOLDERS_BY_STAGE = Map.of(
        Stages.LATENT, RavenousItems.RAVENOUS_MAW_LATENT,
        Stages.ADVANCED, RavenousItems.RAVENOUS_MAW_ADVANCED,
        Stages.NOBLE, RavenousItems.RAVENOUS_MAW_NOBLE,
        Stages.EXCELSIOR, RavenousItems.RAVENOUS_MAW_EXCELSIOR
    );

    private static final Map<DeferredHolder<Item, ? extends Item>, Stages> STAGES_BY_MAW_HOLDERS = Map.of(
        RavenousItems.RAVENOUS_MAW_LATENT, Stages.LATENT,
        RavenousItems.RAVENOUS_MAW_ADVANCED, Stages.ADVANCED,
        RavenousItems.RAVENOUS_MAW_NOBLE, Stages.NOBLE,
        RavenousItems.RAVENOUS_MAW_EXCELSIOR, Stages.EXCELSIOR
    );

    public static Item getMawItem(Stages stage) {
        DeferredHolder<Item, ? extends Item> holder = MAW_HOLDERS_BY_STAGE.get(stage);
        return holder != null ? holder.get() : null;
    }

    public static Stages getStage(DeferredHolder<Item, ? extends Item> item) {
        Stages stage = STAGES_BY_MAW_HOLDERS.get(item);
        return stage;
    }
}
