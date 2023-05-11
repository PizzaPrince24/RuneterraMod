package com.pizzaprince.runeterramod.block.entity.custom;

import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.block.entity.ModBlockEntities;
import com.pizzaprince.runeterramod.client.screen.SunDiskAltarMenu;
import com.pizzaprince.runeterramod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SunDiskAltarEntity extends BlockEntity implements IAnimatable, MenuProvider {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final ItemStackHandler itemHandler = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    private int sunBlocks = 0;
    private int maxSunBlocks = 2500;

    public SunDiskAltarEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SUN_DISK_ALTAR_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SunDiskAltarEntity.this.progress;
                    case 1 -> SunDiskAltarEntity.this.maxProgress;
                    case 2 -> SunDiskAltarEntity.this.sunBlocks;
                    case 3 -> SunDiskAltarEntity.this.maxSunBlocks;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SunDiskAltarEntity.this.progress = value;
                    case 1 -> SunDiskAltarEntity.this.maxProgress = value;
                    case 2 -> SunDiskAltarEntity.this.sunBlocks = value;
                    case 3 -> SunDiskAltarEntity.this.maxSunBlocks = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<SunDiskAltarEntity>(this, "controller", 20, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event){
        if(this.serializeNBT().getInt("sun_disk_altar.progress") > 0){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sun_disk_altar.active_transfusing", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else {
            System.out.println(this.serializeNBT().getInt("sun_disk_altar.progress"));
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sun_disk_altar.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Sun Disk Altar");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SunDiskAltarMenu(pContainerId, pPlayerInventory, this, this.data);
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
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("sun_disk_altar.progress", this.progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("sun_disk_altar.progress");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SunDiskAltarEntity entity) {
        if(level.isClientSide()) return;

        if(hasRecipe(entity)){
            entity.progress++;
            setChanged(level, blockPos, blockState);
            if(entity.progress >= entity.maxProgress){
                craftItem(entity);
            }
            System.out.println(entity.progress);
        } else {
            entity.resetProgress();
            setChanged(level, blockPos, blockState);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(SunDiskAltarEntity entity) {
        if(hasRecipe(entity)) {
            entity.itemHandler.extractItem(0, 1, false);
            entity.sunBlocks++;

            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(SunDiskAltarEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        boolean hasCorrectBlockInFirstSlot = entity.itemHandler.getStackInSlot(0).getItem() == ModBlocks.SUN_DISK_SHARD.get().asItem() ||
                entity.itemHandler.getStackInSlot(0).getItem() == Blocks.GOLD_BLOCK.asItem();

        boolean hasCorrectItemInSecondSlot = entity.itemHandler.getStackInSlot(1).getItem() == ModItems.SUN_STONE.get();

        return hasCorrectBlockInFirstSlot && hasCorrectItemInSecondSlot && entity.sunBlocks < entity.maxSunBlocks;
    }
}
