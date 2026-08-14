package com.raincat.glasscrafts.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class GlassEchoFluteItem extends Item {
    public GlassEchoFluteItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        player.getCooldowns().addCooldown(this, 120); // 6秒冷却

        // 播放优雅高亢的玻璃共鸣声音
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.8F, 2.0F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.2F, 1.5F);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            BlockPos pos = player.blockPosition();
            double range = 12.0;

            // 产生环形共鸣粒子效果
            for (int i = 0; i < 20; i++) {
                double angle = (i * 2 * Math.PI) / 20;
                double dx = Math.cos(angle) * 2.0;
                double dz = Math.sin(angle) * 2.0;
                serverLevel.sendParticles(ParticleTypes.NOTE, 
                        player.getX() + dx, player.getY() + 0.8, player.getZ() + dz, 
                        1, 0.1, 0.1, 0.1, 0.1);
            }

            // 对周围12格内的所有怪物附加缓慢 IV (持续 5 秒)
            AABB area = new AABB(pos).inflate(range);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                    entity -> entity != player && !entity.getType().getCategory().isFriendly());

            for (LivingEntity entity : entities) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, -4)); // 缓慢 IV
                // 对怪物产生眩晕粒子
                serverLevel.sendParticles(ParticleTypes.CRIT, 
                        entity.getX(), entity.getY() + entity.getBbHeight() + 0.2, entity.getZ(), 
                        5, 0.2, 0.1, 0.2, 0.02);
            }

            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<net.minecraft.network.chat.Component> tooltipComponents, net.minecraft.world.item.TooltipFlag tooltipFlag) {
        tooltipComponents.add(net.minecraft.network.chat.Component.translatable(this.getDescriptionId() + ".desc").withStyle(net.minecraft.ChatFormatting.GRAY));
    }
}
