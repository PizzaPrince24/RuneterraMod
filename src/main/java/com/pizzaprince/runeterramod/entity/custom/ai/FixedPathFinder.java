package com.pizzaprince.runeterramod.entity.custom.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
credit to BobMowzie from Mowzie's mobs
https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs
 */
public class FixedPathFinder extends PathFinder {
    public FixedPathFinder(NodeEvaluator processor, int maxVisitedNodes) {
        super(processor, maxVisitedNodes);
    }

    @Nullable
    public Path findPath(PathNavigationRegion regionIn, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
        Path path = super.findPath(regionIn, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);
        return path == null ? null : new FixedPathFinder.PatchedPath(path);
    }

    static class PatchedPath extends Path {
        public PatchedPath(Path original) {
            super(copyPathPoints(original), original.getTarget(), original.canReach());
        }

        public Vec3 getEntityPosAtNode(Entity entity, int index) {
            Node point = this.getNode(index);
            double d0 = (double)point.x + (double) Mth.floor(entity.getBbWidth() + 1.0F) * 0.5;
            double d1 = (double)point.y;
            double d2 = (double)point.z + (double)Mth.floor(entity.getBbWidth() + 1.0F) * 0.5;
            return new Vec3(d0, d1, d2);
        }

        private static List<Node> copyPathPoints(Path original) {
            List<Node> points = new ArrayList();

            for(int i = 0; i < original.getNodeCount(); ++i) {
                points.add(original.getNode(i));
            }

            return points;
        }
    }
}
