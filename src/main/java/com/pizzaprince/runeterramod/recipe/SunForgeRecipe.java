package com.pizzaprince.runeterramod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SunForgeRecipe implements Recipe<SimpleContainer> {

    public final ResourceLocation id;
    public final ItemStack output;
    public final NonNullList<Ingredient> recipeItems;

    public final int sunEnergyRequired;

    public SunForgeRecipe(ResourceLocation id, ItemStack output,
                          NonNullList<Ingredient> recipeItems, int sunEnergy){
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.sunEnergyRequired = sunEnergy;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;

        int[] checked = new int[recipeItems.size()];

        for(int i = 0; i < checked.length; i++){
            checked[i] = -1;
        }

        for(int i = 0; i < recipeItems.size(); i++){
            Ingredient ing = recipeItems.get(i);
            for(int j = 0; j < recipeItems.size(); j++){
                boolean flag = false;
                for(int n : checked){
                    if(n == j){
                        flag = true;
                    }
                }
                if(flag) continue;
                if(ing.test(pContainer.getItem(j))){
                    checked[i] = j;
                    break;
                }
            }
        }

        boolean matches = true;

        for(int i = 0; i < checked.length; i++){
            if(checked[i] == -1){
                matches = false;
            }
        }

        return matches;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SunForgeRecipe>{
        private Type() {}
        public static final SunForgeRecipe.Type INSTANCE = new SunForgeRecipe.Type();
        public static final String ID = "sun_forge";
    }

    public static class Serializer implements RecipeSerializer<SunForgeRecipe>{

        public static final SunForgeRecipe.Serializer INSTANCE = new SunForgeRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(RuneterraMod.MOD_ID, "sun_forge");

        @Override
        public SunForgeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);
            int energy = GsonHelper.getAsInt(pSerializedRecipe, "energy");

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new SunForgeRecipe(pRecipeId, output, inputs, energy);
        }

        @Override
        public @Nullable SunForgeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            int sunEnergyRequired = pBuffer.readInt();
            return new SunForgeRecipe(pRecipeId, output, inputs, sunEnergyRequired);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SunForgeRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.output, false);
            pBuffer.writeInt(pRecipe.sunEnergyRequired);
        }
    }
}
