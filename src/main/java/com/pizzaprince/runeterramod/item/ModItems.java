package com.pizzaprince.runeterramod.item;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.item.custom.AsheBow;
import com.pizzaprince.runeterramod.item.custom.EightBallItem;
import com.pizzaprince.runeterramod.item.custom.Test;
import com.pizzaprince.runeterramod.item.custom.armor.AsheArmorItem;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RuneterraMod.MOD_ID);
	
	public static final RegistryObject<Item> ZIRCON = ITEMS.register("zircon", 
			() -> new Item(new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB)));
	
	public static final RegistryObject<Item> RAW_ZIRCON = ITEMS.register("raw_zircon", 
			() -> new Item(new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB)));
	
	public static final RegistryObject<Item> EIGHT_BALL = ITEMS.register("eight_ball", 
			() -> new EightBallItem(new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB).stacksTo(1)));
	
	public static final RegistryObject<Item> ASHE_BOW = ITEMS.register("ashe_bow", 
			() -> new AsheBow(new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB).stacksTo(1)));
	
	public static final RegistryObject<Item> TEST = ITEMS.register("test", 
			() -> new Test(new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB)));
	
	public static final RegistryObject<Item> ASHE_HELMET = ITEMS.register("ashe_helmet", 
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB)));
	
	public static final RegistryObject<Item> ASHE_CHESTPLATE = ITEMS.register("ashe_chestplate", 
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB)));
	
	public static final RegistryObject<Item> ASHE_LEGGINGS = ITEMS.register("ashe_leggings", 
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB)));
	
	public static final RegistryObject<Item> ASHE_BOOTS = ITEMS.register("ashe_boots", 
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB)));
	
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
