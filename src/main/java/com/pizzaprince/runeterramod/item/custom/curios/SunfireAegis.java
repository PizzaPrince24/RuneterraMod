package com.pizzaprince.runeterramod.item.custom.curios;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.item.custom.curios.SunfireAegisCapability;
import com.pizzaprince.runeterramod.ability.item.custom.curios.SunfireAegisCapabilityAttacher;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;
import top.theillusivec4.curios.api.event.CurioEquipEvent;
import top.theillusivec4.curios.api.event.CurioUnequipEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class SunfireAegis extends Item implements ICurioItem {

    private static AttributeModifier SUNFIRE_AEGIS_HEALTH = new AttributeModifier("sunfire_aegis_health",
            5, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier SUNFIRE_AEGIS_ARMOR = new AttributeModifier("sunfire_aegis_armor",
            5, AttributeModifier.Operation.ADDITION);
    public SunfireAegis(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        SunfireAegisCapabilityAttacher.getAbilityItemCapability(stack).ifPresent(ability -> {
            ability.tick(slotContext.entity());
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

    @Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID)
    public class Events {
        @SubscribeEvent
        public static void onHit(LivingHurtEvent event){
            if(event.getEntity() instanceof Player player){
                player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
                    inventory.getCurios().values().forEach(curio -> {
                        for(int slot = 0; slot < curio.getSlots(); slot++){
                            if(curio.getStacks().getStackInSlot(slot).getItem() instanceof SunfireAegis item){
                                SunfireAegisCapabilityAttacher.getAbilityItemCapability(curio.getStacks().getStackInSlot(slot)).ifPresent(ability -> {
                                    ability.startBurn();
                                });
                            }
                        }
                    });
                });
            }

            if(event.getSource().getEntity() instanceof Player player){
                player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
                    inventory.getCurios().values().forEach(curio -> {
                        for(int slot = 0; slot < curio.getSlots(); slot++){
                            if(curio.getStacks().getStackInSlot(slot).getItem() instanceof SunfireAegis){
                                SunfireAegisCapabilityAttacher.getAbilityItemCapability(curio.getStacks().getStackInSlot(slot)).ifPresent(ability -> {
                                    ability.startBurn();
                                });
                            }
                        }
                    });
                });
            }
        }
    }
}
