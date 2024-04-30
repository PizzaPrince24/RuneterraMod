package com.pizzaprince.runeterramod.ability.ascendent;

import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Set;

public class TurtleAscendant extends BaseAscendant{

    private static AttributeModifier HEALTH = new AttributeModifier("turtle_ascendant_health",
            40, AttributeModifier.Operation.ADDITION);
    private float shellHealth = 500f;
    private boolean isShellBroken = false;
    private int brokenShellTimer = 0;
    private String lastDimension = "";
    private Vec3 lastPos = new Vec3(0, 0, 0);
    private boolean inShell = false;
    private int distance = 0;
    private int shellWidth = 5;
    private int resistanceCount = 0;
    private AttributeModifier bonusResist = new AttributeModifier("turtle_ascendant_resistances", 10, AttributeModifier.Operation.ADDITION);

    /*
    stored as bytes for easy nbt stuff
    0 for false; 1 for true
    upgrades:
        0: Have over 200 health
        1: Have over 70 armor
        2: Have over 70 magic resist
        3: Be over 3x normal size
        4: Have your shell take over 100 damage from a single hit without breaking
     */
    private byte[] shellUpgrades = new byte[5];

    @Override
    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("shellHealth", shellHealth);
        nbt.putBoolean("isShellBroken", isShellBroken);
        nbt.putInt("brokenShellTimer", brokenShellTimer);
        nbt.putBoolean("inShell", inShell);
        nbt.putString("lastDimension", lastDimension);
        nbt.putDouble("lastPosX", lastPos.x);
        nbt.putDouble("lastPosY", lastPos.y);
        nbt.putDouble("lastPosZ", lastPos.z);
        nbt.putInt("distance", distance);
        nbt.putInt("shellWidth", shellWidth);
        nbt.putByteArray("shellUpgrades", shellUpgrades);
    }

    @Override
    public void loadNBTData(CompoundTag nbt) {
        shellHealth = nbt.getFloat("shellHealth");
        isShellBroken = nbt.getBoolean("isShellBroken");
        brokenShellTimer = nbt.getInt("brokenShellTimer");
        inShell = nbt.getBoolean("inShell");
        lastDimension = nbt.getString("lastDimension");
        lastPos = new Vec3(nbt.getDouble("lastPosX"), nbt.getDouble("lastPosY"), nbt.getDouble("lastPosZ"));
        distance = nbt.getInt("distance");
        shellWidth = nbt.getInt("shellWidth");
        shellUpgrades = nbt.getByteArray("shellUpgrades");
    }

    @Override
    public void tick(ServerPlayer player) {
        brokenShellTimer = Math.max(0, --brokenShellTimer);
        if(tickCount % 10 == 0 && outOfCombat == 0 && brokenShellTimer == 0) {
            damageShell(-0.2f, player);
        }
        if(tickCount % 20 == 0){
            resistanceCount++;
            if(resistanceCount >= 10 && !isShellBroken){
                resistanceCount = 0;
                addResistances(player);
            }
            inShell = player.level().dimension() == ModDimensions.TURTLE_SHELL_SPACE_DIM;
        }
    }

    private void addResistances(ServerPlayer player){
        double amount = calculateAddedResistances(player);
        if(amount != bonusResist.getAmount()){
            if(player.getAttribute(Attributes.ARMOR).hasModifier(bonusResist)) {
                player.getAttribute(Attributes.ARMOR).removeModifier(bonusResist);
            }
            if(player.getAttribute(ModAttributes.MAGIC_RESIST.get()).hasModifier(bonusResist)) {
                player.getAttribute(ModAttributes.MAGIC_RESIST.get()).removeModifier(bonusResist);
            }
            bonusResist = new AttributeModifier("turtle_ascendant_resistances", amount, AttributeModifier.Operation.ADDITION);
            player.getAttribute(Attributes.ARMOR).addTransientModifier(bonusResist);
            player.getAttribute(ModAttributes.MAGIC_RESIST.get()).addTransientModifier(bonusResist);
        }
    }

    private double calculateAddedResistances(ServerPlayer player){
        double amount = 10;
        amount += Math.max((ScaleTypes.BASE.getScaleData(player).getTargetScale() - 1) * 10, 0);
        return amount;
    }

    private void makeShell(ServerLevel level){
        for(int x = 160*distance; x < 160*distance + shellWidth; x++){
            for(int y = 99; y < 99+shellWidth; y++){
                for(int z = 0; z < shellWidth; z++){
                    if(y==99 || y==99+shellWidth-1){
                        level.setBlockAndUpdate(new BlockPos(x, y, z), ModBlocks.SHELL_BLOCK.get().defaultBlockState());
                    } else if(x % 160 == 0 || z == 0 || x % 160 == shellWidth-1 || z == shellWidth-1){
                        level.setBlockAndUpdate(new BlockPos(x, y, z), ModBlocks.SHELL_BLOCK.get().defaultBlockState());
                    }
                }
            }
        }
    }

    private void removeShellBlocks(ServerLevel level){
        for(int x = 160*distance; x <= 160*distance + shellWidth; x++){
            for(int y = 99; y < 99+shellWidth; y++){
                for(int z = 0; z <= shellWidth; z++){
                    BlockPos pos = new BlockPos(x, y, z);
                    if(level.getBlockState(pos).is(ModBlocks.SHELL_BLOCK.get())){
                        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                }
            }
        }
    }

    private void updateShellSize(ServerPlayer player){
        if(player.getAttributeValue(Attributes.MAX_HEALTH) >= 200){
            shellUpgrades[0] = 1;
        }
        if(player.getAttributeValue(Attributes.ARMOR) >= 70){
            shellUpgrades[1] = 1;
        }
        if(player.getAttributeValue(ModAttributes.MAGIC_RESIST.get()) >= 70){
            shellUpgrades[2] = 1;
        }
        if(ScaleTypes.BASE.getScaleData(player).getScale() >= 3){
            shellUpgrades[3] = 1;
        }
        int count = 0;
        for(byte upgrade : shellUpgrades){
            if(upgrade >= 1) count++;
        }
        switch(count){
            case 0 -> shellWidth = 5;
            case 1 -> shellWidth = 6;
            case 2 -> shellWidth = 7;
            case 3 -> shellWidth = 9;
            case 4 -> shellWidth = 12;
            case 5 -> shellWidth = 16;
        }
    }

    public void changeDimension(ServerPlayer player){
        if(inShell){
            player.getServer().getAllLevels().forEach(level -> {
                if(level.dimension().toString().equals(lastDimension)){
                    inShell = false;
                    player.teleportTo(level, lastPos.x, lastPos.y, lastPos.z, Set.of(), player.getYHeadRot(), player.getXRot());
                }
            });
        } else if(!isShellBroken) {
            ServerLevel shellDim = player.getServer().getLevel(ModDimensions.TURTLE_SHELL_SPACE_DIM);
            if(distance == 0){
                int dist = 1;
                while(!isFreeSpot(shellDim, dist)) {
                    dist++;
                }
                distance = dist;
                updateShellSize(player);
                makeShell(shellDim);
                inShell = true;
                lastPos = player.position();
                lastDimension = player.level().dimension().toString();
                ChunkPos a = new ChunkPos(distance*10, 0);
                shellDim.setChunkForced(a.x, a.z, true);
                shellDim.getChunkSource().addRegionTicket(TicketType.FORCED, a, 3, a, true);
                player.teleportTo(shellDim, 160*distance + (shellWidth/2f), 100, shellWidth/2f, Set.of(), player.getYHeadRot(), player.getXRot());
            } else {
                inShell = true;
                lastPos = player.position();
                lastDimension = player.level().dimension().toString();
                int oldShellWidth = shellWidth;
                updateShellSize(player);
                if(shellWidth > oldShellWidth){
                    removeShellBlocks(shellDim);
                    makeShell(shellDim);
                }
                player.teleportTo(shellDim, 160*distance + (shellWidth/2f), 100, shellWidth/2f, Set.of(), player.getYHeadRot(), player.getXRot());
            }
        }
    }

    private boolean isFreeSpot(ServerLevel level, int dist){
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(dist*160 + 3, 99, 3);
        for(int y = 0; y < 7; y++){
            if(level.getBlockState(blockPos) != Blocks.AIR.defaultBlockState()){
                return false;
            }
            blockPos.move(0, 1, 0);
        }
        return true;
    }

    @Override
    public void onAscend(Player player) {
        if(!player.getAttribute(Attributes.MAX_HEALTH).hasModifier(HEALTH)) {
            player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(HEALTH);
            player.heal((float)HEALTH.getAmount());
        }
        if(!player.getAttribute(Attributes.ARMOR).hasModifier(bonusResist)){
            player.getAttribute(Attributes.ARMOR).addTransientModifier(bonusResist);
        }
        if(!player.getAttribute(ModAttributes.MAGIC_RESIST.get()).hasModifier(bonusResist)){
            player.getAttribute(ModAttributes.MAGIC_RESIST.get()).addTransientModifier(bonusResist);
        }
    }

    @Override
    public void onDescend(Player player) {
        if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(HEALTH)) {
            player.getAttribute(Attributes.MAX_HEALTH).removeModifier(HEALTH);
        }
        if(player.getAttribute(Attributes.ARMOR).hasModifier(bonusResist)) {
            player.getAttribute(Attributes.ARMOR).removeModifier(bonusResist);
        }
        if(player.getAttribute(ModAttributes.MAGIC_RESIST.get()).hasModifier(bonusResist)) {
            player.getAttribute(ModAttributes.MAGIC_RESIST.get()).removeModifier(bonusResist);
        }
    }

    public void damageShell(float damage, ServerPlayer player){
        if(damage < 0 && shellHealth == 0){
            onAscend(player);
            isShellBroken = false;
        }
        shellHealth = Mth.clamp(shellHealth - damage, 0, 500);
        if(shellHealth <= 0) {
            shellHealth = 0;
            isShellBroken = true;
            onDescend(player);
            brokenShellTimer = 12000;
        } else if(damage > 100){
            shellUpgrades[4] = 1;
        }
    }

    public void calculateShellDamage(LivingDamageEvent event){
        if(event.getEntity().level().isClientSide()) return;
        ServerPlayer player = (ServerPlayer) event.getEntity();
        float damageToShell = event.getAmount() * 0.6f;
        damageShell(damageToShell, player);
        event.setAmount(event.getAmount() - damageToShell);
    }

    public boolean isShellBroken(){
        return isShellBroken;
    }
}
