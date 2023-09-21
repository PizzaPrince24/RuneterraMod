package com.pizzaprince.runeterramod.world.dimension;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.registries.ForgeRegistries;

public class ModDimensions {

    public static final ResourceKey<Level> DISK_FIGHT_DIM_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(RuneterraMod.MOD_ID, "disk_fight_dim"));

    public static final ResourceKey<DimensionType> DISK_FIGHT_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            DISK_FIGHT_DIM_KEY.registry());

    public static final ResourceKey<Level> TURTLE_SHELL_SPACE_DIM = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(RuneterraMod.MOD_ID, "turtle_shell_dim"));

    public static final ResourceKey<DimensionType> TURTLE_SHELL_SPACE_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            TURTLE_SHELL_SPACE_DIM.registry());

    public static void register(){
        System.out.println("Registering Mod Dimensions for " + RuneterraMod.MOD_ID);
    }
}
