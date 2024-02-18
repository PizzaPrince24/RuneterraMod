package com.pizzaprince.runeterramod.item.custom.curios.epic;

import com.pizzaprince.runeterramod.effect.ModAttributes;
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

public class LostChapter extends Item implements ICurioItem {

    private static AttributeModifier LOST_CHAPTER_AP = new AttributeModifier("lost_chapter_ap",
            2, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier LOST_CHAPTER_HASTE = new AttributeModifier("lost_chapter_haste",
            15, AttributeModifier.Operation.ADDITION);
    public LostChapter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(LOST_CHAPTER_AP)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(LOST_CHAPTER_AP);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(LOST_CHAPTER_HASTE)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).addTransientModifier(LOST_CHAPTER_HASTE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(LOST_CHAPTER_AP)){
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).removeModifier(LOST_CHAPTER_AP);
        }
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(LOST_CHAPTER_HASTE)){
            slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).removeModifier(LOST_CHAPTER_HASTE);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+2 Ability Power").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+15 Ability Haste").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
