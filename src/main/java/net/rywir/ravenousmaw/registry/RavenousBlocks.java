package net.rywir.ravenousmaw.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rywir.ravenousmaw.RavenousMaw;
import net.rywir.ravenousmaw.content.block.MutationMatrixBlock;

import java.util.function.Supplier;

public class RavenousBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RavenousMaw.MOD_ID);

    public static final DeferredBlock<Block> MUTATION_MATRIX = registerBlock("mutation_matrix",
        () -> new MutationMatrixBlock(BlockBehaviour.Properties.of()
            .strength(4.0F)
            .requiresCorrectToolForDrops()
            .ignitedByLava()
            .sound(SoundType.SLIME_BLOCK))
    );

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        RavenousItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void registerBlocks(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
