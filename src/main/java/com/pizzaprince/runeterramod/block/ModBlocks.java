package com.pizzaprince.runeterramod.block;

import com.google.common.base.Supplier;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.item.ModCreativeModeTab;
import com.pizzaprince.runeterramod.item.ModItems;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SoundType;
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
			() -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops(), UniformInt.of(3,7)), ModCreativeModeTab.RUNETERRA_TAB);
	
	public static final RegistryObject<Block> DEEPSLATE_ZIRCON_ORE = registerBlock("deepslate_zircon_ore", 
			() -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops(), UniformInt.of(3,7)), ModCreativeModeTab.RUNETERRA_TAB);
	
	public static final RegistryObject<Block> SHURIMAN_SAND = registerBlock("shuriman_sand",
			() -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND).sound(SoundType.SAND)), ModCreativeModeTab.RUNETERRA_TAB);

	public static final RegistryObject<Block> SHURIMAN_SANDSTONE = registerBlock("shuriman_sandstone",
			() -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RUNETERRA_TAB);
	
	private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
		RegistryObject<T> toReturn = BLOCKS.register(name,  block);
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
