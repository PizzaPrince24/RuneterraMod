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

public class GuinsoosRageblade extends Item implements ICurioItem {

    private static AttributeModifier GUINSOOS_ATTACK_SPEED = new AttributeModifier("guinsoos_attack_speed",
            0.35, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier GUINSOOS_ABILITY_POWER = new AttributeModifier("guinsoos_ability_power",
            2, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier GUINSOOS_ATTACK_DAMAGE = new AttributeModifier("guinsoos_attack_damage",
            2, AttributeModifier.Operation.ADDITION);

    private Consumer<LivingHurtEvent> hitEffect = event -> {
        event.getEntity().hurt(event.getEntity().level().damageSources().magic(), 2);
        if(event.getSource().getEntity() instanceof Player player && !player.getCooldowns().isOnCooldown(this)){
            player.getCooldowns().addCooldown(this, 80);
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
                cap.applyHitEffects(event);
            });
        }
    };
    public GuinsoosRageblade(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(GUINSOOS_ATTACK_SPEED)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(GUINSOOS_ATTACK_SPEED);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(GUINSOOS_ABILITY_POWER)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(GUINSOOS_ABILITY_POWER);
        }
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(GUINSOOS_ATTACK_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(GUINSOOS_ATTACK_DAMAGE);
        }
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.addPermaHitEffect("guinsoos_double_hit", hitEffect);
        });
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(GUINSOOS_ATTACK_SPEED)){
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).removeModifier(GUINSOOS_ATTACK_SPEED);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(GUINSOOS_ABILITY_POWER)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(GUINSOOS_ABILITY_POWER);
        }
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(GUINSOOS_ATTACK_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(GUINSOOS_ATTACK_DAMAGE);
        }
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.removePermaHitEffect("guinsoos_double_hit");
        });
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Every 4 second your next attack will deal on-hit effects twice").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+0.35 Attack Speed").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+2 Ability Power").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+2 Attack Damage").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
