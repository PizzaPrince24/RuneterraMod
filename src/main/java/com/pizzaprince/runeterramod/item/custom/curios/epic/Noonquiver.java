package com.pizzaprince.runeterramod.item.custom.curios.epic;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
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

public class Noonquiver extends Item implements ICurioItem {
    private static AttributeModifier NOONQUIVER_ATTACK_SPEED = new AttributeModifier("noonquiver_attack_speed",
            0.15, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier NOONQUIVER_ATTACK_DAMAGE = new AttributeModifier("noonquiver_attack_damage",
            1.5, AttributeModifier.Operation.ADDITION);
    public Noonquiver(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(NOONQUIVER_ATTACK_SPEED)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(NOONQUIVER_ATTACK_SPEED);
        }
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(NOONQUIVER_ATTACK_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(NOONQUIVER_ATTACK_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(NOONQUIVER_ATTACK_SPEED)){
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).removeModifier(NOONQUIVER_ATTACK_SPEED);
        }
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(NOONQUIVER_ATTACK_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(NOONQUIVER_ATTACK_DAMAGE);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+0.15 Attack Speed").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+1.5 Attack Damage").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
