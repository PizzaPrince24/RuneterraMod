package com.pizzaprince.runeterramod.effect;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ModDamageTypes {

    public static final ResourceKey<DamageType> SAND_BLAST = registerDamageType("sand_blast");
    public static final ResourceKey<DamageType> RAGE_ART = registerDamageType("rage_art");
    public static final ResourceKey<DamageType> SUN_ENERGY = registerDamageType("sun_energy");
    public static final ResourceKey<DamageType> SCORPION_POISON = registerDamageType("scorpion_poison");
    public static final ResourceKey<DamageType> STOMP = registerDamageType("stomp");

    public static final ResourceKey<DamageType> IMMOLATION = registerDamageType("immolation");

    private static ResourceKey<DamageType> registerDamageType(String name){
        ResourceKey<DamageType> type = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(RuneterraMod.MOD_ID, name));
        return type;
    }

    public static void register(){
        System.out.println("Registering Damage Types for " + RuneterraMod.MOD_ID);
    }

    public static DamageSource getDamageSource(Level level, ResourceKey<DamageType> type) {
        return getEntityDamageSource(level, type, null);
    }

    public static DamageSource getEntityDamageSource(Level level, ResourceKey<DamageType> type, @Nullable Entity attacker) {
        return getIndirectEntityDamageSource(level, type, attacker, attacker);
    }

    public static DamageSource getIndirectEntityDamageSource(Level level, ResourceKey<DamageType> type, @Nullable Entity attacker, @Nullable Entity indirectAttacker) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), attacker, indirectAttacker);
    }
}
