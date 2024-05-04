package com.pizzaprince.runeterramod.util;

import com.pizzaprince.runeterramod.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModPotionUtils {

    public static ItemStack makeElixir(ItemStack oldPotion, Level level){
        int elixirLevel = oldPotion.getOrCreateTag().getInt("elixirLevel");
        ItemStack newPotion = new ItemStack(oldPotion.getItem());
        List<MobEffectInstance> oldEffects = PotionUtils.getMobEffects(oldPotion);
        ArrayList<MobEffectInstance> newEffects = new ArrayList<>();
        oldEffects.forEach(effect -> {
            newEffects.add(new MobEffectInstance(effect.getEffect(), 36000*(elixirLevel+1), effect.getAmplifier()+1, effect.isAmbient(), effect.isVisible(), effect.showIcon()));
        });
        PotionUtils.setCustomEffects(newPotion, newEffects);
        MobEffectInstance firstPositiveEffect = getFirstPositiveEffectOrFirst(newEffects);
        ResourceLocation nameLocation = ForgeRegistries.MOB_EFFECTS.getKey(firstPositiveEffect.getEffect());
        newPotion.setHoverName(Component.literal((elixirLevel == 1 ? "Strong " : "") + "Elixir of ").append(Component.translatable("effect."+nameLocation.getNamespace()+"."+nameLocation.getPath())));
        newPotion.getOrCreateTag().putInt("elixirLevel", elixirLevel+1);
        newPotion.getOrCreateTag().putInt("CustomPotionColor", firstPositiveEffect.getEffect().getColor());
        return newPotion;
    }

    public static ItemStack makeScorpionCharm(ItemStack potion, Level level){
        ItemStack charm = new ItemStack(ModItems.SCORPION_CHARM.get());
        List<MobEffectInstance> oldEffects = PotionUtils.getMobEffects(potion);
        ArrayList<MobEffectInstance> newEffects = new ArrayList<>();
        oldEffects.forEach(effect -> {
            newEffects.add(new MobEffectInstance(effect.getEffect(), 5, effect.getAmplifier()+1, effect.isAmbient(), effect.isVisible(), effect.showIcon()));
        });
        PotionUtils.setCustomEffects(charm, newEffects);
        MobEffectInstance firstPositiveEffect = getFirstPositiveEffectOrFirst(newEffects);
        ResourceLocation nameLocation = ForgeRegistries.MOB_EFFECTS.getKey(firstPositiveEffect.getEffect());
        charm.setHoverName(Component.literal( "Charm of ").append(Component.translatable("effect."+nameLocation.getNamespace()+"."+nameLocation.getPath())));
        charm.getOrCreateTag().putInt("CustomPotionColor", firstPositiveEffect.getEffect().getColor());
        return charm;
    }

    private static MobEffectInstance getFirstPositiveEffectOrFirst(List<MobEffectInstance> list){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getEffect().getCategory() == MobEffectCategory.BENEFICIAL){
                return list.get(i);
            }
        }
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getEffect().getCategory() == MobEffectCategory.NEUTRAL){
                return list.get(i);
            }
        }
        return list.get(0);
    }
}
