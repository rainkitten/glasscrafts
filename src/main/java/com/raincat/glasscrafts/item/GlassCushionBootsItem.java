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

public class GlassCushionBootsItem extends Item {
    public GlassCushionBootsItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        player.getCooldowns().addCooldown(this, 300); // 15秒冷却

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.6F, 1.8F);
        // 使用原版存在的 BIG_FALL 声音
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_BIG_FALL, SoundSource.PLAYERS, 1.0F, 1.5F);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            // 附加 15 秒缓降效果
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 300, 0));
            // 并通过抗性提升确保这段时间内不受任何高空坠落的意外伤害
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 4)); // 抗性 V 相当于无敌

            // 产生云雾/羽毛粒子
            for (int i = 0; i < 15; i++) {
                serverLevel.sendParticles(ParticleTypes.CLOUD, 
                        player.getX() + (level.getRandom().nextDouble() - 0.5) * 0.6, 
                        player.getY() + 0.1, 
                        player.getZ() + (level.getRandom().nextDouble() - 0.5) * 0.6, 
                        2, 0.1, 0.1, 0.1, 0.01);
            }

            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
