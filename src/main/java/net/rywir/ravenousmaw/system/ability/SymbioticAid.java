package net.rywir.ravenousmaw.system.ability;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.rywir.ravenousmaw.registry.Mutations;
import net.rywir.ravenousmaw.registry.RavenousMobEffects;
import net.rywir.ravenousmaw.system.MutationHandler;
import net.rywir.ravenousmaw.system.interfaces.IMutationAbility;

public class SymbioticAid implements IMutationAbility {
    // look into RavenousMobEffectAddedEvent for impl

    @Override
    public void onInstability(MutationHandler mutationHandler, ItemStack stack, Player player) {
        boolean hasSymbioticAid = mutationHandler.has(Mutations.SYMBIOTIC_AID);

        if (!hasSymbioticAid) {
            return;
        }

        int durationInSeconds = 6;

        player.addEffect(new MobEffectInstance(RavenousMobEffects.SYMBIOTIC_INFECTION, durationInSeconds * 20));
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, durationInSeconds * 20, 4));
        player.displayClientMessage(Component.translatable("instability_message.ravenousmaw.symbiotic_aid"), true);
    }
}
