package com.pizzaprince.runeterramod.item.custom.curios.ascension;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.CapSyncS2CPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class TurtleAscensionPendant extends Item implements ICurioItem {

    public TurtleAscensionPendant(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.setTurtleAscended(true);
        });
        ModPackets.sendToClients(new CapSyncS2CPacket(slotContext.entity()));
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.setTurtleAscended(false);
        });
        ModPackets.sendToClients(new CapSyncS2CPacket(slotContext.entity()));
    }
}
