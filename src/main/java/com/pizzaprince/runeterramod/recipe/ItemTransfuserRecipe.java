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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemTransfuserRecipe implements Recipe<SimpleContainer> {

    public final ResourceLocation id;
    public final ItemStack output;
    public final NonNullList<Ingredient> recipeItems;

    public ItemTransfuserRecipe(ResourceLocation id, ItemStack output,
                                    NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
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

    public static class Type implements RecipeType<ItemTransfuserRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "item_transfusing";
    }

    public static class Serializer implements RecipeSerializer<ItemTransfuserRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(RuneterraMod.MOD_ID, "item_transfusing");

        @Override
        public ItemTransfuserRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new ItemTransfuserRecipe(pRecipeId, output, inputs);
        }

        @Override
        public @Nullable ItemTransfuserRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new ItemTransfuserRecipe(pRecipeId, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ItemTransfuserRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.output, false);
        }
    }
}
