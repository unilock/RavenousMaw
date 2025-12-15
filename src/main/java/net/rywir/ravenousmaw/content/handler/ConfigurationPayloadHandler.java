package net.rywir.ravenousmaw.content.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.rywir.ravenousmaw.content.gui.menu.ConfigurationMenu;
import net.rywir.ravenousmaw.content.payload.ConfigurationPayload;
import net.rywir.ravenousmaw.content.payload.ConfigurationSyncPayload;
import net.rywir.ravenousmaw.datagen.provider.RavenousItemTagsProvider;
import net.rywir.ravenousmaw.registry.Mutations;
import net.rywir.ravenousmaw.system.MutationHandler;

public class ConfigurationPayloadHandler {
    public static void handle(final ConfigurationPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
                if (!(context.player() instanceof ServerPlayer player)) return;

                ItemStack stack = player.getMainHandItem();

                if (!stack.is(RavenousItemTagsProvider.MAW)) return;

                if (!(player.containerMenu instanceof ConfigurationMenu)) return;

                MutationHandler handler = new MutationHandler(stack);

                switch (payload.action()) {
                    case DECREMENT -> handler.prevConfigVal(payload.paramkey(), player.level());
                    case INCREMENT -> handler.nextConfVal(payload.paramkey(), player.level());
                    case RESET -> handler.resetConfigVal(payload.paramkey(), player.level());
                }

                int newValue = handler.getConfigVal(
                    Mutations.Parameters.byKey(payload.paramkey())
                );

                PacketDistributor.sendToPlayer(
                    player,
                    new ConfigurationSyncPayload(payload.paramkey(), newValue)
                );
            }
        );
    }
}
