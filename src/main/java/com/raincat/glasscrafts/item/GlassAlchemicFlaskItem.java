package com.raincat.glasscrafts.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlassAlchemicFlaskItem extends Item {
    public GlassAlchemicFlaskItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        // 检测是否有任何状态效果需要清除
        if (player.getActiveEffects().isEmpty()) {
            return InteractionResultHolder.pass(itemstack);
        }

        player.getCooldowns().addCooldown(this, 20); // 1秒短冷却

        // 播放咕嘟饮水和玻璃破碎音
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.4F, 2.0F);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            // 清除全部负面及正面状态 (同牛奶效果)
            player.removeAllEffects();

            // 产生炼金药水微粒
            for (int i = 0; i < 15; i++) {
                serverLevel.sendParticles(ParticleTypes.WITCH, 
                        player.getX() + (level.getRandom().nextDouble() - 0.5) * 0.6, 
                        player.getY() + 1.0, 
                        player.getZ() + (level.getRandom().nextDouble() - 0.5) * 0.6, 
                        3, 0.1, 0.1, 0.1, 0.02);
            }

            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
