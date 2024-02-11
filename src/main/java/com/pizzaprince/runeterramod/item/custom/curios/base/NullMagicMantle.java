package com.pizzaprince.runeterramod.item.custom.curios.base;

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

public class NullMagicMantle extends Item implements ICurioItem {

    private static AttributeModifier NULL_MAGIC_MANTLE_ARMOR = new AttributeModifier("null_magic_mantle_armor",
            1, AttributeModifier.Operation.ADDITION);
    public NullMagicMantle(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.MAGIC_RESIST.get()).hasModifier(NULL_MAGIC_MANTLE_ARMOR)) {
            slotContext.entity().getAttribute(ModAttributes.MAGIC_RESIST.get()).addTransientModifier(NULL_MAGIC_MANTLE_ARMOR);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.MAGIC_RESIST.get()).hasModifier(NULL_MAGIC_MANTLE_ARMOR)) {
            slotContext.entity().getAttribute(ModAttributes.MAGIC_RESIST.get()).removeModifier(NULL_MAGIC_MANTLE_ARMOR);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+1 Magic Resist").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
