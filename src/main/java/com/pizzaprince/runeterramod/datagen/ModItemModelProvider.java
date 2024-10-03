package com.pizzaprince.runeterramod.datagen;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RuneterraMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        block(ModBlocks.BLACK_TERRACOTTA_SLAB);
        block(ModBlocks.BLACK_TERRACOTTA_STAIRS);
        block(ModBlocks.TERRACOTTA_SLAB);
        block(ModBlocks.TERRACOTTA_STAIRS);
        block(ModBlocks.WHITE_TERRACOTTA_SLAB);
        block(ModBlocks.WHITE_TERRACOTTA_STAIRS);
        block(ModBlocks.ORANGE_TERRACOTTA_SLAB);
        block(ModBlocks.ORANGE_TERRACOTTA_STAIRS);
        block(ModBlocks.MAGENTA_TERRACOTTA_SLAB);
        block(ModBlocks.MAGENTA_TERRACOTTA_STAIRS);
        block(ModBlocks.LIGHT_BLUE_TERRACOTTA_SLAB);
        block(ModBlocks.LIGHT_BLUE_TERRACOTTA_STAIRS);
        block(ModBlocks.YELLOW_TERRACOTTA_SLAB);
        block(ModBlocks.YELLOW_TERRACOTTA_STAIRS);
        block(ModBlocks.LIME_TERRACOTTA_SLAB);
        block(ModBlocks.LIME_TERRACOTTA_STAIRS);
        block(ModBlocks.PINK_TERRACOTTA_SLAB);
        block(ModBlocks.PINK_TERRACOTTA_STAIRS);
        block(ModBlocks.GRAY_TERRACOTTA_SLAB);
        block(ModBlocks.GRAY_TERRACOTTA_STAIRS);
        block(ModBlocks.LIGHT_GRAY_TERRACOTTA_SLAB);
        block(ModBlocks.LIGHT_GRAY_TERRACOTTA_STAIRS);
        block(ModBlocks.CYAN_TERRACOTTA_SLAB);
        block(ModBlocks.CYAN_TERRACOTTA_STAIRS);
        block(ModBlocks.PURPLE_TERRACOTTA_SLAB);
        block(ModBlocks.PURPLE_TERRACOTTA_STAIRS);
        block(ModBlocks.BLUE_TERRACOTTA_SLAB);
        block(ModBlocks.BLUE_TERRACOTTA_STAIRS);
        block(ModBlocks.BROWN_TERRACOTTA_SLAB);
        block(ModBlocks.BROWN_TERRACOTTA_STAIRS);
        block(ModBlocks.GREEN_TERRACOTTA_SLAB);
        block(ModBlocks.GREEN_TERRACOTTA_STAIRS);
        block(ModBlocks.RED_TERRACOTTA_SLAB);
        block(ModBlocks.RED_TERRACOTTA_STAIRS);
    }

    private void block(RegistryObject<Block> block){
        withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath()));
    }
}
