package com.pizzaprince.runeterramod.item.custom.curios.ascension;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.CapSyncS2CPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class EagleAscensionPendant extends Item implements ICurioItem {
    public EagleAscensionPendant(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.ascend((Player) slotContext.entity(), AscendantType.EAGLE);
        });
        ModPackets.sendToClients(new CapSyncS2CPacket(slotContext.entity()));
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.descend((Player) slotContext.entity());
        });
        ModPackets.sendToClients(new CapSyncS2CPacket(slotContext.entity()));
    }
}
