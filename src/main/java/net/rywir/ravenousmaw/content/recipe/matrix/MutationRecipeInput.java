package net.rywir.ravenousmaw.content.recipe.matrix;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record MutationRecipeInput(ItemStack base, ItemStack feast, ItemStack mutagen) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        if (index == 0) {
            return base;
        }

        if (index == 1) {
            return feast;
        }

        else {
            return mutagen;
        }
    }

    @Override
    public int size() {
        return 3;
    }
}
