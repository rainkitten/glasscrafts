package com.raincat.glasscrafts.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class GlassMagnetItem extends Item {
    public GlassMagnetItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        // 只有放在主手或副手才起作用
        if (!(entity instanceof Player player)) {
            return;
        }

        boolean active = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
        if (!active) {
            return;
        }

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel && level.getGameTime() % 10 == 0) {
            double range = 8.0;
            AABB area = new AABB(player.blockPosition()).inflate(range);
            
            // 吸引掉落物
            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, area);
            boolean pulled = false;
            for (ItemEntity item : items) {
                if (item.isAlive() && !item.hasPickUpDelay()) {
                    double dx = player.getX() - item.getX();
                    double dy = (player.getY() + 0.5) - item.getY();
                    double dz = player.getZ() - item.getZ();
                    double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    if (dist > 0.5) {
                        item.setDeltaMovement(dx / dist * 0.45, dy / dist * 0.45, dz / dist * 0.45);
                        pulled = true;
                    }
                }
            }

            // 吸引经验球
            List<ExperienceOrb> orbs = level.getEntitiesOfClass(ExperienceOrb.class, area);
            for (ExperienceOrb orb : orbs) {
                if (orb.isAlive()) {
                    double dx = player.getX() - orb.getX();
                    double dy = (player.getY() + 0.5) - orb.getY();
                    double dz = player.getZ() - orb.getZ();
                    double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    if (dist > 0.5) {
                        orb.setDeltaMovement(dx / dist * 0.45, dy / dist * 0.45, dz / dist * 0.45);
                        pulled = true;
                    }
                }
            }

            if (pulled) {
                // 产生极其漂亮的玻璃磁力微粒
                serverLevel.sendParticles(ParticleTypes.PORTAL, 
                        player.getX(), player.getY() + 1.0, player.getZ(), 
                        3, 0.2, 0.2, 0.2, 0.0);

                if (level.getRandom().nextFloat() < 0.15f) {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.3F, 1.8F);
                }
            }
        }
    }
}
