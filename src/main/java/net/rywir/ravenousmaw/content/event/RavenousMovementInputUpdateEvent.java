package net.rywir.ravenousmaw.content.event;

import net.minecraft.client.player.Input;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.rywir.ravenousmaw.registry.RavenousMobEffects;

public class RavenousMovementInputUpdateEvent {
    @SubscribeEvent
    public static void onMovementInputUpdateEvent(MovementInputUpdateEvent event) {
        LivingEntity entity = event.getEntity();

        if (!entity.hasEffect(RavenousMobEffects.SYMBIOTIC_INFECTION)) {
            return;
        }

        Input input = event.getInput();

        input.forwardImpulse = -input.forwardImpulse;
        input.leftImpulse = -input.leftImpulse;
    }
}
