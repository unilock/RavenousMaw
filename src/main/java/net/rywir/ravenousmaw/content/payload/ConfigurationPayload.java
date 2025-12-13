package net.rywir.ravenousmaw.content.payload;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rywir.ravenousmaw.RavenousMaw;

public record ConfigurationPayload(int buttonId) implements CustomPacketPayload {
    public static final Type<ConfigurationPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(RavenousMaw.MOD_ID, "configuration_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigurationPayload> STREAM_CODEC =
        StreamCodec.composite(
            ByteBufCodecs.INT, ConfigurationPayload::buttonId,
            ConfigurationPayload::new
        );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
