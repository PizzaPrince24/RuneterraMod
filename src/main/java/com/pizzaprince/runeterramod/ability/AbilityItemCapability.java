package com.pizzaprince.runeterramod.ability;

import com.pizzaprince.runeterramod.ability.IAbilityItem;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.item.AbstractAbility;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class AbilityItemCapability {

    private int selectedSlot;

    private List<AbstractAbility> abilities;
    public AbilityItemCapability(ItemStack itemStack) {
        super();

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

    public boolean fireSelectedAbility(Level level, Player player){
        if(this.abilities.get(selectedSlot).getCurrentCooldown() == 0){
            this.abilities.get(selectedSlot).fireAbility(player, level);
            this.abilities.get(selectedSlot).setOnCooldown(player);
            if(this.abilities.get(selectedSlot).getShouldSetStaticCooldown()){
                player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                    cap.setOnStaticCooldown();
                });
            }
            level.playSound(null, player.blockPosition(), this.abilities.get(selectedSlot).getSoundEvent(), SoundSource.PLAYERS, 1.0f, 1.0f);
            return true;
        } else {
            player.sendSystemMessage(Component.literal("Cooldown is " + (getSelectedAbilityCurrentCooldown())/20.0 + " seconds"));
            return false;
        }
    }

    public void tick(){
        for(AbstractAbility ability : abilities){
            ability.setCurrentCooldown(Math.max(0, ability.getCurrentCooldown()-1));
        }
    }
    public void serializeNBT(CompoundTag tag) {

        for(int i = 0; i < abilities.size(); i++){
            tag.putInt("" + i, abilities.get(i).getCurrentCooldown());
        }

        tag.putInt("selected", selectedSlot);
    }

    public void deserializeNBT(CompoundTag nbt) {
        for(int i = 0; i < abilities.size(); i++){
            abilities.get(i).setCurrentCooldown(nbt.getInt("" + i));
        }

        selectedSlot = nbt.getInt("selected");
    }
}
