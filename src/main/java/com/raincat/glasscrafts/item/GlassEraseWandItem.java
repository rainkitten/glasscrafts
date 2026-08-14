package com.raincat.glasscrafts.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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

public class GlassEraseWandItem extends Item {
    public GlassEraseWandItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        player.getCooldowns().addCooldown(this, 20); // 1秒短CD

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            BlockPos playerPos = player.blockPosition();
            int radius = 4;
            int erasedCount = 0;

            // 扫描半径 4 格内的所有液体并直接蒸发/消除，生存铺路神道具
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos targetPos = playerPos.offset(x, y, z);
                        BlockState state = level.getBlockState(targetPos);
                        
                        if (!state.getFluidState().isEmpty()) {
                            level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);
                            serverLevel.sendParticles(ParticleTypes.SMOKE, 
                                    targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5, 
                                    3, 0.1, 0.1, 0.1, 0.02);
                            erasedCount++;
                        }
                    }
                }
            }

            if (erasedCount > 0) {
                // 播放玻璃吸水/气化碎裂的清脆声音
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.8F, 1.6F);
                itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
