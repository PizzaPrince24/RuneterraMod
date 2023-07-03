package com.pizzaprince.runeterramod.ability.item;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.IAbilityItem;
import dev._100media.capabilitysyncer.core.ItemStackCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Abs;

import java.util.List;

public class AbilityItemCapability extends ItemStackCapability {

    private int selectedSlot;

    private List<AbstractAbility> abilities;
    public AbilityItemCapability(ItemStack itemStack) {
        super(itemStack);

        if(itemStack.getItem() instanceof IAbilityItem item){
            this.abilities = item.getAbilities();
        }

        this.selectedSlot = 0;
    }

    public AbstractAbility getSelectedAbility(){
        return abilities.get(selectedSlot);
    }

    public void nextAbility(){
        selectedSlot++;
        if(selectedSlot >= abilities.size()){
            selectedSlot = 0;
        }
    }

    public int getSelectedAbilityCurrentCooldown(){
        return this.getSelectedAbility().getCurrentCooldown();
    }

    public int getSelectedAbilityMaxCooldown(){
        return this.getSelectedAbility().getMaxCooldown();
    }

    public boolean fireSelectedAbility(Level level, LivingEntity entity){
        if(this.abilities.get(selectedSlot).getCurrentCooldown() == 0){
            this.abilities.get(selectedSlot).fireAbility(entity, level);
            this.abilities.get(selectedSlot).setOnCooldown();
            return true;
        } else {
            if(entity instanceof Player player){
                player.sendSystemMessage(Component.literal("Cooldown is " + (getSelectedAbilityCurrentCooldown())/20.0 + " seconds"));
            }
            return false;
        }
    }

    public void tick(){
        for(AbstractAbility ability : abilities){
            ability.setCurrentCooldown(Math.max(0, ability.getCurrentCooldown()-1));
        }
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag tag = new CompoundTag();

        for(int i = 0; i < abilities.size(); i++){
            tag.putInt("" + i, abilities.get(i).getCurrentCooldown());
        }

        tag.putInt("selected", selectedSlot);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        for(int i = 0; i < abilities.size(); i++){
            abilities.get(i).setCurrentCooldown(nbt.getInt("" + i));
        }

        selectedSlot = nbt.getInt("selected");
    }
}
