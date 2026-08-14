package com.raincat.glasscrafts.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.phys.Vec3;

public class GlassWarpCompassItem extends Item {
    public GlassWarpCompassItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            BlockPos respawnPos = serverPlayer.getRespawnPosition();
            
            if (respawnPos != null) {
                ServerLevel respawnLevel = serverPlayer.server.getLevel(serverPlayer.getRespawnDimension());
                if (respawnLevel != null) {
                    // 传送前粒子与音效
                    respawnLevel.playSound(null, respawnPos.getX(), respawnPos.getY(), respawnPos.getZ(),
                            SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 1.0F, 1.0F);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 1.5F);

                    if (level instanceof ServerLevel currentServerLevel) {
                        currentServerLevel.sendParticles(ParticleTypes.EXPLOSION,
                                player.getX(), player.getY() + 1.0, player.getZ(),
                                10, 0.5, 0.5, 0.5, 0.1);
                    }

                    // 直接使用 player 的安全坐标传送，避免直接暴露未暴露的 map 方法
                    serverPlayer.teleportTo(respawnLevel, respawnPos.getX() + 0.5, respawnPos.getY() + 1.0, respawnPos.getZ() + 0.5, serverPlayer.getYRot(), serverPlayer.getXRot());
                    
                    // 传送后产生末影粒子
                    respawnLevel.sendParticles(ParticleTypes.PORTAL,
                            respawnPos.getX() + 0.5, respawnPos.getY() + 1.0, respawnPos.getZ() + 0.5,
                            30, 0.5, 0.5, 0.5, 0.1);

                    // 扣除大额耐久 (10 点) 并可能碎裂
                    itemstack.hurtAndBreak(10, serverPlayer, serverPlayer.getEquipmentSlotForItem(itemstack));
                    return InteractionResultHolder.consume(itemstack);
                }
            }

            // 如果找不到床/复活点，则报错
            serverPlayer.sendSystemMessage(Component.translatable("message.raincat_glasscrafts.warp_compass_no_bed"));
            return InteractionResultHolder.fail(itemstack);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
