package com.pizzaprince.runeterramod.datagen;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, RuneterraMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.SUN_STONE_BLOCK);
        blockWithItem(ModBlocks.SHURIMAN_SAND);
        //blockWithItem(ModBlocks.SHURIMAN_SANDSTONE);
        blockWithItem(ModBlocks.SUN_STONE_ORE);
        blockWithItem(ModBlocks.SUN_DISK_SHARD);
        blockWithItem(ModBlocks.SUN_DISK_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
        simpleBlockItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
