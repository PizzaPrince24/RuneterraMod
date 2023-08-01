package com.pizzaprince.runeterramod.item.custom.curios.epic;

import com.pizzaprince.runeterramod.ability.curios.ImmolationCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class BamisCinder extends Item implements ICurioItem {

    private static AttributeModifier BAMIS_CINDER_HEALTH = new AttributeModifier("bamis_cinder_health",
            2, AttributeModifier.Operation.ADDITION);
    public BamisCinder(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        stack.getCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY).ifPresent(cap -> {
            cap.tick(slotContext.entity());
        });
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(BAMIS_CINDER_HEALTH)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(BAMIS_CINDER_HEALTH);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(BAMIS_CINDER_HEALTH)){
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(BAMIS_CINDER_HEALTH);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Taking or dealing damage causes nearby entities to burn for 1 damage/second for the next 3 seconds").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+1 Heart").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
