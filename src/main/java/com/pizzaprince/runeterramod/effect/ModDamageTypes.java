package com.pizzaprince.runeterramod.effect;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModDamageTypes {

    public static final ResourceKey<DamageType> SAND_BLAST = registerDamageType("sand_blast");

    public static final ResourceKey<DamageType> RAGE_ART = registerDamageType("rage_art");

    public static final ResourceKey<DamageType> SUN_ENERGY = registerDamageType("sun_energy");

    private static ResourceKey<DamageType> registerDamageType(String name){
        ResourceKey<DamageType> type = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(RuneterraMod.MOD_ID, name));
        return type;
    }

    public static void register(){
        System.out.println("Registering Damage Types for " + RuneterraMod.MOD_ID);
    }

    public static DamageSource getDamageSource(ResourceKey<DamageType> type, Entity source){
        return new DamageSource(source.level().registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(type), null, source, null);
    }
}
