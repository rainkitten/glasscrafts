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

public class GlassOceanShellItem extends Item {
    public GlassOceanShellItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        player.getCooldowns().addCooldown(this, 600); // 30秒冷却

        // 播放深海回音和玻璃破碎回声
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.CONDUIT_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.2F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.4F, 1.8F);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            // 提供 30 秒水下呼吸与海豚恩赐
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 0));
            player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 600, 0));

            // 水泡与水滴微粒
            for (int i = 0; i < 20; i++) {
                serverLevel.sendParticles(ParticleTypes.BUBBLE, 
                        player.getX() + (level.getRandom().nextDouble() - 0.5) * 0.8, 
                        player.getY() + 1.0, 
                        player.getZ() + (level.getRandom().nextDouble() - 0.5) * 0.8, 
                        2, 0.1, 0.1, 0.1, 0.01);
            }

            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
