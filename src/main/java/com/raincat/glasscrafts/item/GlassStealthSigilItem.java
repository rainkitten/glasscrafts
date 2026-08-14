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

public class GlassStealthSigilItem extends Item {
    public GlassStealthSigilItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        player.getCooldowns().addCooldown(this, 100); // 5秒短CD

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.6F, 1.8F);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            // 给玩家附加 12 秒的隐形 II 与 速度 I，极度适合悄无声息地溜走
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 240, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 240, 0));

            // 在玩家脚底下产生一圈玻璃折射气泡粒子效果
            for (int i = 0; i < 10; i++) {
                serverLevel.sendParticles(ParticleTypes.SPLASH, 
                        player.getX() + (level.getRandom().nextDouble() - 0.5) * 0.8, 
                        player.getY() + 0.1, 
                        player.getZ() + (level.getRandom().nextDouble() - 0.5) * 0.8, 
                        3, 0.1, 0.1, 0.1, 0.0);
            }

            // 消耗 1 点耐久
            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
