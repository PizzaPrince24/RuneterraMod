package com.pizzaprince.runeterramod.block.entity.custom;

import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.block.custom.SunDiskAltar;
import com.pizzaprince.runeterramod.block.entity.ModBlockEntities;
import com.pizzaprince.runeterramod.client.screen.SunDiskAltarMenu;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.BlockEntityItemStackSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
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
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class SunDiskAltarEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ItemStackHandler itemHandler = new ItemStackHandler(2){
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
    private int progress = -1;
    private int maxProgress = 780;
    private int sunBlocks = 0;
    private int maxSunBlocks = 50;

    private int blocksLeft = 0;

    private ArrayList<BlockPos> diskShape;

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
                    case 4 -> SunDiskAltarEntity.this.blocksLeft;
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
                    case 4 -> SunDiskAltarEntity.this.blocksLeft = value;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private PlayState predicate(AnimationState state){
        if(this.getUpdatePacket().getTag().getInt("sun_disk_altar.progress") >= 0){
            state.getController().setAnimation(RawAnimation.begin().then("animation.sun_disk_altar.active_transfusing", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        state.getController().setAnimation(RawAnimation.begin().then("animation.sun_disk_altar.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
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
        pTag.putInt("sun_disk_altar.blocks", this.sunBlocks);
        pTag.putInt("sun_disk_altar.blocks_left", this.blocksLeft);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("sun_disk_altar.progress");
        sunBlocks = pTag.getInt("sun_disk_altar.blocks");
        blocksLeft = pTag.getInt("sun_disk_altar.blocks_left");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public int getSunPower(){
        long time = (level.getDayTime() + 2000) % 24000;
        if(time > 16000) return 0;
        if(time <= 11000 && time >= 5000) return 30;
        int power = 0;
        if(time < 5000){
            power = (int) Mth.clampedLerp(0, 30, (time / 5000.0));
        } else if (time > 11000){
            power = (int)Mth.clampedLerp(0, 30, ((16000.0 - time) / 5000.0));
        }
        return power;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SunDiskAltarEntity entity) {
        if(level.isClientSide()) return;

        if(hasRecipe(entity)){
            entity.progress += (entity.getSunPower() / 3) + 1;
            setChanged(level, blockPos, blockState);
            if(entity.progress >= entity.maxProgress){
                craftItem(entity);
            }
        } else {
            entity.trueResetProgress();
            setChanged(level, blockPos, blockState);
        }
        level.setBlockAndUpdate(blockPos, entity.getBlockState());
        level.sendBlockUpdated(blockPos, entity.getBlockState(), entity.getBlockState(), 3);
    }

    private void createDiskShape(SunDiskAltarEntity entity){
        if(entity.diskShape == null){
            BlockPos origin = entity.getBlockPos();
            ArrayList<BlockPos> shape = new ArrayList<BlockPos>();
            int radiusL = 40;
            int radiusM = 25;
            int radiusS = 10;
            BlockPos centerR = origin.offset(0, 5+radiusL, 0);
            boolean ns;
            if(entity.getBlockState().getValue(SunDiskAltar.FACING) == Direction.NORTH || entity.getBlockState().getValue(SunDiskAltar.FACING) == Direction.SOUTH){
                ns = true;
            } else {
                ns = false;
            }

            //big disk
            addBlockToShape(shape, centerR, radiusL, ns, 0);

            //medium disks
            addBlockToShape(shape, centerR, radiusM, ns, 1);
            addBlockToShape(shape, centerR, radiusM, ns, -1);

            //small disks
            addBlockToShape(shape, centerR, radiusS, ns, 2);
            addBlockToShape(shape, centerR, radiusS, ns, -2);

            entity.diskShape = shape;
        }
    }

    private void addBlockToShape(ArrayList<BlockPos> shape, BlockPos centerR, int radius, boolean ns, int offset){
        double currentDegree = 0;
        double step = Math.PI / 300;
        while(currentDegree < 2*Math.PI){
            for(int r = radius; r > 0; r--){
                int x = (int)(Math.cos(currentDegree)*r);
                int y = (int)(Math.sin(currentDegree)*r);
                BlockPos toAdd = centerR.offset(ns ? x : offset, y, ns ? offset : x);
                if(!shape.stream().anyMatch(pos -> pos.getX() == toAdd.getX() && pos.getY() == toAdd.getY() && pos.getZ() == toAdd.getZ())){
                    shape.add(toAdd);
                }
            }
            currentDegree += step;
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void trueResetProgress() {
        this.progress = -1;
    }

    private static void craftItem(SunDiskAltarEntity entity) {
        if(hasRecipe(entity)) {
            if(entity.blocksLeft == 0){
                if(entity.itemHandler.getStackInSlot(1).getItem() == ModItems.SUN_STONE.get()){
                    entity.itemHandler.extractItem(1, 1, false);
                    entity.blocksLeft += 50;
                } else {
                    return;
                }
            }
            entity.itemHandler.extractItem(0, 1, false);
            entity.buildDisk(entity);
            entity.sunBlocks++;
            entity.blocksLeft--;


            entity.resetProgress();
        }
    }

    private void buildDisk(SunDiskAltarEntity entity) {

        Level level = entity.level;
        entity.createDiskShape(entity);
        int step = (int) ((double)(entity.diskShape.size()) / (double)entity.maxSunBlocks);
        int start = (int)((double)(entity.diskShape.size()) * ((double)entity.sunBlocks / (double)entity.maxSunBlocks));
        if(entity.maxSunBlocks - entity.sunBlocks > 1) {
            for (int i = start; i < start + step + 10; i++) {
                if (level.getBlockState(entity.diskShape.get(i)).is(Blocks.AIR)) {
                    level.setBlockAndUpdate(entity.diskShape.get(i), ModBlocks.SUN_DISK_BLOCK.get().defaultBlockState());
                }
            }
        } else {
            for (int i = start; i < entity.diskShape.size(); i++) {
                if (level.getBlockState(entity.diskShape.get(i)).is(Blocks.AIR)) {
                    level.setBlockAndUpdate(entity.diskShape.get(i), ModBlocks.SUN_DISK_BLOCK.get().defaultBlockState());
                }
            }
        }
    }

    public static boolean hasRecipe(SunDiskAltarEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        boolean hasCorrectBlockInFirstSlot = entity.itemHandler.getStackInSlot(0).getItem() == ModBlocks.SUN_DISK_SHARD.get().asItem() ||
                entity.itemHandler.getStackInSlot(0).getItem() == Blocks.GOLD_BLOCK.asItem();

        boolean hasCorrectItemInSecondSlot = entity.itemHandler.getStackInSlot(1).getItem() == ModItems.SUN_STONE.get();

        return hasCorrectBlockInFirstSlot && hasCorrectItemInSecondSlot && entity.sunBlocks < entity.maxSunBlocks && entity.getSunPower() > 0;
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

    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(0);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }


}
