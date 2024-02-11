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

public class VoltaicCyclosword extends Item implements ICurioItem {

    private static AttributeModifier VOLTAIC_CYCLOSWORD_LETHALITY = new AttributeModifier("voltaic_cyclosword_lethality",
            3, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier VOLTAIC_CYCLOSWORD_DAMAGE = new AttributeModifier("voltaic_cyclosword_damage",
            3, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier VOLTAIC_CYCLOSWORD_HASTE = new AttributeModifier("voltaic_cyclosword_haste",
            15, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private Consumer<LivingHurtEvent> hitEffect = event -> {
        if(event.getSource().getEntity() instanceof Player player && !player.getCooldowns().isOnCooldown(this)){
            event.setAmount(event.getAmount() + 3f);
            player.getCooldowns().addCooldown(this, 120);
        }
    };
    public VoltaicCyclosword(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).hasModifier(VOLTAIC_CYCLOSWORD_LETHALITY)) {
            slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).addTransientModifier(VOLTAIC_CYCLOSWORD_LETHALITY);
        }
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(VOLTAIC_CYCLOSWORD_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(VOLTAIC_CYCLOSWORD_DAMAGE);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(VOLTAIC_CYCLOSWORD_HASTE)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).addTransientModifier(VOLTAIC_CYCLOSWORD_HASTE);
        }
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.addPermaHitEffect("voltaic_cyclosword_damage", hitEffect);
        });
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).hasModifier(VOLTAIC_CYCLOSWORD_LETHALITY)){
            slotContext.entity().getAttribute(ModAttributes.LETHALITY.get()).removeModifier(VOLTAIC_CYCLOSWORD_LETHALITY);
        }
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(VOLTAIC_CYCLOSWORD_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(VOLTAIC_CYCLOSWORD_DAMAGE);
        }
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(VOLTAIC_CYCLOSWORD_HASTE)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).removeModifier(VOLTAIC_CYCLOSWORD_HASTE);
        }
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.removePermaHitEffect("voltaic_cyclosword_damage");
        });
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Every 6 seconds your next attack will deal 3 additional damage").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+3 Attack Damage").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+3 Lethality").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+15 Ability Haste").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
