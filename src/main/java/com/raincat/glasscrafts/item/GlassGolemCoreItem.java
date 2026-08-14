package com.raincat.glasscrafts.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GlassGolemCoreItem extends Item {
    public GlassGolemCoreItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        // 作用于友善生物以及傀儡（如铁傀儡、雪傀儡）
        boolean isGolemOrFriendly = target.getType().getCategory().isFriendly() || target instanceof net.minecraft.world.entity.animal.IronGolem || target instanceof net.minecraft.world.entity.animal.SnowGolem;
        
        if (isGolemOrFriendly) {
            player.getCooldowns().addCooldown(this, 20); // 1秒短CD

            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.5F);
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.5F, 1.8F);

            if (!target.level().isClientSide() && target.level() instanceof ServerLevel serverLevel) {
                // 赋予 60 秒的无敌与力量提升
                target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 4)); // 抗性 V
                target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1)); // 力量 II

                // 爆发出核心充能粒子
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, 
                        target.getX(), target.getY() + 0.5, target.getZ(), 
                        15, 0.3, 0.3, 0.3, 0.1);

                stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.sidedSuccess(target.level().isClientSide());
        }

        return InteractionResult.PASS;
    }
}
