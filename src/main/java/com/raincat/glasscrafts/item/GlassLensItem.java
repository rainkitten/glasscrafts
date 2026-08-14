package com.raincat.glasscrafts.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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

public class GlassLensItem extends Item {
    public GlassLensItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        player.getCooldowns().addCooldown(this, 100);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.6F);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            BlockPos playerPos = player.blockPosition();
            int radius = 12;

            // 扫描周围 12 格内的珍贵矿石
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos scanPos = playerPos.offset(x, y, z);
                        BlockState state = level.getBlockState(scanPos);
                        
                        if (state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.DEEPSLATE_DIAMOND_ORE) ||
                            state.is(Blocks.GOLD_ORE) || state.is(Blocks.DEEPSLATE_GOLD_ORE) ||
                            state.is(Blocks.IRON_ORE) || state.is(Blocks.DEEPSLATE_IRON_ORE) ||
                            state.is(Blocks.EMERALD_ORE) || state.is(Blocks.DEEPSLATE_EMERALD_ORE) ||
                            state.is(Blocks.ANCIENT_DEBRIS)) {
                            
                            // 直接发送发光的END_ROD和GLOW粒子给该玩家，使其穿墙清晰可见
                            double px = scanPos.getX() + 0.5;
                            double py = scanPos.getY() + 0.5;
                            double pz = scanPos.getZ() + 0.5;

                            serverLevel.sendParticles(serverPlayer, ParticleTypes.END_ROD, true,
                                    px, py, pz, 15, 0.3, 0.3, 0.3, 0.02);
                            serverLevel.sendParticles(serverPlayer, ParticleTypes.GLOW, true,
                                    px, py, pz, 15, 0.3, 0.3, 0.3, 0.05);
                        }
                    }
                }
            }

            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
