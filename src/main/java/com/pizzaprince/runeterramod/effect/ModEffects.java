package com.pizzaprince.runeterramod.effect;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.effect.custom.*;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public class ModEffects {
	
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RuneterraMod.MOD_ID);
	
	public static final RegistryObject<MobEffect> STUN = MOB_EFFECTS.register("stun",
			() -> new StunEffect(MobEffectCategory.HARMFUL, 8989061));
	public static final RegistryObject<MobEffect> QUENCHED = MOB_EFFECTS.register("quenched",
			() -> new QuenchedEffect(MobEffectCategory.HARMFUL, 8989061));
	public static final RegistryObject<MobEffect> RYLAIS_SLOW = MOB_EFFECTS.register("rylais_slow",
			() -> new RylaisSlow(MobEffectCategory.HARMFUL, 8989061)
					.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID.randomUUID().toString(), -0.3, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> GIANT = MOB_EFFECTS.register("giant",
			() -> new MobEffect(MobEffectCategory.NEUTRAL, 11119017)
					.addAttributeModifier(Attributes.MAX_HEALTH, UUID.randomUUID().toString(), 20, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> DWARF = MOB_EFFECTS.register("dwarf",
			() -> new MobEffect(MobEffectCategory.NEUTRAL, 11119017)
					.addAttributeModifier(Attributes.MAX_HEALTH, UUID.randomUUID().toString(), -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> VULNERABILITY = MOB_EFFECTS.register("vulnerability",
			() -> new MobEffect(MobEffectCategory.HARMFUL, 13907767));
	public static final RegistryObject<MobEffect> EXHAUSTED = MOB_EFFECTS.register("exhausted",
			() -> new MobEffect(MobEffectCategory.HARMFUL, 10184508));
	public static final RegistryObject<MobEffect> SCORPION_POISON = MOB_EFFECTS.register("scorpion_poison",
			() -> new ScorpionPoison(MobEffectCategory.HARMFUL, 6950317));


	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}

}
