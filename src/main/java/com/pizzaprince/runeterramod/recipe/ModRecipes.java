package com.pizzaprince.runeterramod.recipe;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.custom.SunForge;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RuneterraMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ItemTransfuserRecipe>> ITEM_TRANSFUSER_SERIALIZER = SERIALIZERS.register("item_transfusing",
            () -> ItemTransfuserRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SunForgeRecipe>> SUN_FORGE_SERIALIZER = SERIALIZERS.register("sun_forge",
            () -> SunForgeRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
