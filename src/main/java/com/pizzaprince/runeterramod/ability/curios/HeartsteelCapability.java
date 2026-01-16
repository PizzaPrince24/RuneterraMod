package com.pizzaprince.runeterramod.ability.curios;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.api.ScaleTypes;

public class HeartsteelCapability {
    private int stacks = 0;
    private int ticks = 0;
    private AttributeModifier modifier;
    private float savedScale = 0;

    public HeartsteelCapability(){
        modifier = createModifier();
    }

    public void serializeNBT(CompoundTag nbt) {
        nbt.putInt("stacks", stacks);
        nbt.put("modifier", modifier.save());
        nbt.putFloat("savedScale", savedScale);
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.stacks = nbt.getInt("stacks");
        this.modifier = AttributeModifier.load(nbt.getCompound("modifier"));
        this.savedScale = nbt.getFloat("savedScale");
    }

    public void tick(LivingEntity entity){
        if(entity.level().isClientSide) return;
        if(++ticks > 100){
            ticks = 0;
            if(!entity.getAttribute(Attributes.MAX_HEALTH).hasModifier(getModifier())) {
                entity.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(getModifier());
                savedScale = 0;
            }
            setScale((Player) entity);
        }
    }

    public void addStacks(Player player) {
        if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(modifier)){
            player.getAttribute(Attributes.MAX_HEALTH).removeModifier(modifier);
        }

        this.stacks++;

        modifier = createModifier();

        player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(modifier);
        setScale(player);
    }

    public void setScale(Player player){
        System.out.println("savedScale is " + savedScale);
        float scaleToAdd = (float)(Math.floor(player.getAttributeValue(Attributes.MAX_HEALTH)/player.getAttributeBaseValue(Attributes.MAX_HEALTH))-1)*0.05f;
        System.out.println("calculated scale is " + scaleToAdd);
        ScaleTypes.BASE.getScaleData(player).setTargetScale(ScaleTypes.BASE.getScaleData(player).getTargetScale() + (scaleToAdd - savedScale));
        System.out.println("scale added " + (scaleToAdd - savedScale));
        savedScale = scaleToAdd;
        System.out.println("savedScale is now " + savedScale);
    }

    public void resetScale(Player player){
        ScaleTypes.BASE.getScaleData(player).setTargetScale(ScaleTypes.BASE.getScaleData(player).getTargetScale() - savedScale);
        System.out.println("removing " + savedScale + " scale");
        savedScale = 0;
        System.out.println(savedScale);
    }

    public void resetSavedScale(){
        savedScale = 0;
        System.out.println("THIS SHOULD NOT BE HERE");
    }

    public int getStacks(){
        return stacks;
    }
    public AttributeModifier getModifier(){
        return this.modifier;
    }

    private AttributeModifier createModifier(){
        return new AttributeModifier("heartsteel_health", 2 * stacks, AttributeModifier.Operation.ADDITION);
    }

}
