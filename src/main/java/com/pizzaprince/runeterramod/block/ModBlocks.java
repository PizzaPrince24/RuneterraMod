package com.pizzaprince.runeterramod.block;

import com.google.common.base.Supplier;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.custom.*;
import com.pizzaprince.runeterramod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RuneterraMod.MOD_ID);

	public static final RegistryObject<Block> SUN_STONE_BLOCK = registerBlock("sun_stone_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(6f).requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> SUN_STONE_ORE = registerBlock("sun_stone_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3,7)));

	public static final RegistryObject<Block> SHURIMAN_SAND = registerBlock("shuriman_sand",
			() -> new ShurimanSand(BlockBehaviour.Properties.copy(Blocks.SAND)));

	public static final RegistryObject<Block> SHURIMAN_SANDSTONE = registerBlock("shuriman_sandstone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.SANDSTONE).strength(0.8f).requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> SHURIMAN_SANDSTONE_STAIRS = registerBlock("shuriman_sandstone_stairs",
			() -> new StairBlock(() -> SHURIMAN_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SANDSTONE_STAIRS)));

	public static final RegistryObject<Block> SMOOTH_SHURIMAN_SANDSTONE = registerBlock("smooth_shuriman_sandstone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE).strength(0.8f).requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> SMOOTH_SHURIMAN_SANDSTONE_STAIRS = registerBlock("smooth_shuriman_sandstone_stairs",
			() -> new StairBlock(() -> SMOOTH_SHURIMAN_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE_STAIRS)));

	public static final RegistryObject<Block> SMOOTH_SHURIMAN_SANDSTONE_SLAB = registerBlock("smooth_shuriman_sandstone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE_SLAB)));

	public static final RegistryObject<Block> SUN_DISK_SHARD = registerBlock("sun_disk_shard",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(4f).requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> SUN_DISK_ALTAR = BLOCKS.register("sun_disk_altar",
			() -> new SunDiskAltar(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE).strength(4f).requiresCorrectToolForDrops().noOcclusion().noLootTable()));

	public static final RegistryObject<Block> SUN_FORGE = BLOCKS.register("sun_forge",
			() -> new SunForge(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE).strength(4f).requiresCorrectToolForDrops().noOcclusion()));

	public static final RegistryObject<Block> SUN_DISK_BLOCK = registerBlock("sun_disk_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(-1).noLootTable()));

	public static final RegistryObject<Block> SHURIMAN_CACTUS = registerBlock("shuriman_cactus",
			() -> new ShurimanCactus(BlockBehaviour.Properties.copy(Blocks.CACTUS).noOcclusion()));

	public static final RegistryObject<Block> FANCY_GOBLET = registerBlock("fancy_goblet",
			() -> new FancyGoblet(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).noOcclusion()));

	public static final RegistryObject<Block> BASIC_GOBLET = registerBlock("basic_goblet",
			() -> new BasicGoblet(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).noOcclusion()));

	public static final RegistryObject<Block> SHURIMAN_ITEM_TRANSFUSER = BLOCKS.register("shuriman_transfuser",
			() -> new ShurimanTransfuser(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE).strength(4f).requiresCorrectToolForDrops().noOcclusion().noLootTable()));

	public static final RegistryObject<Block> SHELL_BLOCK = registerBlock("shell_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

	public static final RegistryObject<Block> BLACK_TERRACOTTA_SLAB = registerBlock("black_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_TERRACOTTA)));

	public static final RegistryObject<Block> BLACK_TERRACOTTA_STAIRS = registerBlock("black_terracotta_stairs",
			() -> new StairBlock(Blocks.BLACK_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.BLACK_TERRACOTTA)));

	public static final RegistryObject<Block> TERRACOTTA_SLAB = registerBlock("terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA)));

	public static final RegistryObject<Block> TERRACOTTA_STAIRS = registerBlock("terracotta_stairs",
			() -> new StairBlock(Blocks.TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.TERRACOTTA)));

	public static final RegistryObject<Block> WHITE_TERRACOTTA_SLAB = registerBlock("white_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_TERRACOTTA)));

	public static final RegistryObject<Block> WHITE_TERRACOTTA_STAIRS = registerBlock("white_terracotta_stairs",
			() -> new StairBlock(Blocks.WHITE_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.WHITE_TERRACOTTA)));

	public static final RegistryObject<Block> ORANGE_TERRACOTTA_SLAB = registerBlock("orange_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.ORANGE_TERRACOTTA)));

	public static final RegistryObject<Block> ORANGE_TERRACOTTA_STAIRS = registerBlock("orange_terracotta_stairs",
			() -> new StairBlock(Blocks.ORANGE_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.ORANGE_TERRACOTTA)));

	public static final RegistryObject<Block> MAGENTA_TERRACOTTA_SLAB = registerBlock("magenta_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.MAGENTA_TERRACOTTA)));

	public static final RegistryObject<Block> MAGENTA_TERRACOTTA_STAIRS = registerBlock("magenta_terracotta_stairs",
			() -> new StairBlock(Blocks.MAGENTA_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.MAGENTA_TERRACOTTA)));

	public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_SLAB = registerBlock("light_blue_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA)));

	public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_STAIRS = registerBlock("light_blue_terracotta_stairs",
			() -> new StairBlock(Blocks.LIGHT_BLUE_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA)));

	public static final RegistryObject<Block> YELLOW_TERRACOTTA_SLAB = registerBlock("yellow_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.YELLOW_TERRACOTTA)));

	public static final RegistryObject<Block> YELLOW_TERRACOTTA_STAIRS = registerBlock("yellow_terracotta_stairs",
			() -> new StairBlock(Blocks.YELLOW_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.YELLOW_TERRACOTTA)));

	public static final RegistryObject<Block> LIME_TERRACOTTA_SLAB = registerBlock("lime_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.LIME_TERRACOTTA)));

	public static final RegistryObject<Block> LIME_TERRACOTTA_STAIRS = registerBlock("lime_terracotta_stairs",
			() -> new StairBlock(Blocks.LIME_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.LIME_TERRACOTTA)));

	public static final RegistryObject<Block> PINK_TERRACOTTA_SLAB = registerBlock("pink_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.PINK_TERRACOTTA)));

	public static final RegistryObject<Block> PINK_TERRACOTTA_STAIRS = registerBlock("pink_terracotta_stairs",
			() -> new StairBlock(Blocks.PINK_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.PINK_TERRACOTTA)));

	public static final RegistryObject<Block> GRAY_TERRACOTTA_SLAB = registerBlock("gray_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_TERRACOTTA)));

	public static final RegistryObject<Block> GRAY_TERRACOTTA_STAIRS = registerBlock("gray_terracotta_stairs",
			() -> new StairBlock(Blocks.GRAY_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.GRAY_TERRACOTTA)));

	public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_SLAB = registerBlock("light_gray_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA)));

	public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_STAIRS = registerBlock("light_gray_terracotta_stairs",
			() -> new StairBlock(Blocks.LIGHT_GRAY_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA)));

	public static final RegistryObject<Block> CYAN_TERRACOTTA_SLAB = registerBlock("cyan_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CYAN_TERRACOTTA)));

	public static final RegistryObject<Block> CYAN_TERRACOTTA_STAIRS = registerBlock("cyan_terracotta_stairs",
			() -> new StairBlock(Blocks.CYAN_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.CYAN_TERRACOTTA)));

	public static final RegistryObject<Block> PURPLE_TERRACOTTA_SLAB = registerBlock("purple_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.PURPLE_TERRACOTTA)));

	public static final RegistryObject<Block> PURPLE_TERRACOTTA_STAIRS = registerBlock("purple_terracotta_stairs",
			() -> new StairBlock(Blocks.PURPLE_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.PURPLE_TERRACOTTA)));

	public static final RegistryObject<Block> BLUE_TERRACOTTA_SLAB = registerBlock("blue_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_TERRACOTTA)));

	public static final RegistryObject<Block> BLUE_TERRACOTTA_STAIRS = registerBlock("blue_terracotta_stairs",
			() -> new StairBlock(Blocks.BLUE_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.BLUE_TERRACOTTA)));

	public static final RegistryObject<Block> BROWN_TERRACOTTA_SLAB = registerBlock("brown_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_TERRACOTTA)));

	public static final RegistryObject<Block> BROWN_TERRACOTTA_STAIRS = registerBlock("brown_terracotta_stairs",
			() -> new StairBlock(Blocks.BROWN_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.BROWN_TERRACOTTA)));

	public static final RegistryObject<Block> GREEN_TERRACOTTA_SLAB = registerBlock("green_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GREEN_TERRACOTTA)));

	public static final RegistryObject<Block> GREEN_TERRACOTTA_STAIRS = registerBlock("green_terracotta_stairs",
			() -> new StairBlock(Blocks.GREEN_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.GREEN_TERRACOTTA)));

	public static final RegistryObject<Block> RED_TERRACOTTA_SLAB = registerBlock("red_terracotta_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.RED_TERRACOTTA)));

	public static final RegistryObject<Block> RED_TERRACOTTA_STAIRS = registerBlock("red_terracotta_stairs",
			() -> new StairBlock(Blocks.RED_TERRACOTTA::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.RED_TERRACOTTA)));

	public static final RegistryObject<Block> NEXUS_CRYSTAL_BLOCK = registerBlock("nexus_crystal_block",
			() -> new NexusCrystalBlock(BlockBehaviour.Properties.of().sound(SoundType.GLASS).lightLevel(state -> 15).noOcclusion()));

	private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}

}
