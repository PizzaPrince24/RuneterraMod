package com.pizzaprince.runeterramod.datagen.loot;

import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.item.ModItems;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        this.dropSelf(ModBlocks.SHURIMAN_SAND.get());
        this.dropSelf(ModBlocks.SUN_STONE_BLOCK.get());
        this.dropSelf(ModBlocks.SHURIMAN_SANDSTONE.get());
        this.dropSelf(ModBlocks.SUN_DISK_SHARD.get());
        this.dropSelf(ModBlocks.SHURIMAN_CACTUS.get());

        this.add(ModBlocks.SUN_STONE_ORE.get(),
                (block) -> createOreDrop(ModBlocks.SUN_STONE_ORE.get(), ModItems.SUN_STONE.get()));

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
