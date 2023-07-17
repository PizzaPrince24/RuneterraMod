package com.pizzaprince.runeterramod.block.entity.custom;

import com.pizzaprince.runeterramod.block.entity.ModBlockEntities;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.BlockEntityItemStackSyncS2CPacket;
import com.pizzaprince.runeterramod.recipe.ItemTransfuserRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public abstract class AbstractItemTransuferEntity extends BlockEntity {

    private int progress = 0;

    private int maxProgress = 360;

    private final ItemStackHandler itemHandler = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                ModPackets.sendToClients(new BlockEntityItemStackSyncS2CPacket(this, worldPosition));
            }
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    public AbstractItemTransuferEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SHURIMAN_ITEM_TRANSFUSER_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> AbstractItemTransuferEntity.this.progress;
                    case 1 -> AbstractItemTransuferEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> AbstractItemTransuferEntity.this.progress = pValue;
                    case 1 -> AbstractItemTransuferEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("item_tranfuser.progress", this.progress);;
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("item_tranfuser.progress");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, AbstractItemTransuferEntity entity) {
        if(level.isClientSide()) return;

        if(hasRecipe(entity) && entity.progress < 200){
            entity.progress++;
            setChanged(level, blockPos, blockState);
            if(entity.progress >= 200){
                craftItem(entity);
            }
        } else if(entity.progress >= 200) {
            entity.progress++;
            if(entity.progress >= entity.maxProgress){
                entity.resetProgress();
            }
            setChanged(level, blockPos, blockState);
        } else {
            entity.resetProgress();
            setChanged(level, blockPos, blockState);
        }
        level.setBlockAndUpdate(blockPos, entity.getBlockState());
        level.sendBlockUpdated(blockPos, entity.getBlockState(), entity.getBlockState(), 3);
    }

    private static boolean hasRecipe(AbstractItemTransuferEntity entity) {
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<ItemTransfuserRecipe> recipe = level.getRecipeManager().getRecipeFor(ItemTransfuserRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent() && inventory.getItem(3).isEmpty();

    }

    public void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(AbstractItemTransuferEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<ItemTransfuserRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ItemTransfuserRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)) {
            int amountToExtract = recipe.get().recipeItems.size();
            for(int i = 0; i < amountToExtract; i++){
                pEntity.itemHandler.extractItem(i, 1, false);
            }
            pEntity.itemHandler.setStackInSlot(3, new ItemStack(recipe.get().output.getItem()));
        }
    }

    public ItemStack getRenderStack(int slot){
        return itemHandler.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        setChanged();
        return saveWithoutMetadata();    // okay to send entire inventory on chunk load
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        setChanged();
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
}
