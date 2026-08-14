package com.raincat.glasscrafts.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class GlassGrowFertilizerItem extends Item {
    public GlassGrowFertilizerItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();

        if (player != null) {
            player.getCooldowns().addCooldown(this, 15); // 0.75秒短CD
        }

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            int grewCount = 0;
            // 扫描以点击位置为中心 3x3 范围内的农作物并对其使用强力骨粉催熟
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos targetPos = pos.offset(x, 0, z);
                    BlockState state = level.getBlockState(targetPos);
                    
                    // 模拟骨粉使用
                    if (state.getBlock() instanceof net.minecraft.world.level.block.BonemealableBlock bonemealableBlock) {
                        if (bonemealableBlock.isValidBonemealTarget(level, targetPos, state)) {
                            bonemealableBlock.performBonemeal(serverLevel, level.random, targetPos, state);
                            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, 
                                    targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5, 
                                    5, 0.2, 0.2, 0.2, 0.05);
                            grewCount++;
                        }
                    }
                }
            }

            if (grewCount > 0) {
                // 播放清脆的玻璃摇晃和骨粉声
                level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.3F);
                itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
