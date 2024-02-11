package com.pizzaprince.runeterramod.item.custom.curios.legendary;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.curios.HeartsteelCapabilityProvider;
import com.pizzaprince.runeterramod.ability.curios.ImmolationCapabilityProvider;
import com.pizzaprince.runeterramod.ability.curios.RodOfAgesCapabilityProvider;
import com.pizzaprince.runeterramod.ability.curios.WarmogsCapabilityProvider;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.function.Consumer;

public class RodOfAges extends Item implements ICurioItem {

    private Consumer<LivingHurtEvent> hitEffect = event -> {
        event.getSource().getEntity().getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
            inventory.getCurios().values().forEach(curio -> {
                for (int slot = 0; slot < curio.getSlots(); slot++) {
                    if (curio.getStacks().getStackInSlot(slot).getItem() instanceof RodOfAges) {
                        curio.getStacks().getStackInSlot(slot).getCapability(RodOfAgesCapabilityProvider.CAPABILITY).ifPresent(cap -> {
                            cap.setInCombat();
                        });
                    }
                }
            });
        });
    };
    public RodOfAges(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        stack.getCapability(RodOfAgesCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            cap.tick(slotContext.entity());
        });
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        stack.getCapability(RodOfAgesCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(cap.getHealthMod())) {
                slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(cap.getHealthMod());
            }
            if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(cap.getApMod())) {
                slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(cap.getApMod());
            }
            if(!slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(cap.getHasteMod())) {
                slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).addTransientModifier(cap.getHasteMod());
            }
        });
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.addPermaHitEffect("rod_of_ages_combat", hitEffect);
            cap.addPermaOnDamageEffect("rod_of_ages_combat", hitEffect);
        });
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        stack.getCapability(RodOfAgesCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(cap.getHealthMod())) {
                slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(cap.getHealthMod());
            }
            if(slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(cap.getApMod())) {
                slotContext.entity().getAttribute(ModAttributes.ABILITY_POWER.get()).removeModifier(cap.getApMod());
            }
            if(slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).hasModifier(cap.getHasteMod())) {
                slotContext.entity().getAttribute(ModAttributes.ABILITY_HASTE.get()).removeModifier(cap.getHasteMod());
            }
        });
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.removePermaHitEffect("rod_of_ages_combat");
            cap.removePermaOnDamageEffect("rod_of_ages_combat");
        });
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pStack.getCapability(RodOfAgesCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            pTooltipComponents.add(Component.literal("Grants permanent stats for every minute in combat, up to 10 total minutes").withStyle(ChatFormatting.RED));
            pTooltipComponents.add(Component.literal(String.format("+%.1f Hearts", ((float)cap.getStacks())/2f)).withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.literal(String.format("+%.1f Ability Power", ((float)cap.getStacks())/2f)).withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.literal(String.format("+%d Ability Power", cap.getStacks()*2)).withStyle(ChatFormatting.GOLD));
        });

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
