package com.pizzaprince.runeterramod.effect;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.effect.custom.StunEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
	
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RuneterraMod.MOD_ID);
	
	public static final RegistryObject<MobEffect> STUN = MOB_EFFECTS.register("stun",
			() -> new StunEffect(MobEffectCategory.HARMFUL, 8989061));
	
	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}

}