package com.pizzaprince.runeterramod.item.custom.curios.legendary;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.effect.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

public class SeryldasGrudge extends Item implements ICurioItem {

    private static AttributeModifier SERYLDAS_LETHALITY = new AttributeModifier("seryldas_lethality",
            3, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier SERYLDAS_LETHALITY_MULT = new AttributeModifier("seryldas_lethality_mult",
            0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static AttributeModifier SERYLDAS_DAMAGE = new AttributeModifier("seryldas_damage",
            2, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier SERYLDAS_HASTE = new AttributeModifier("seryldas_haste",
            15, AttributeModifier.Operation.ADDITION);

    private Consumer<LivingHurtEvent> hitEffect = event -> {
        event.getEntity().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 1, true, true, true));
    };
    public SeryldasGrudge(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).hasModifier(SERYLDAS_LETHALITY)) {
            slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).addTransientModifier(SERYLDAS_LETHALITY);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).hasModifier(SERYLDAS_LETHALITY_MULT)) {
            slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).addTransientModifier(SERYLDAS_LETHALITY_MULT);
        }
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(SERYLDAS_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(SERYLDAS_DAMAGE);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(SERYLDAS_HASTE)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).addTransientModifier(SERYLDAS_HASTE);
        }
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.addPermaHitEffect("seryldas_slow", hitEffect);
        });
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).hasModifier(SERYLDAS_LETHALITY)){
            slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).removeModifier(SERYLDAS_LETHALITY);
        }
        if(slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).hasModifier(SERYLDAS_LETHALITY_MULT)){
            slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).removeModifier(SERYLDAS_LETHALITY_MULT);
        }
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(SERYLDAS_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(SERYLDAS_DAMAGE);
        }
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(SERYLDAS_HASTE)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).removeModifier(SERYLDAS_HASTE);
        }
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.removePermaHitEffect("seryldas_slow");
        });
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Dealing damage will slow the target by 30% for 1.5 seconds").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+2 Attack Damage").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+3 Lethality").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+20% Lethality").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+15 Ability Haste").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
