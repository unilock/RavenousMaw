package net.rywir.ravenousmaw.datagen.provider;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.rywir.ravenousmaw.registry.RavenousBlocks;

import java.util.Set;

public class RavenousBlockLootProvider extends BlockLootSubProvider {
    public RavenousBlockLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(RavenousBlocks.MUTATION_MATRIX.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return RavenousBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
