package com.pizzaprince.runeterramod.item.custom.curios.epic;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class Kindlegem extends Item implements ICurioItem {

    private static AttributeModifier KINDLEGEM_HEALTH = new AttributeModifier("kindlegem_health",
            2, AttributeModifier.Operation.ADDITION);

    private int numAbilityHaste = 10;
    public Kindlegem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(KINDLEGEM_HEALTH)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(KINDLEGEM_HEALTH);
        }
        if(slotContext.entity() instanceof Player player){
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                cap.addAbilityHaste(numAbilityHaste);
            });
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(KINDLEGEM_HEALTH)){
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(KINDLEGEM_HEALTH);
        }
        if(slotContext.entity() instanceof Player player){
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                cap.removeAbilityHaste(numAbilityHaste);
            });
        }
    }
}
