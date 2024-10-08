package com.pizzaprince.runeterramod.datagen;

import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.SUN_STONE_BLOCK.get());
        add(ModBlocks.SUN_STONE_ORE.get(),
                (block) -> createOreDrop(ModBlocks.SUN_STONE_ORE.get(), ModItems.SUN_STONE.get()));
        dropSelf(ModBlocks.SHURIMAN_SAND.get());
        dropSelf(ModBlocks.SHURIMAN_SANDSTONE.get());
        dropSelf(ModBlocks.SHURIMAN_SANDSTONE_STAIRS.get());
        dropSelf(ModBlocks.SMOOTH_SHURIMAN_SANDSTONE.get());
        dropSelf(ModBlocks.SMOOTH_SHURIMAN_SANDSTONE_STAIRS.get());
        dropSelf(ModBlocks.SMOOTH_SHURIMAN_SANDSTONE_SLAB.get());
        dropSelf(ModBlocks.SUN_DISK_SHARD.get());
        dropSelf(ModBlocks.SHURIMAN_CACTUS.get());
        dropSelf(ModBlocks.FANCY_GOBLET.get());
        dropSelf(ModBlocks.BASIC_GOBLET.get());
        dropSelf(ModBlocks.SHELL_BLOCK.get());
        dropSelf(ModBlocks.SUN_FORGE.get());

        dropSelf(ModBlocks.TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.WHITE_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.WHITE_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.ORANGE_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.ORANGE_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.MAGENTA_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.MAGENTA_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.LIGHT_BLUE_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.LIGHT_BLUE_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.YELLOW_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.YELLOW_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.LIME_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.LIME_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.PINK_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.PINK_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.GRAY_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.GRAY_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.LIGHT_GRAY_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.LIGHT_GRAY_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.CYAN_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.CYAN_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.PURPLE_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.PURPLE_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.BLUE_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.BLUE_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.BROWN_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.BROWN_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.GREEN_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.GREEN_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.RED_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.RED_TERRACOTTA_SLAB.get());
        dropSelf(ModBlocks.BLACK_TERRACOTTA_STAIRS.get());
        dropSelf(ModBlocks.BLACK_TERRACOTTA_SLAB.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
