package com.pizzaprince.runeterramod.item.custom.curios;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.item.custom.curios.SunfireAegisCapabilityAttacher;
import com.pizzaprince.runeterramod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class Rylais extends Item implements ICurioItem {

    private static AttributeModifier RYLAIS_DAMAGE = new AttributeModifier("rylais_damage",
            3, AttributeModifier.Operation.ADDITION);
    public Rylais(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(RYLAIS_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(RYLAIS_DAMAGE);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(RYLAIS_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(RYLAIS_DAMAGE);
        }
    }

  // @Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID)
  // public class Events {
  //     @SubscribeEvent
  //     public static void onHit(LivingHurtEvent event){
  //         if(event.getSource().getEntity() instanceof Player player){
  //             player.getCapability(CuriosCapability.INVENTORY).ifPresent(inventory -> {
  //                 inventory.getCurios().values().forEach(curio -> {
  //                     for(int slot = 0; slot < curio.getSlots(); slot++){
  //                         if(curio.getStacks().getStackInSlot(slot).getItem() instanceof Rylais){
  //                             event.getEntity().addEffect(new MobEffectInstance(ModEffects.RYLAIS_SLOW.get(),
  //                                     30, 1, true, true, true));
  //                             System.out.println("Applied Rylai's Slow");
  //                         }
  //                     }
  //                 });
  //             });
  //         }
  //     }
  // }
}
