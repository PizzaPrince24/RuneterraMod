package com.pizzaprince.runeterramod.item.custom.curios.epic;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

public class VampiricScepter extends Item implements ICurioItem {

    private static AttributeModifier VAMPIRIC_SCEPTER_DAMAGE = new AttributeModifier("vampiric_scepter_damage",
            1.5, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier VAMPIRIC_SCEPTER_OMNI = new AttributeModifier("vampiric_scepter_omni",
            0.07, AttributeModifier.Operation.ADDITION);
    public VampiricScepter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(VAMPIRIC_SCEPTER_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(VAMPIRIC_SCEPTER_DAMAGE);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.OMNIVAMP.get()).hasModifier(VAMPIRIC_SCEPTER_OMNI)) {
            slotContext.entity().getAttribute(ModAttributes.OMNIVAMP.get()).addTransientModifier(VAMPIRIC_SCEPTER_OMNI);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(VAMPIRIC_SCEPTER_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(VAMPIRIC_SCEPTER_DAMAGE);
        }
        if(slotContext.entity().getAttribute(ModAttributes.OMNIVAMP.get()).hasModifier(VAMPIRIC_SCEPTER_OMNI)){
            slotContext.entity().getAttribute(ModAttributes.OMNIVAMP.get()).removeModifier(VAMPIRIC_SCEPTER_OMNI);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+7% Omnivamp").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+1.5 Attack Damage").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
