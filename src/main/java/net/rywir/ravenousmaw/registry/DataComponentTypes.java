package net.rywir.ravenousmaw.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rywir.ravenousmaw.RavenousMaw;
import net.rywir.ravenousmaw.content.component.AdaptiveShiftComponent;
import net.rywir.ravenousmaw.content.component.MutationComponent;

public class DataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, RavenousMaw.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MutationComponent>> MUTATION_COMPONENT_TYPE = DATA_COMPONENT_TYPES.register("mutation_component",
        () -> DataComponentType.<MutationComponent>builder()
            .persistent(MutationComponent.CODEC)
            .networkSynchronized(MutationComponent.STREAM_CODEC)
            .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AdaptiveShiftComponent>> ADAPTIVE_SHIFT_COMPONENT_TYPE = DATA_COMPONENT_TYPES.register("adaptive_shift_component",
        () -> DataComponentType.<AdaptiveShiftComponent>builder()
            .persistent(AdaptiveShiftComponent.CODEC)
            .networkSynchronized(AdaptiveShiftComponent.STREAM_CODEC)
            .build());

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
