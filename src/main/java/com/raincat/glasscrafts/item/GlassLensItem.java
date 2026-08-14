package com.raincat.glasscrafts.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.*;

public class GlassLensItem extends Item {
    public GlassLensItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        player.getCooldowns().addCooldown(this, 15); // 0.75秒短CD

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.8F);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            BlockPos playerPos = player.blockPosition();
            int radiusXZ = 32; // 大搜索半径 32 格
            int radiusY = 24;

            Set<BlockPos> visited = new HashSet<>();
            List<BlockPos> oreClusterCenters = new ArrayList<>();

            // 1. 扫描周围大范围矿石并按连通矿脉聚类（一个矿物群只取一个中心）
            for (int x = -radiusXZ; x <= radiusXZ; x++) {
                for (int y = -radiusY; y <= radiusY; y++) {
                    for (int z = -radiusXZ; z <= radiusXZ; z++) {
                        BlockPos pos = playerPos.offset(x, y, z);
                        if (visited.contains(pos)) continue;

                        BlockState state = level.getBlockState(pos);
                        Vector3f color = getOreColor(state);

                        if (color != null) {
                            // 发现新矿脉，通过广度优先搜索找到该矿脉的所有块并计算中心
                            BlockPos clusterCenter = findClusterCenter(level, pos, visited);
                            oreClusterCenters.add(clusterCenter);
                        }
                    }
                }
            }

            // 2. 从玩家位置向每个矿物群中心发射受矿物颜色影响的连贯粒子线条
            Vec3 startVec = player.getEyePosition().add(0, -0.2, 0);

            for (BlockPos center : oreClusterCenters) {
                BlockState state = level.getBlockState(center);
                Vector3f color = getOreColor(state);
                if (color == null) color = new Vector3f(1.0F, 1.0F, 1.0F);

                DustParticleOptions dustParticle = new DustParticleOptions(color, 1.2F);
                Vec3 endVec = new Vec3(center.getX() + 0.5, center.getY() + 0.5, center.getZ() + 0.5);

                double distance = startVec.distanceTo(endVec);
                int steps = Math.max(5, (int) (distance * 2.5)); // 密集连贯线条

                for (int i = 0; i <= steps; i++) {
                    double t = (double) i / steps;
                    double px = startVec.x + (endVec.x - startVec.x) * t;
                    double py = startVec.y + (endVec.y - startVec.y) * t;
                    double pz = startVec.z + (endVec.z - startVec.z) * t;

                    // 穿墙直接发送给玩家
                    serverLevel.sendParticles(serverPlayer, dustParticle, true, px, py, pz, 1, 0, 0, 0, 0);
                }
            }

            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    // 根据矿物类型获取对应的RGB粒子颜色
    private Vector3f getOreColor(BlockState state) {
        if (state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.DEEPSLATE_DIAMOND_ORE)) {
            return new Vector3f(0.2F, 0.9F, 1.0F); // 青蓝色 (钻石)
        } else if (state.is(Blocks.GOLD_ORE) || state.is(Blocks.DEEPSLATE_GOLD_ORE) || state.is(Blocks.NETHER_GOLD_ORE)) {
            return new Vector3f(1.0F, 0.85F, 0.1F); // 璀璨黄色 (黄金)
        } else if (state.is(Blocks.IRON_ORE) || state.is(Blocks.DEEPSLATE_IRON_ORE)) {
            return new Vector3f(0.85F, 0.65F, 0.45F); // 橙褐色 (铁矿)
        } else if (state.is(Blocks.COAL_ORE) || state.is(Blocks.DEEPSLATE_COAL_ORE)) {
            return new Vector3f(0.15F, 0.15F, 0.15F); // 黑色 (煤炭)
        } else if (state.is(Blocks.EMERALD_ORE) || state.is(Blocks.DEEPSLATE_EMERALD_ORE)) {
            return new Vector3f(0.1F, 1.0F, 0.3F); // 翠绿色 (绿宝石)
        } else if (state.is(Blocks.ANCIENT_DEBRIS)) {
            return new Vector3f(0.6F, 0.35F, 0.25F); // 紫褐色 (远古残骸)
        } else if (state.is(Blocks.LAPIS_ORE) || state.is(Blocks.DEEPSLATE_LAPIS_ORE)) {
            return new Vector3f(0.1F, 0.2F, 1.0F); // 深蓝色 (青金石)
        } else if (state.is(Blocks.REDSTONE_ORE) || state.is(Blocks.DEEPSLATE_REDSTONE_ORE)) {
            return new Vector3f(1.0F, 0.1F, 0.1F); // 鲜红色 (红石)
        } else if (state.is(Blocks.COPPER_ORE) || state.is(Blocks.DEEPSLATE_COPPER_ORE)) {
            return new Vector3f(0.9F, 0.45F, 0.2F); // 紫铜色 (铜矿)
        }
        return null;
    }

    // 广度优先搜索 (BFS) 计算矿脉中心
    private BlockPos findClusterCenter(Level level, BlockPos startPos, Set<BlockPos> visited) {
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(startPos);
        visited.add(startPos);

        long sumX = 0, sumY = 0, sumZ = 0;
        int count = 0;

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            sumX += current.getX();
            sumY += current.getY();
            sumZ += current.getZ();
            count++;

            for (BlockPos neighbor : BlockPos.betweenClosed(current.offset(-1, -1, -1), current.offset(1, 1, 1))) {
                BlockPos nPos = neighbor.immutable();
                if (!visited.contains(nPos)) {
                    BlockState nState = level.getBlockState(nPos);
                    if (getOreColor(nState) != null) {
                        visited.add(nPos);
                        queue.add(nPos);
                    }
                }
            }
        }

        return new BlockPos((int) (sumX / count), (int) (sumY / count), (int) (sumZ / count));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<net.minecraft.network.chat.Component> tooltipComponents, net.minecraft.world.item.TooltipFlag tooltipFlag) {
        tooltipComponents.add(net.minecraft.network.chat.Component.translatable(this.getDescriptionId() + ".desc").withStyle(net.minecraft.ChatFormatting.GRAY));
    }
}
