package com.pizzaprince.runeterramod.block.entity.custom;

import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.block.entity.ModBlockEntities;
import com.pizzaprince.runeterramod.client.screen.SunDiskAltarMenu;
import com.pizzaprince.runeterramod.client.screen.SunForgeMenu;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.BlockEntityItemStackSyncS2CPacket;
import com.pizzaprince.runeterramod.recipe.ItemTransfuserRecipe;
import com.pizzaprince.runeterramod.recipe.SunForgeRecipe;
import com.pizzaprince.runeterramod.util.ModTags;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
import java.util.Optional;

public class SunForgeEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final RawAnimation nightAnim = RawAnimation.begin().then("animation.sun_forge.day_to_night", Animation.LoopType.PLAY_ONCE)
            .then("animation.sun_forge.idle_night", Animation.LoopType.LOOP);

    private final RawAnimation forging = RawAnimation.begin().then("animation.sun_forge.forging", Animation.LoopType.LOOP);

    private final RawAnimation nightToDay = RawAnimation.begin().then("animation.sun_forge.night_to_day", Animation.LoopType.PLAY_ONCE);

    private final RawAnimation idle = RawAnimation.begin().then("animation.sun_forge.idle", Animation.LoopType.LOOP);

    private final AnimationController controller = new AnimationController(this, "controller", 10, state -> {
        if(state.getController().getCurrentRawAnimation() == null){
            state.getController().setAnimation(idle);
            return PlayState.CONTINUE;
        }
        long time = (level.getDayTime() + 2000) % 24000;
        if(time > 16000){
            if(!state.getController().getCurrentRawAnimation().equals(nightAnim)) state.getController().setAnimation(nightAnim);
            return PlayState.CONTINUE;
        } else {
            if(state.getController().getCurrentRawAnimation().equals(nightAnim)
                    || (state.getController().getCurrentRawAnimation().equals(nightToDay) && !state.getController().hasAnimationFinished())){
                state.getController().setAnimation(nightToDay);
                return PlayState.CONTINUE;
            } else {
                if (this.getUpdatePacket().getTag().getInt("sun_forge.progress") > 0) {
                    state.getController().setAnimation(forging);
                    return PlayState.CONTINUE;
                } else {
                    state.getController().setAnimation(idle);
                    return PlayState.CONTINUE;
                }
            }
        }
    });

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 6000;

    private int sunEnergy = 0;

    private int maxSunEnergy = 100000;

    private ItemStack toCraft = ItemStack.EMPTY;

    private int guiAnimCraft = 0;

    private final ItemStackHandler itemHandler = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                ModPackets.sendToClients(new BlockEntityItemStackSyncS2CPacket(this, worldPosition));
            }
        }
    };
    public SunForgeEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SUN_FORGE_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SunForgeEntity.this.progress;
                    case 1 -> SunForgeEntity.this.maxProgress;
                    case 2 -> SunForgeEntity.this.sunEnergy;
                    case 3 -> SunForgeEntity.this.maxSunEnergy;
                    case 4 -> SunForgeEntity.this.guiAnimCraft;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SunForgeEntity.this.progress = value;
                    case 1 -> SunForgeEntity.this.maxProgress = value;
                    case 2 -> SunForgeEntity.this.sunEnergy = value;
                    case 3 -> SunForgeEntity.this.maxSunEnergy = value;
                    case 4 -> SunForgeEntity.this.guiAnimCraft = value;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SunForgeEntity entity) {
        if(level.isClientSide()) return;

        entity.sunEnergy = Math.min(entity.sunEnergy+(entity.getSunPower()/10), entity.maxSunEnergy);

        checkAddSunEnergy(entity);

        if(getRecipe(entity).isPresent()){
            SunForgeRecipe recipe = getRecipe(entity).get();
            if(entity.toCraft.getItem() != recipe.output.getItem()){
                entity.progress = 0;
                entity.maxProgress = recipe.sunEnergyRequired;
                entity.toCraft = recipe.output;
                entity.guiAnimCraft = 0;
            } else if(entity.sunEnergy >= 20 && entity.getSunPower() > 0){
                entity.guiAnimCraft++;
                if(entity.guiAnimCraft >= 19) entity.guiAnimCraft = 0;
                int newProgress = (int)(20f*((float)entity.getSunPower()/30f));
                entity.progress+=newProgress;
                entity.sunEnergy = Math.max(0, entity.sunEnergy-newProgress);
                if(entity.progress >= entity.maxProgress){
                    craftItem(entity);
                    entity.progress = 0;
                    entity.toCraft = ItemStack.EMPTY;
                    entity.guiAnimCraft = 0;
                }
            }
        } else if(hasSunStoneItemToRepair(entity) && entity.sunEnergy > 20 && entity.getSunPower() > 0){
            entity.guiAnimCraft++;
            if(entity.guiAnimCraft >= 19) entity.guiAnimCraft = 0;
            int newProgress = (int)(20f*((float)entity.getSunPower()/30f));
            entity.sunEnergy = Math.max(0, entity.sunEnergy-newProgress);
            ItemStack item = entity.itemHandler.getStackInSlot(2);
            item.setDamageValue(Math.max(0, item.getDamageValue()-newProgress));
            entity.progress = 1;
        } else {
            entity.progress = 0;
            entity.toCraft = ItemStack.EMPTY;
            entity.guiAnimCraft = 0;
        }
        setChanged(level, blockPos, blockState);
        level.setBlockAndUpdate(blockPos, entity.getBlockState());
        level.sendBlockUpdated(blockPos, entity.getBlockState(), entity.getBlockState(), 3);
    }

    private static boolean hasSunStoneItemToRepair(SunForgeEntity entity) {
        ItemStack item = entity.itemHandler.getStackInSlot(2);
        return item.is(ModTags.Items.SUN_FORGE_REPAIRABLE) && item.isDamaged();
    }

    private static void craftItem(SunForgeEntity entity){
        Optional<SunForgeRecipe> recipe = getRecipe(entity);
        if(recipe.isPresent()){
            CompoundTag nbt = null;
            if(entity.itemHandler.getStackInSlot(1).hasTag()) nbt = entity.itemHandler.getStackInSlot(1).getTag();
            if(entity.itemHandler.getStackInSlot(0).hasTag()) nbt = entity.itemHandler.getStackInSlot(0).getTag();
            entity.itemHandler.extractItem(0, 1, false);
            entity.itemHandler.extractItem(1, 1, false);
            entity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().output.getItem()));
            if(nbt != null) entity.itemHandler.getStackInSlot(2).setTag(nbt);
        }
    }

    private static Optional<SunForgeRecipe> getRecipe(SunForgeEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(SunForgeRecipe.Type.INSTANCE, inventory, level);
    }

    public static void checkAddSunEnergy(SunForgeEntity entity){
        if(entity.sunEnergy < entity.maxSunEnergy) {
            if (entity.itemHandler.getStackInSlot(3).getItem() == ModItems.SUN_STONE.get()) {
                entity.itemHandler.extractItem(3, 1, false);
                entity.sunEnergy = Math.min(entity.sunEnergy + 2000, entity.maxSunEnergy);
            }
            if (entity.itemHandler.getStackInSlot(3).getItem() == ModBlocks.SUN_STONE_BLOCK.get().asItem()) {
                entity.itemHandler.extractItem(3, 1, false);
                entity.sunEnergy = Math.min(entity.sunEnergy + 18000, entity.maxSunEnergy);
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SunForgeMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("sun_forge.progress", this.progress);
        pTag.putInt("sun_forge.sunEnergy", this.sunEnergy);
        pTag.put("sun_forge.toCraft", this.toCraft.serializeNBT());
        pTag.putInt("sun_forge.maxProgress", this.maxProgress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("sun_forge.progress");
        sunEnergy = pTag.getInt("sun_forge.sunEnergy");
        toCraft = ItemStack.of(pTag.getCompound("sun_forge.toCraft"));
        maxProgress = pTag.getInt("sun_forge.maxProgress");
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

    @Override
    public Component getDisplayName() {
        return Component.literal("Sun Forge");
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(controller);
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

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public ItemStack getRenderStack(int slot){
        return itemHandler.getStackInSlot(slot);
    }
}
