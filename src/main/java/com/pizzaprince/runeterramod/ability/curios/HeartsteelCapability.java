package com.pizzaprince.runeterramod.ability.curios;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.api.ScaleTypes;

public class HeartsteelCapability {
    private int stacks = 9;

    private AttributeModifier modifier;

    public HeartsteelCapability(){
        modifier = createModifier();
    }

    public void serializeNBT(CompoundTag nbt) {
        nbt.putInt("stacks", stacks);
        nbt.put("modifier", modifier.save());
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.stacks = nbt.getInt("stacks");
        this.modifier = AttributeModifier.load(nbt.getCompound("modifier"));
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
        float scale = (float)(Math.floor(player.getAttributeValue(Attributes.MAX_HEALTH)/20)-1)*0.05f;
        ScaleTypes.BASE.getScaleData(player).setTargetScale(ScaleTypes.BASE.getScaleData(player).getTargetScale() + scale);
    }

    public void resetScale(Player player){
        float scale = (float)(Math.floor(Math.max((player.getAttributeValue(Attributes.MAX_HEALTH)/20)-1, 0)))*0.05f;
        ScaleTypes.BASE.getScaleData(player).setTargetScale(ScaleTypes.BASE.getScaleData(player).getTargetScale() - scale);
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
