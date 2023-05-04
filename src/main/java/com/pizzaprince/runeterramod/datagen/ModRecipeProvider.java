package com.pizzaprince.runeterramod.datagen;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, ModItems.SUN_STONE.get(), ModBlocks.SUN_STONE_BLOCK.get());
        /*
        ShapelessRecipeBuilder.shapeless(ModItems.SUN_STONE.get())
                .requires(ModBlocks.SUN_STONE_BLOCK.get())
                .unlockedBy("has_sun_stone_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.SUN_STONE_BLOCK.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.SUN_STONE_BLOCK.get())
                .define('C', ModItems.SUN_STONE.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_sun_stone", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.SUN_STONE.get()).build()))
                .save(pFinishedRecipeConsumer);
         */

    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, getSimpleRecipeName(pPacked), (String)null, getSimpleRecipeName(pUnpacked), (String)null);
    }

    protected static void nineBlockStorageRecipesWithCustomPacking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked, String pPackingRecipeName, String pPackingRecipeGroup) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, pPackingRecipeName, pPackingRecipeGroup, getSimpleRecipeName(pUnpacked), (String)null);
    }

    protected static void nineBlockStorageRecipesRecipesWithCustomUnpacking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked, String pUnpackingRecipeName, String pUnpackingRecipeGroup) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, getSimpleRecipeName(pPacked), (String)null, pUnpackingRecipeName, pUnpackingRecipeGroup);
    }
    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked, String pPackingRecipeName, @Nullable String pPackingRecipeGroup, String pUnpackingRecipeName, @Nullable String pUnpackingRecipeGroup) {
        ShapelessRecipeBuilder.shapeless(pUnpacked, 9).requires(pPacked).group(pUnpackingRecipeGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pFinishedRecipeConsumer, new ResourceLocation(RuneterraMod.MOD_ID, pUnpackingRecipeName));
        ShapedRecipeBuilder.shaped(pPacked).define('#', pUnpacked).pattern("###").pattern("###").pattern("###").group(pPackingRecipeGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, new ResourceLocation(RuneterraMod.MOD_ID, pPackingRecipeName));
    }
}
