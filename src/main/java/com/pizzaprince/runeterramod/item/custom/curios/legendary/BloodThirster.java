package com.pizzaprince.runeterramod.item.custom.curios.legendary;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.effect.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.function.Consumer;

public class BloodThirster extends Item implements ICurioItem {

    private static AttributeModifier BLOODTHIRSTER_DAMAGE = new AttributeModifier("bloodthirster_damage",
            3, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier BLOODTHIRSTER_OMNI = new AttributeModifier("bloodthirster_omni",
            0.18, AttributeModifier.Operation.ADDITION);

    public BloodThirster(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(BLOODTHIRSTER_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(BLOODTHIRSTER_DAMAGE);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.OMNIVAMP.get()).hasModifier(BLOODTHIRSTER_OMNI)) {
            slotContext.entity().getAttribute(ModAttributes.OMNIVAMP.get()).addTransientModifier(BLOODTHIRSTER_OMNI);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(BLOODTHIRSTER_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(BLOODTHIRSTER_DAMAGE);
        }
        if(slotContext.entity().getAttribute(ModAttributes.OMNIVAMP.get()).hasModifier(BLOODTHIRSTER_OMNI)){
            slotContext.entity().getAttribute(ModAttributes.OMNIVAMP.get()).removeModifier(BLOODTHIRSTER_OMNI);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+3 Attack Damage").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+18% Omnivamp").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
