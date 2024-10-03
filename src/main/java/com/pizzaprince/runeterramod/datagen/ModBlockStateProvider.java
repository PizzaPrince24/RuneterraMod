package com.pizzaprince.runeterramod.datagen;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RuneterraMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        stairs(ModBlocks.BLACK_TERRACOTTA_STAIRS, mcLoc("block/black_terracotta"));
        slab(ModBlocks.BLACK_TERRACOTTA_SLAB, mcLoc("block/black_terracotta"));
        stairs(ModBlocks.TERRACOTTA_STAIRS, mcLoc("block/terracotta"));
        slab(ModBlocks.TERRACOTTA_SLAB, mcLoc("block/terracotta"));
        stairs(ModBlocks.WHITE_TERRACOTTA_STAIRS, mcLoc("block/white_terracotta"));
        slab(ModBlocks.WHITE_TERRACOTTA_SLAB, mcLoc("block/white_terracotta"));
        stairs(ModBlocks.ORANGE_TERRACOTTA_STAIRS, mcLoc("block/orange_terracotta"));
        slab(ModBlocks.ORANGE_TERRACOTTA_SLAB, mcLoc("block/orange_terracotta"));
        stairs(ModBlocks.MAGENTA_TERRACOTTA_STAIRS, mcLoc("block/magenta_terracotta"));
        slab(ModBlocks.MAGENTA_TERRACOTTA_SLAB, mcLoc("block/magenta_terracotta"));
        stairs(ModBlocks.LIGHT_BLUE_TERRACOTTA_STAIRS, mcLoc("block/light_blue_terracotta"));
        slab(ModBlocks.LIGHT_BLUE_TERRACOTTA_SLAB, mcLoc("block/light_blue_terracotta"));
        stairs(ModBlocks.YELLOW_TERRACOTTA_STAIRS, mcLoc("block/yellow_terracotta"));
        slab(ModBlocks.YELLOW_TERRACOTTA_SLAB, mcLoc("block/yellow_terracotta"));
        stairs(ModBlocks.LIME_TERRACOTTA_STAIRS, mcLoc("block/lime_terracotta"));
        slab(ModBlocks.LIME_TERRACOTTA_SLAB, mcLoc("block/lime_terracotta"));
        stairs(ModBlocks.PINK_TERRACOTTA_STAIRS, mcLoc("block/pink_terracotta"));
        slab(ModBlocks.PINK_TERRACOTTA_SLAB, mcLoc("block/pink_terracotta"));
        stairs(ModBlocks.GRAY_TERRACOTTA_STAIRS, mcLoc("block/gray_terracotta"));
        slab(ModBlocks.GRAY_TERRACOTTA_SLAB, mcLoc("block/gray_terracotta"));
        stairs(ModBlocks.LIGHT_GRAY_TERRACOTTA_STAIRS, mcLoc("block/light_gray_terracotta"));
        slab(ModBlocks.LIGHT_GRAY_TERRACOTTA_SLAB, mcLoc("block/light_gray_terracotta"));
        stairs(ModBlocks.CYAN_TERRACOTTA_STAIRS, mcLoc("block/cyan_terracotta"));
        slab(ModBlocks.CYAN_TERRACOTTA_SLAB, mcLoc("block/cyan_terracotta"));
        stairs(ModBlocks.PURPLE_TERRACOTTA_STAIRS, mcLoc("block/purple_terracotta"));
        slab(ModBlocks.PURPLE_TERRACOTTA_SLAB, mcLoc("block/purple_terracotta"));
        stairs(ModBlocks.BLUE_TERRACOTTA_STAIRS, mcLoc("block/blue_terracotta"));
        slab(ModBlocks.BLUE_TERRACOTTA_SLAB, mcLoc("block/blue_terracotta"));
        stairs(ModBlocks.BROWN_TERRACOTTA_STAIRS, mcLoc("block/brown_terracotta"));
        slab(ModBlocks.BROWN_TERRACOTTA_SLAB, mcLoc("block/brown_terracotta"));
        stairs(ModBlocks.GREEN_TERRACOTTA_STAIRS, mcLoc("block/green_terracotta"));
        slab(ModBlocks.GREEN_TERRACOTTA_SLAB, mcLoc("block/green_terracotta"));
        stairs(ModBlocks.RED_TERRACOTTA_STAIRS, mcLoc("block/red_terracotta"));
        slab(ModBlocks.RED_TERRACOTTA_SLAB, mcLoc("block/red_terracotta"));
    }

    private void stairs(RegistryObject<Block> block, ResourceLocation tex){
        stairsBlock((StairBlock) block.get(), tex);
    }

    private void slab(RegistryObject<Block> block, ResourceLocation tex){
        slabBlock((SlabBlock) block.get(), tex, tex);
    }
}
