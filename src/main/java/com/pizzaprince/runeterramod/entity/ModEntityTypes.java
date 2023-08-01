package com.pizzaprince.runeterramod.entity;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.RampagingBaccaiEntity;
import com.pizzaprince.runeterramod.entity.custom.RekSaiEntity;
import com.pizzaprince.runeterramod.entity.custom.SunFishEntity;
import com.pizzaprince.runeterramod.entity.custom.projectile.EnchantedCrystalArrow;
import com.pizzaprince.runeterramod.entity.custom.projectile.IceArrow;

import com.pizzaprince.runeterramod.entity.custom.projectile.RunaansHomingBolt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
	
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RuneterraMod.MOD_ID);
	
	public static final RegistryObject<EntityType<IceArrow>> ICE_ARROW = ENTITY_TYPES.register("ice_arrow", 
			() -> EntityType.Builder.of((EntityType.EntityFactory<IceArrow>)IceArrow::new, MobCategory.MISC).sized(0.5f, 0.5f)
					.clientTrackingRange(4).updateInterval(20).build(RuneterraMod.MOD_ID + ":ice_arrow"));
	
	public static final RegistryObject<EntityType<EnchantedCrystalArrow>> ENCHANTED_CRYSTAL_ARROW = ENTITY_TYPES.register("enchanted_crystal_arrow", 
			() -> EntityType.Builder.of((EntityType.EntityFactory<EnchantedCrystalArrow>)EnchantedCrystalArrow::new, MobCategory.MISC).sized(1f, 1f)
					.clientTrackingRange(4).updateInterval(20).build(RuneterraMod.MOD_ID + ":enchanted_crystal_arrow"));
	
	public static final RegistryObject<EntityType<RekSaiEntity>> REKSAI = ENTITY_TYPES.register("reksai", 
			() -> EntityType.Builder.of(RekSaiEntity::new, MobCategory.MONSTER).sized(1, 1)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "reksai").toString()));

	public static final RegistryObject<EntityType<RampagingBaccaiEntity>> RAMPAGING_BACCAI = ENTITY_TYPES.register("rampaging_baccai",
			() -> EntityType.Builder.of(RampagingBaccaiEntity::new, MobCategory.MONSTER).sized(3, 7)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "rampaging_baccai").toString()));

	public static final RegistryObject<EntityType<RunaansHomingBolt>> RUNAANS_HOMING_BOLT = ENTITY_TYPES.register("runaans_homing_bolt",
			() -> EntityType.Builder.of((EntityType.EntityFactory<RunaansHomingBolt>)RunaansHomingBolt::new, MobCategory.MISC).sized(0.5f, 0.5f)
					.clientTrackingRange(6).updateInterval(20).build(RuneterraMod.MOD_ID + ":runaans_homing_bolt"));

	public static final RegistryObject<EntityType<SunFishEntity>> SUNFISH = ENTITY_TYPES.register("sunfish",
			() -> EntityType.Builder.of(SunFishEntity::new, MobCategory.WATER_AMBIENT).sized(1, 1)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "sunfish").toString()));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}

}
