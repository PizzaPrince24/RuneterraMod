package com.pizzaprince.runeterramod.item.custom.curios;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.item.custom.curios.SunfireAegisCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class SunfireAegis extends Item implements ICurioItem {

    private static AttributeModifier SUNFIRE_AEGIS_HEALTH = new AttributeModifier("sunfire_aegis_health",
            4, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier SUNFIRE_AEGIS_ARMOR = new AttributeModifier("sunfire_aegis_armor",
            4, AttributeModifier.Operation.ADDITION);
    public SunfireAegis(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        stack.getCapability(SunfireAegisCapabilityProvider.SUNFIRE_AEGIS_CAPABILITY).ifPresent(cap -> {
            cap.tick(slotContext.entity());
        });
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(SUNFIRE_AEGIS_HEALTH)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(SUNFIRE_AEGIS_HEALTH);
        }
        if(!slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(SUNFIRE_AEGIS_ARMOR)) {
            slotContext.entity().getAttribute(Attributes.ARMOR).addTransientModifier(SUNFIRE_AEGIS_ARMOR);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(SUNFIRE_AEGIS_HEALTH)){
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(SUNFIRE_AEGIS_HEALTH);
        }
        if(slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(SUNFIRE_AEGIS_ARMOR)) {
            slotContext.entity().getAttribute(Attributes.ARMOR).removeModifier(SUNFIRE_AEGIS_ARMOR);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Taking or dealing damage causes nearby entities to burn for the next 3 seconds").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+2 Hearts").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+4 Armor").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
