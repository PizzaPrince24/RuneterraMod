package com.pizzaprince.runeterramod.item;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.item.custom.AsheBow;
import com.pizzaprince.runeterramod.item.custom.BaccaiStaff;
import com.pizzaprince.runeterramod.item.custom.SunDiskAltarItem;
import com.pizzaprince.runeterramod.item.custom.armor.AsheArmorItem;
import com.pizzaprince.runeterramod.item.custom.curios.InfinityEdge;
import com.pizzaprince.runeterramod.item.custom.curios.Rylais;
import com.pizzaprince.runeterramod.item.custom.curios.SunfireAegis;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Properties;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RuneterraMod.MOD_ID);
	
	public static final RegistryObject<Item> SUN_STONE = ITEMS.register("sun_stone",
			() -> new Item(new Item.Properties()));
	
	public static final RegistryObject<Item> ASHE_BOW = ITEMS.register("ashe_bow", 
			() -> new AsheBow(new Item.Properties().stacksTo(1)));
	
	public static final RegistryObject<Item> ASHE_HELMET = ITEMS.register("ashe_helmet", 
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, ArmorItem.Type.HELMET, new Item.Properties()));
	
	public static final RegistryObject<Item> ASHE_CHESTPLATE = ITEMS.register("ashe_chestplate", 
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	
	public static final RegistryObject<Item> ASHE_LEGGINGS = ITEMS.register("ashe_leggings", 
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	
	public static final RegistryObject<Item> ASHE_BOOTS = ITEMS.register("ashe_boots", 
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));
	
	public static final RegistryObject<Item> REKSAI_SPAWN_EGG = ITEMS.register("reksai_spawn_egg", 
			() -> new ForgeSpawnEggItem(ModEntityTypes.REKSAI, 0x1C1C1C, 0x456296, new Item.Properties()));

	public static final RegistryObject<Item> RAMPAGING_BACCAI_SPAWN_EGG = ITEMS.register("rampaging_baccai_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityTypes.RAMPAGING_BACCAI, 16750899, 16776960, new Item.Properties()));
	public static final RegistryObject<Item> SUN_DISK_ALTAR_ITEM = ITEMS.register("sun_disk_altar",
			() -> new SunDiskAltarItem(ModBlocks.SUN_DISK_ALTAR.get(), new Item.Properties()));

	public static final RegistryObject<Item> BACCAI_STAFF = ITEMS.register("baccai_staff",
			() -> new BaccaiStaff(new Item.Properties().stacksTo(1).setNoRepair()));

	public static final RegistryObject<Item> SUNFIRE_AEGIS = ITEMS.register("sunfire_aegis",
			() -> new SunfireAegis(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> RYLAIS_SCEPTER = ITEMS.register("rylais_scepter",
			() -> new Rylais(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> INFINITY_EDGE = ITEMS.register("infinity_edge",
			() -> new InfinityEdge(new Item.Properties().stacksTo(1)));


	public static final RegistryObject<Item> CACTUS_JUICE = ITEMS.register("cactus_juice",
			() -> new Item(new Item.Properties().stacksTo(64).food(
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
