package com.raincat.glasscrafts.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlassRegenCrownItem extends Item {
    public GlassRegenCrownItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        player.getCooldowns().addCooldown(this, 400); // 20秒冷却时间

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GLASS_CHIME, SoundSource.PLAYERS, 1.0F, 1.2F);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            // 给玩家附加 6 秒的生命恢复 II 状态，用于高频战斗回血
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 1));
            
            // 额外赠送 5 秒的抗性提升 I
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 0));

            // 在头部上方产生一圈漂亮的绿色爱心粒子和圣洁发光粒子
            for (int i = 0; i < 12; i++) {
                double angle = (i * 2 * Math.PI) / 12;
                double dx = Math.cos(angle) * 0.5;
                double dz = Math.sin(angle) * 0.5;
                serverLevel.sendParticles(ParticleTypes.HEART, 
                        player.getX() + dx, player.getY() + 2.1, player.getZ() + dz, 
                        1, 0, 0, 0, 0);
            }

            // 消耗 1 点耐久
            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
