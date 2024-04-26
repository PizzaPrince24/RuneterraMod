package com.pizzaprince.runeterramod.item.custom.curios.legendary;

import com.pizzaprince.runeterramod.ability.curios.HeartsteelCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;

public class Heartsteel extends Item implements ICurioItem {

    public Heartsteel(Properties pProperties) {
        super(pProperties);
    }

    private static AttributeModifier HEALTH = new AttributeModifier("heartsteel_base_health",
            18, AttributeModifier.Operation.ADDITION);

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(HEALTH)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(HEALTH);
        }
        stack.getCapability(HeartsteelCapabilityProvider.HEARTSTEEL_CAPABILITY).ifPresent(cap -> {
            if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(cap.getModifier())) {
                slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(cap.getModifier());
            }
            cap.setScale((Player)slotContext.entity());
        });
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(HEALTH)){
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(HEALTH);
        }
        stack.getCapability(HeartsteelCapabilityProvider.HEARTSTEEL_CAPABILITY).ifPresent(cap -> {
            if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(cap.getModifier())) {
                slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(cap.getModifier());
            }
            cap.resetScale((Player)slotContext.entity());
        });

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pStack.getCapability(HeartsteelCapabilityProvider.HEARTSTEEL_CAPABILITY).ifPresent(cap -> {
            pTooltipComponents.add(Component.literal("Grants an additional heart for every boss you slay").withStyle(ChatFormatting.RED));
            pTooltipComponents.add(Component.literal("Grants 5% Size for every row of hearts you have").withStyle(ChatFormatting.RED));
            pTooltipComponents.add(Component.literal("+9 Hearts").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.literal("Current Bonus: +" + cap.getStacks() + " Hearts").withStyle(ChatFormatting.GOLD));
        });

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
