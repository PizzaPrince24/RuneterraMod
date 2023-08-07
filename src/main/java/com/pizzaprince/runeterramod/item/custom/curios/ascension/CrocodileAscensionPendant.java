package com.pizzaprince.runeterramod.item.custom.curios.ascension;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.CapSyncS2CPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CrocodileAscensionPendant extends Item implements ICurioItem {

    public CrocodileAscensionPendant(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.setCrocodileAscended(true);
        });
        ModPackets.sendToClients(new CapSyncS2CPacket(slotContext.entity()));
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.setCrocodileAscended(false);
        });
        ModPackets.sendToClients(new CapSyncS2CPacket(slotContext.entity()));
    }
}
