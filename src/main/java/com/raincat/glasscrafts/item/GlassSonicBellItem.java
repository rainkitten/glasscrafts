package com.raincat.glasscrafts.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class GlassSonicBellItem extends Item {
    public GlassSonicBellItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        player.getCooldowns().addCooldown(this, 50); // 2.5秒冷却

        // 播放清脆的玻璃钟声音
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 1.2F, 1.6F);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            BlockPos pos = player.blockPosition();
            double range = 12.0;

            // 1. 在半径 12 格内释放清脆的声波粒子
            for (int i = 0; i < 30; i++) {
                double angle = (i * 2 * Math.PI) / 30;
                double dx = Math.cos(angle) * 3.0;
                double dz = Math.sin(angle) * 3.0;
                serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, 
                        player.getX() + dx, player.getY() + 0.5, player.getZ() + dz, 
                        1, 0, 0, 0, 0);
            }

            // 2. 击退范围内的怪物并造成 3 点击退伤害
            AABB area = new AABB(pos).inflate(range);
            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, 
                    entity -> entity != player && entity.isAlive());

            for (LivingEntity target : targets) {
                double dx = target.getX() - player.getX();
                double dz = target.getZ() - player.getZ();
                double distance = Math.max(0.1, Math.sqrt(dx * dx + dz * dz));
                
                // 根据距离算击退力度
                double force = (range - distance) / range; // 越近击退越远
                target.knockback(force * 1.5, -dx, -dz);
                
                // 造成 3 点基础魔法风暴伤害
                target.hurt(level.damageSources().magic(), 3.0F);
            }

            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
