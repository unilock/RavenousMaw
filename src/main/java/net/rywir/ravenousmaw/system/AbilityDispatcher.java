package net.rywir.ravenousmaw.system;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.rywir.ravenousmaw.RavenousMaw;
import net.rywir.ravenousmaw.registry.Mutations;
import net.rywir.ravenousmaw.registry.Stages;
import net.rywir.ravenousmaw.system.ability.CombustiveEnzyme;
import net.rywir.ravenousmaw.system.interfaces.IMutationAbility;

public class AbilityDispatcher implements IMutationAbility {
    @Override
    public void onAttack(ItemStack stack, LivingEntity target, Level level) {
        Mutations.COMBUSTIVE_ENZYME.getAbility().onAttack(stack, target, level);
    }

    @Override
    public float getAttackDamageBonus(MutationHandler handler, ItemStack stack, LivingEntity target, Level level, Stages stage) {
        float combustionBonus = Mutations.COMBUSTIVE_ENZYME.getAbility().getAttackDamageBonus(handler, stack, target, level, stage);
        float rendingBonus = Mutations.RESONANT_RENDING.getAbility().getAttackDamageBonus(handler, stack, target, level, stage);
        float voracityBonus = Mutations.INSATIABLE_VORACITY.getAbility().getAttackDamageBonus(handler, stack, target, level, stage);
        float adaptiveMultiplier = Mutations.ADAPTIVE_SHIFT.getAbility().getAttackDamageBonus(handler, stack, target, level, stage);
        float arcaneBonus = Mutations.ARCANE_HYPERTROPHY.getAbility().getAttackDamageBonus(handler, stack, target, level, stage);

        float bonus = (combustionBonus + rendingBonus + voracityBonus) * adaptiveMultiplier;

        return bonus;
    }

    @Override
    public void onMine() {}

    @Override
    public void onCraft(ItemStack stack) {}

    @Override
    public void onInstability(MutationHandler mutationHandler, ItemStack stack, Player player) {}
}
