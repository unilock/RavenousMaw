package net.rywir.ravenousmaw.content.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.rywir.ravenousmaw.datagen.provider.RavenousItemTagsProvider;
import net.rywir.ravenousmaw.registry.Mutations;
import net.rywir.ravenousmaw.system.MutationHandler;

public class RavenousBreakSpeedEvent {
    private static final float SUBMERGED_MODIFIER = 5.0F;
    private static final float AIRBORNE_MODIFIER = 5.0F;

    @SubscribeEvent
    public static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();

        if (!stack.is(RavenousItemTagsProvider.MAW)) {
            return;
        }

        BlockState state = event.getState();

        if (state.is(Blocks.COBWEB)) {
            event.setNewSpeed(event.getOriginalSpeed() * 15.0F);
            return;
        }

        MutationHandler handler = new MutationHandler(stack);

        if (!handler.has(Mutations.INDOMITABLE_WILL)) {
            return;
        }

        if (player.isEyeInFluidType(Fluids.WATER.getFluidType())) {
            event.setNewSpeed(event.getOriginalSpeed() * SUBMERGED_MODIFIER);
        }

        else if (!player.onGround() && !player.isInWater()) {
            event.setNewSpeed(event.getOriginalSpeed() * AIRBORNE_MODIFIER);
        }
    }
}
