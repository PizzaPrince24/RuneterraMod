package com.pizzaprince.runeterramod.networking.packet;

import com.pizzaprince.runeterramod.block.entity.custom.AbstractItemTransuferEntity;
import com.pizzaprince.runeterramod.block.entity.custom.SunDiskAltarEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class BlockEntityItemStackSyncS2CPacket {
	private final ItemStackHandler itemStackHandler;
	private final BlockPos pos;

	public BlockEntityItemStackSyncS2CPacket(ItemStackHandler itemStackHandler, BlockPos pos) {
		this.itemStackHandler = itemStackHandler;
		this.pos = pos;
	}

	public BlockEntityItemStackSyncS2CPacket(FriendlyByteBuf buf) {
		List<ItemStack> collection = buf.readCollection(ArrayList::new, FriendlyByteBuf::readItem);
		itemStackHandler = new ItemStackHandler(collection.size());
		for (int i = 0; i < collection.size(); i++) {
			itemStackHandler.insertItem(i, collection.get(i), false);
		}

		this.pos = buf.readBlockPos();
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		Collection<ItemStack> list = new ArrayList<>();
		for(int i = 0; i < itemStackHandler.getSlots(); i++) {
			list.add(itemStackHandler.getStackInSlot(i));
		}

		buf.writeCollection(list, FriendlyByteBuf::writeItem);
		buf.writeBlockPos(pos);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof SunDiskAltarEntity blockEntity) {
				blockEntity.setHandler(this.itemStackHandler);
			}
			if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof AbstractItemTransuferEntity blockEntity) {
				blockEntity.setHandler(this.itemStackHandler);
			}
		});
		return true;
	}

}
