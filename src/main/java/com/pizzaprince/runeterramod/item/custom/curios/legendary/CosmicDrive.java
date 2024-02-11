package com.pizzaprince.runeterramod.item.custom.curios.legendary;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.util.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
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

public class CosmicDrive extends Item implements ICurioItem {

    private static AttributeModifier COSMIC_DRIVE_AP = new AttributeModifier("cosmic_drive_ap",
            4, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier COSMIC_DRIVE_HEALTH = new AttributeModifier("cosmic_drive_health",
            5, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier COSMIC_DRIVE_HASTE = new AttributeModifier("cosmic_drive_haste",
            25, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier COSMIC_DRIVE_MOVE_SPEED = new AttributeModifier("cosmic_drive_move_speed",
            0.05, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private Consumer<LivingHurtEvent> hitEffect = event -> {
        if(event.getSource().getEntity() instanceof LivingEntity entity && event.getSource().is(ModTags.DamageTypes.MAGIC)){
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, true, true, true));
        }
    };
    public CosmicDrive(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(COSMIC_DRIVE_AP)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(COSMIC_DRIVE_AP);
        }
        if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(COSMIC_DRIVE_HEALTH)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(COSMIC_DRIVE_HEALTH);
        }
        if(!slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(COSMIC_DRIVE_MOVE_SPEED)) {
            slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(COSMIC_DRIVE_MOVE_SPEED);
        }
        if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(COSMIC_DRIVE_HASTE)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).addTransientModifier(COSMIC_DRIVE_HASTE);
        }
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.addPermaHitEffect("rylais_slow", hitEffect);
        });
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(COSMIC_DRIVE_AP)){
            slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).removeModifier(COSMIC_DRIVE_AP);
        }
        if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(COSMIC_DRIVE_HEALTH)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(COSMIC_DRIVE_HEALTH);
        }
        if(slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(COSMIC_DRIVE_MOVE_SPEED)) {
            slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(COSMIC_DRIVE_MOVE_SPEED);
        }
        if(slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(COSMIC_DRIVE_HASTE)) {
            slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).removeModifier(COSMIC_DRIVE_HASTE);
        }
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.removePermaHitEffect("rylais_slow");
        });
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Dealing any magic damage will grant Speed I for 2 seconds").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+4 Ability Power").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+2.5 Hearts").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+25 Ability Haste").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+5% Movement Speed").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
