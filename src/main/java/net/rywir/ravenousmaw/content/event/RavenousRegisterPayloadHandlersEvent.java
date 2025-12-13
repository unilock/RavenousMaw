package net.rywir.ravenousmaw.content.event;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.rywir.ravenousmaw.content.gui.menu.ConfigurationMenu;
import net.rywir.ravenousmaw.content.payload.ConfigurationPayload;

public class RavenousRegisterPayloadHandlersEvent {
    @SubscribeEvent
    public static void onRegisterPayloadHandlersEvent(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
            ConfigurationPayload.TYPE,
            ConfigurationPayload.STREAM_CODEC,
            RavenousRegisterPayloadHandlersEvent::handleData
        );
    }

    private static void handleData(ConfigurationPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();

            if (player.containerMenu instanceof ConfigurationMenu menu) {
                switch (payload.buttonId()) {
                    case 0:
                        menu.toggleLoot();
                        break;
                    case 1:
                        menu.toggleSilk();
                        break;
                    case 2:
                        menu.configureArea();
                        break;
                    case 3:
                        menu.resetArea();
                }

                menu.broadcastChanges();
            }
        });
    }
}
