package com.pizzaprince.runeterramod.effect;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
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
    public static final RegistryObject<Potion> SCORPION_POISON = POTIONS.register("scorpion_poison",
            () -> new Potion(new MobEffectInstance(ModEffects.VULNERABILITY.get(), 1200, 0),
                    new MobEffectInstance(ModEffects.EXHAUSTED.get(), 1200, 0),
                    new MobEffectInstance(ModEffects.SCORPION_POISON.get(), 1200, 0)));

    public static void addPotionRecipes(){
        PotionBrewing.addMix(Potions.AWKWARD, Items.IRON_INGOT, ModPotions.GIANT_POTION.get());
        PotionBrewing.addMix(ModPotions.GIANT_POTION.get(), Blocks.IRON_BLOCK.asItem(), ModPotions.STRONG_GIANT_POTION.get());
    }

    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }
}
