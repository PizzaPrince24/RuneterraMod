package com.pizzaprince.runeterramod.item;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.item.custom.*;
import com.pizzaprince.runeterramod.item.custom.armor.AsheArmorItem;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RuneterraMod.MOD_ID);
	
	public static final RegistryObject<Item> SUN_STONE = ITEMS.register("sun_stone",
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
	
	public static final RegistryObject<Item> REKSAI_SPAWN_EGG = ITEMS.register("reksai_spawn_egg", 
			() -> new ForgeSpawnEggItem(ModEntityTypes.REKSAI, 0x1C1C1C, 0x456296, new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB)));

	public static final RegistryObject<Item> SUN_DISK_ALTAR_ITEM = ITEMS.register("sun_disk_altar",
			() -> new SunDiskAltarItem(ModBlocks.SUN_DISK_ALTAR.get(), new Item.Properties().tab(ModCreativeModeTab.RUNETERRA_TAB)));


	public static final RegistryObject<Item> CACTUS_JUICE = ITEMS.register("cactus_juice",
			() -> new Item(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.RUNETERRA_TAB).food(
					new FoodProperties.Builder().saturationMod(7).nutrition(6)
							.effect(() -> new MobEffectInstance(ModEffects.QUENCHED.get(), 90*20, 0, false, true, true), 1)
							.effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 90*20, 1, false, false, false), 1)
							.effect(() -> new MobEffectInstance(MobEffects.LUCK, 90*20, 2, false, false, false), 1)
							.alwaysEat().build()
			)));


	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
