package com.raincat.glasscrafts.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class GlassLensItem extends Item {
    public GlassLensItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        // 赋予玩家 5 秒的冷却时间
        player.getCooldowns().addCooldown(this, 100);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            BlockPos playerPos = player.blockPosition();
            int radius = 10;
            int foundOreCount = 0;

            // 扫描周围 10 格内的珍贵矿石 (钻石、黄金、铁) 并高亮粒子展示
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos scanPos = playerPos.offset(x, y, z);
                        BlockState state = level.getBlockState(scanPos);
                        
                        if (state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.DEEPSLATE_DIAMOND_ORE) ||
                            state.is(Blocks.GOLD_ORE) || state.is(Blocks.DEEPSLATE_GOLD_ORE) ||
                            state.is(Blocks.IRON_ORE) || state.is(Blocks.DEEPSLATE_IRON_ORE) ||
                            state.is(Blocks.EMERALD_ORE) || state.is(Blocks.DEEPSLATE_EMERALD_ORE)) {
                            
                            // 在矿石位置产生闪烁的粒子提示玩家
                            serverLevel.sendParticles(ParticleTypes.GLOW, 
                                    scanPos.getX() + 0.5, scanPos.getY() + 0.5, scanPos.getZ() + 0.5, 
                                    10, 0.2, 0.2, 0.2, 0.05);
                            foundOreCount++;
                        }
                    }
                }
            }

            // 消耗 1 点耐久
            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
