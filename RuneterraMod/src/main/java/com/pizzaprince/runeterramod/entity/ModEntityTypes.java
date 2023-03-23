package com.pizzaprince.runeterramod.entity;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.champion.RekSaiEntity;
import com.pizzaprince.runeterramod.entity.custom.projectile.EnchantedCrystalArrow;
import com.pizzaprince.runeterramod.entity.custom.projectile.IceArrow;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
	
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RuneterraMod.MOD_ID);
	
	public static final RegistryObject<EntityType<IceArrow>> ICE_ARROW = ENTITY_TYPES.register("ice_arrow", 
			() -> EntityType.Builder.of((EntityType.EntityFactory<IceArrow>)IceArrow::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build(RuneterraMod.MOD_ID + ":ice_arrow"));
	
	public static final RegistryObject<EntityType<EnchantedCrystalArrow>> ENCHANTED_CRYSTAL_ARROW = ENTITY_TYPES.register("enchanted_crystal_arrow", 
			() -> EntityType.Builder.of((EntityType.EntityFactory<EnchantedCrystalArrow>)EnchantedCrystalArrow::new, MobCategory.MISC).sized(1f, 1f).clientTrackingRange(4).updateInterval(20).build(RuneterraMod.MOD_ID + ":enchanted_crystal_arrow"));
	
	public static final RegistryObject<EntityType<RekSaiEntity>> REKSAI = ENTITY_TYPES.register("reksai", 
			() -> EntityType.Builder.of(RekSaiEntity::new, MobCategory.MONSTER).sized(1, 1).build(new ResourceLocation(RuneterraMod.MOD_ID, "reksai").toString()));
	
	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}

}
