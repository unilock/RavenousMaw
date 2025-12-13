package net.rywir.ravenousmaw.content.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.rywir.ravenousmaw.datagen.provider.RavenousItemTagsProvider;
import net.rywir.ravenousmaw.registry.Mutations;
import net.rywir.ravenousmaw.registry.Stages;
import net.rywir.ravenousmaw.system.MutationHandler;
import net.rywir.ravenousmaw.system.StageHandler;
import net.rywir.ravenousmaw.util.HelperData;

public class FeastItem extends Item {
    public FeastItem(Holder<MobEffect> effect, int nutrition, float saturation) {
        super(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(nutrition)
            .alwaysEdible()
            .saturationModifier(-saturation) // It's disgusting
            .effect(() -> new MobEffectInstance(effect, 200, 1), 1.0f)
            .build())
        );
    }
}