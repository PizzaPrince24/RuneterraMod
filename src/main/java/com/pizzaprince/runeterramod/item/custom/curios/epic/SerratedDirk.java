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

public class SerratedDirk extends Item implements ICurioItem {

    private static AttributeModifier SERRATED_DIRK_LETHALITY = new AttributeModifier("serrated_dirk_lethality",
            1.5, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier SERRATED_DIRK_DAMAGE = new AttributeModifier("serrated_dirk_damage",
            1, AttributeModifier.Operation.ADDITION);
    public SerratedDirk(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).hasModifier(SERRATED_DIRK_LETHALITY)) {
            slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).addTransientModifier(SERRATED_DIRK_LETHALITY);
        }
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(SERRATED_DIRK_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(SERRATED_DIRK_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).hasModifier(SERRATED_DIRK_LETHALITY)){
            slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).removeModifier(SERRATED_DIRK_LETHALITY);
        }
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(SERRATED_DIRK_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(SERRATED_DIRK_DAMAGE);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("+1.5 Lethality").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+1 Attack Damage").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
