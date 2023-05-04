package com.pizzaprince.runeterramod.datagen;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, RuneterraMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.SUN_STONE_BLOCK);
        blockWithItem(ModBlocks.SHURIMAN_SAND);
        blockWithItem(ModBlocks.SHURIMAN_SANDSTONE);
        blockWithItem(ModBlocks.SUN_STONE_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_ZIRCON_ORE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
        simpleBlockItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
