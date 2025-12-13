package net.rywir.ravenousmaw.content.component;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.rywir.ravenousmaw.registry.Mutations;

import java.util.HashMap;
import java.util.Map;

public record AdaptiveShiftComponent(ImmutableMap<String, Boolean> parameters) {
    public static final Codec<AdaptiveShiftComponent> CODEC;
    public static final StreamCodec<ByteBuf, AdaptiveShiftComponent> STREAM_CODEC;

    public static AdaptiveShiftComponent generateDefaultInstance() {
        Map<String, Boolean> pseudo = new HashMap<>();

        pseudo.put(Mutations.Parameters.LOOT.getKey(), true);
        pseudo.put(Mutations.Parameters.SILK_TOUCH.getKey(), false);

        ImmutableMap<String, Boolean> componentMap = ImmutableMap.copyOf(pseudo);
        return new AdaptiveShiftComponent(componentMap);
    }

    static {
        CODEC = Codec.unboundedMap(Codec.STRING, Codec.BOOL)
            .xmap(
                map -> new AdaptiveShiftComponent(ImmutableMap.copyOf(map)),
                AdaptiveShiftComponent::parameters
            );

        STREAM_CODEC = ByteBufCodecs.map(
            HashMap::new,
            ByteBufCodecs.STRING_UTF8,
            ByteBufCodecs.BOOL
        ).map(
            map -> new AdaptiveShiftComponent(ImmutableMap.copyOf(map)),
            comp -> new HashMap<>(comp.parameters)
        );
    }
}
