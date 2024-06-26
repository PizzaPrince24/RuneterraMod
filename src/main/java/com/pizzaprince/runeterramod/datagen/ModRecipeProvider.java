package com.pizzaprince.runeterramod.datagen;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        nineBlockStorageRecipes(pWriter, RecipeCategory.BUILDING_BLOCKS, ModItems.SUN_STONE.get(), RecipeCategory.MISC, ModBlocks.SUN_STONE_BLOCK.get());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NEXUS_CRYSTAL.get())
                .requires(Items.DRAGON_BREATH).requires(Items.ENDER_PEARL).requires(Items.ENDER_PEARL)
                .requires(Items.AMETHYST_CLUSTER)
                .unlockedBy("has_nexus_crystal_items", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.DRAGON_BREATH, Items.ENDER_PEARL, Items.AMETHYST_CLUSTER).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PURIFIED_SUN_STONE.get())
                .define('#', ModItems.PURIFIED_SUN_STONE_DUST.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_purified_sun_stone_items", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.PURIFIED_SUN_STONE_DUST.get()).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SUN_FORGE_ITEM.get())
                .define('#', ModBlocks.SUN_STONE_BLOCK.get().asItem())
                .define('$', Blocks.GOLD_BLOCK.asItem())
                .define('%', ModItems.SUN_STONE.get())
                .define('*', ModBlocks.SMOOTH_SHURIMAN_SANDSTONE.get())
                .pattern("$%$")
                .pattern("%#%")
                .pattern("***")
                .unlockedBy("has_sun_forge_items", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.SUN_STONE.get(), ModBlocks.SUN_STONE_BLOCK.get().asItem(), Blocks.GOLD_BLOCK, ModBlocks.SMOOTH_SHURIMAN_SANDSTONE.get()).build()))
                .save(pWriter);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.SHURIMAN_SANDSTONE.get().asItem()), RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SMOOTH_SHURIMAN_SANDSTONE.get(), 0.3f, 200)
                .unlockedBy("has_shuriman_sandstone", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SHURIMAN_SANDSTONE.get().asItem()).build()))
                .save(pWriter);

        // ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLACK_OPAL.get())
        //         .requires(ModBlocks.BLACK_OPAL_BLOCK.get())
        //         .unlockedBy("has_black_opal_block", inventoryTrigger(ItemPredicate.Builder.item()
        //                 .of(ModBlocks.BLACK_OPAL_BLOCK.get()).build()))
        //         .save(consumer);

        // ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BLACK_OPAL_BLOCK.get())
        //         .define('B', ModItems.BLACK_OPAL.get())
        //         .pattern("BBB")
        //         .pattern("BBB")
        //         .pattern("BBB")
        //         .unlockedBy("has_black_opal", inventoryTrigger(ItemPredicate.Builder.item()
        //                 .of(ModItems.BLACK_OPAL.get()).build()))
        //         .save(consumer);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked, String pPackedName, @Nullable String pPackedGroup, String pUnpackedName, @Nullable String pUnpackedGroup) {
        ShapelessRecipeBuilder.shapeless(pUnpackedCategory, pUnpacked, 9).requires(pPacked).group(pUnpackedGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pFinishedRecipeConsumer, new ResourceLocation(RuneterraMod.MOD_ID, pUnpackedName));
        ShapedRecipeBuilder.shaped(pPackedCategory, pPacked).define('#', pUnpacked).pattern("###").pattern("###").pattern("###").group(pPackedGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, new ResourceLocation(RuneterraMod.MOD_ID, pPackedName));
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpackedCategory, pUnpacked, pPackedCategory, pPacked, getSimpleRecipeName(pPacked), (String)null, getSimpleRecipeName(pUnpacked), (String)null);
    }
}
