package com.pizzaprince.runeterramod.block;

import com.google.common.base.Supplier;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.custom.ShurimanCactus;
import com.pizzaprince.runeterramod.block.custom.ShurimanSand;
import com.pizzaprince.runeterramod.block.custom.SunDiskAltar;
import com.pizzaprince.runeterramod.item.ModCreativeModeTab;
import com.pizzaprince.runeterramod.item.ModItems;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RuneterraMod.MOD_ID);
	
	public static final RegistryObject<Block> SUN_STONE_BLOCK = registerBlock("sun_stone_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RUNETERRA_TAB);
	
	public static final RegistryObject<Block> SUN_STONE_ORE = registerBlock("sun_stone_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3,7)), ModCreativeModeTab.RUNETERRA_TAB);

	public static final RegistryObject<Block> SHURIMAN_SAND = registerBlock("shuriman_sand",
			() -> new ShurimanSand(BlockBehaviour.Properties.of(Material.SAND).sound(SoundType.SAND)), ModCreativeModeTab.RUNETERRA_TAB);

	public static final RegistryObject<Block> SHURIMAN_SANDSTONE = registerBlock("shuriman_sandstone",
			() -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.8f).requiresCorrectToolForDrops()), ModCreativeModeTab.RUNETERRA_TAB);

	public static final RegistryObject<Block> SUN_DISK_SHARD = registerBlock("sun_disk_shard",
			() -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f).requiresCorrectToolForDrops()), ModCreativeModeTab.RUNETERRA_TAB);

	public static final RegistryObject<Block> SUN_DISK_ALTAR = BLOCKS.register("sun_disk_altar",
			() -> new SunDiskAltar(BlockBehaviour.Properties.of(Material.STONE).strength(4f).requiresCorrectToolForDrops().noOcclusion().noLootTable()));

	public static final RegistryObject<Block> SUN_DISK_BLOCK = registerBlock("sun_disk_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(-1).noLootTable()), ModCreativeModeTab.RUNETERRA_TAB);

	public static final RegistryObject<Block> SHURIMAN_CACTUS = registerBlock("shuriman_cactus",
			() -> new ShurimanCactus(BlockBehaviour.Properties.copy(Blocks.CACTUS).noOcclusion()), ModCreativeModeTab.RUNETERRA_TAB);


	private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn, tab);
		return toReturn;
	}
	
	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
	
	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}

}
