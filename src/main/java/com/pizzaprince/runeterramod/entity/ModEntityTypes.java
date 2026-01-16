package com.pizzaprince.runeterramod.entity;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.entity.custom.*;
import com.pizzaprince.runeterramod.entity.custom.projectile.CasterMinionProjectile;
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
					.clientTrackingRange(4).updateInterval(20).build(RuneterraMod.MOD_ID + "ice_arrow"));
	
	public static final RegistryObject<EntityType<EnchantedCrystalArrow>> ENCHANTED_CRYSTAL_ARROW = ENTITY_TYPES.register("enchanted_crystal_arrow", 
			() -> EntityType.Builder.of((EntityType.EntityFactory<EnchantedCrystalArrow>)EnchantedCrystalArrow::new, MobCategory.MISC).sized(1f, 1f)
					.clientTrackingRange(4).updateInterval(20).build(RuneterraMod.MOD_ID + "enchanted_crystal_arrow"));
	
	public static final RegistryObject<EntityType<RekSaiEntity>> REKSAI = ENTITY_TYPES.register("reksai", 
			() -> EntityType.Builder.of(RekSaiEntity::new, MobCategory.MONSTER).sized(1, 1)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "reksai").toString()));

	public static final RegistryObject<EntityType<RampagingBaccaiEntity>> RAMPAGING_BACCAI = ENTITY_TYPES.register("rampaging_baccai",
			() -> EntityType.Builder.of(RampagingBaccaiEntity::new, MobCategory.MONSTER).sized(3, 7)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "rampaging_baccai").toString()));

	public static final RegistryObject<EntityType<RenektonEntity>> RENEKTON = ENTITY_TYPES.register("renekton",
			() -> EntityType.Builder.of(RenektonEntity::new, MobCategory.MONSTER).sized(10, 18).clientTrackingRange(100)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "renekton").toString()));

	public static final RegistryObject<EntityType<RunaansHomingBolt>> RUNAANS_HOMING_BOLT = ENTITY_TYPES.register("runaans_homing_bolt",
			() -> EntityType.Builder.of((EntityType.EntityFactory<RunaansHomingBolt>)RunaansHomingBolt::new, MobCategory.MISC).sized(0.5f, 0.5f)
					.clientTrackingRange(6).updateInterval(20).build(RuneterraMod.MOD_ID + "runaans_homing_bolt"));

	public static final RegistryObject<EntityType<SunFishEntity>> SUNFISH = ENTITY_TYPES.register("sunfish",
			() -> EntityType.Builder.of(SunFishEntity::new, MobCategory.WATER_AMBIENT).sized(1, 1)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "sunfish").toString()));

	public static final RegistryObject<EntityType<SunDiskEntity>> SUN_DISK = ENTITY_TYPES.register("sun_disk",
			() -> EntityType.Builder.<SunDiskEntity>of(SunDiskEntity::new, MobCategory.MISC).sized(3, 3).clientTrackingRange(100)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "sun_disk").toString()));

	public static final RegistryObject<EntityType<BlueCasterMinionEntity>> BLUE_CASTER_MINION = ENTITY_TYPES.register("blue_caster_minion",
			() -> EntityType.Builder.of(BlueCasterMinionEntity::new, MobCategory.MONSTER).sized(1, 1)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "blue_caster_minion").toString()));

	public static final RegistryObject<EntityType<CasterMinionProjectile>> CASTER_MINION_PROJECTILE = ENTITY_TYPES.register("caster_minion_projectile",
			() -> EntityType.Builder.of((EntityType.EntityFactory<CasterMinionProjectile>)CasterMinionProjectile::new, MobCategory.MONSTER).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "caster_minion_projectile").toString()));

	public static final RegistryObject<EntityType<BlueMeleeMinionEntity>> BLUE_MELEE_MINION = ENTITY_TYPES.register("blue_melee_minion",
			() -> EntityType.Builder.of(BlueMeleeMinionEntity::new, MobCategory.MONSTER).sized(1.2f, 1.2f)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "blue_melee_minion").toString()));

	public static final RegistryObject<EntityType<BlueCannonMinionEntity>> BLUE_CANNON_MINION = ENTITY_TYPES.register("blue_cannon_minion",
			() -> EntityType.Builder.of(BlueCannonMinionEntity::new, MobCategory.MONSTER).sized(1.7f, 1.5f)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "blue_cannon_minion").toString()));


	public static final RegistryObject<EntityType<BlueSuperMinionEntity>> BLUE_SUPER_MINION = ENTITY_TYPES.register("blue_super_minion",
			() -> EntityType.Builder.of(BlueSuperMinionEntity::new, MobCategory.MONSTER).sized(2.4f, 2.5f)
					.build(new ResourceLocation(RuneterraMod.MOD_ID, "blue_super_minion").toString()));


	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}

}
