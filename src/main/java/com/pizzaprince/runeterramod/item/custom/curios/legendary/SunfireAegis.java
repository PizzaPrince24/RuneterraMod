package com.pizzaprince.runeterramod.item.custom.curios.legendary;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.curios.ImmolationCapabilityProvider;
import com.pizzaprince.runeterramod.item.custom.curios.epic.BamisCinder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

public class SunfireAegis extends Item implements ICurioItem {

    private static AttributeModifier SUNFIRE_AEGIS_HEALTH = new AttributeModifier("sunfire_aegis_health",
            9, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier SUNFIRE_AEGIS_ARMOR = new AttributeModifier("sunfire_aegis_armor",
            5, AttributeModifier.Operation.ADDITION);

    private Consumer<LivingHurtEvent> hitEffect = event -> {
        event.getSource().getEntity().getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
            inventory.getCurios().values().forEach(curio -> {
                for (int slot = 0; slot < curio.getSlots(); slot++) {
                    if (curio.getStacks().getStackInSlot(slot).getItem() instanceof SunfireAegis) {
                        curio.getStacks().getStackInSlot(slot).getCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY).ifPresent(immolation -> {
                            immolation.startBurn();
                        });
                    }
                }
            });
        });
    };
    public SunfireAegis(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        stack.getCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY).ifPresent(cap -> {
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

        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.addPermaHitEffect("sunfire_aegis_burn", hitEffect);
            cap.addPermaOnDamageEffect("sunfire_aegis_burn", hitEffect);
        });
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(SUNFIRE_AEGIS_HEALTH)){
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(SUNFIRE_AEGIS_HEALTH);
        }
        if(slotContext.entity().getAttribute(Attributes.ARMOR).hasModifier(SUNFIRE_AEGIS_ARMOR)) {
            slotContext.entity().getAttribute(Attributes.ARMOR).removeModifier(SUNFIRE_AEGIS_ARMOR);
        }

        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.removePermaHitEffect("sunfire_aegis_burn");
            cap.removePermaOnDamageEffect("sunfire_aegis_burn");
        });
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Taking or dealing damage causes nearby entities to burn for 2 damage/second for the next 3 seconds").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+4.5 Hearts").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+5 Armor").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
