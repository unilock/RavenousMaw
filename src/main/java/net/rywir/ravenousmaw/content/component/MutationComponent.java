package net.rywir.ravenousmaw.content.component;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import java.util.HashMap;

public record MutationComponent(ImmutableMap<String, Integer> mutations) {
    public static final MutationComponent EMPTY = new MutationComponent(ImmutableMap.of());

    public static final Codec<MutationComponent> CODEC;
    public static final StreamCodec<ByteBuf, MutationComponent> STREAM_CODEC;

    static {
        CODEC = Codec.unboundedMap(Codec.STRING, Codec.INT)
            .xmap(
                map -> new MutationComponent(ImmutableMap.copyOf(map)),
                MutationComponent::mutations
            );

        STREAM_CODEC = ByteBufCodecs.map(
            HashMap::new,
            ByteBufCodecs.STRING_UTF8,
            ByteBufCodecs.VAR_INT
        ).map(
            map -> new MutationComponent(ImmutableMap.copyOf(map)),
            comp -> new HashMap<>(comp.mutations())
        );
    }
}