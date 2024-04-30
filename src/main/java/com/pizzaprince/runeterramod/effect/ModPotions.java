package com.pizzaprince.runeterramod.effect;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, RuneterraMod.MOD_ID);

    public static final RegistryObject<Potion> GIANT_POTION = POTIONS.register("giant_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.GIANT.get(), 3600, 0)));

    public static final RegistryObject<Potion> STRONG_GIANT_POTION = POTIONS.register("strong_giant_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.GIANT.get(), 6000, 1)));

    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }
}
