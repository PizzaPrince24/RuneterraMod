package com.pizzaprince.runeterramod.util;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

public class CustomPoisonEffect {

    //Each string in the array represents the resource location for a mob effect, such as minecraft:poison
    //Can also include the strings "amp" or "lengthen" and either increases the amplifier of the previous effect by 1 or double duration of the previous effect
    private ResourceLocation[] effects;
    private String name;
    public static ResourceLocation AMP = new ResourceLocation(RuneterraMod.MOD_ID, "amp");
    public static ResourceLocation LENGTHEN = new ResourceLocation(RuneterraMod.MOD_ID, "lengthen");

    public static ResourceLocation EMPTY = new ResourceLocation(RuneterraMod.MOD_ID, "empty");

    public CustomPoisonEffect(int length){
        effects = new ResourceLocation[length];
        Arrays.fill(effects, EMPTY);
        this.name = "";
    }

    public CustomPoisonEffect(ResourceLocation[] effects, String name){
        this.effects = effects;
        this.name = name;
    }

    public CustomPoisonEffect(int length, ResourceLocation[] effects, String name){
        this.effects = new ResourceLocation[length];
        Arrays.fill(this.effects, EMPTY);
        for(int i = 0; i < this.effects.length; i++){
            if(i < effects.length){
                this.effects[i] = effects[i];
            }
        }
        this.name = name;
    }

    public void addEffect(ResourceLocation location){
        for(int i = 0; i < effects.length; i++){
            if(effects[i] == null){
                effects[i] = location;
            }
        }
    }

    public void removeEffect(){
        for(int i = effects.length-1; i >= 0; i--){
            if(effects[i] != null){
                effects[i] = null;
            }
        }
    }

    public String toString(){
        System.out.println(name);
        for(ResourceLocation location : effects){
            if(location == null) continue;
            if(location.equals(LENGTHEN) || location.equals(AMP) || location.equals(EMPTY)){
                System.out.println(location.getPath());
            } else {
                System.out.println(ForgeRegistries.MOB_EFFECTS.getValue(location).getDescriptionId());
            }
        }
        return "";
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public ResourceLocation[] getEffects(){
        return effects;
    }
}
