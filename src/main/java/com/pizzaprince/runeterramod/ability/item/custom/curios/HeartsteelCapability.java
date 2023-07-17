package com.pizzaprince.runeterramod.ability.item.custom.curios;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class HeartsteelCapability {
    private int stacks = 2;

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
