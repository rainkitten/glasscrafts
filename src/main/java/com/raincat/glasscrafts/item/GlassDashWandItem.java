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
import net.minecraft.world.phys.Vec3;

public class GlassDashWandItem extends Item {
    public GlassDashWandItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        player.getCooldowns().addCooldown(this, 10); // 0.5秒极短CD

        // 播放玻璃闪鸣的音效
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.8F);

        // 得到玩家视线朝向向量并归一化
        Vec3 lookVec = player.getLookAngle();
        // 瞬间向玩家视线方向施加一个高强度的水平及垂直冲力 (1.8倍冲力)
        player.setDeltaMovement(new Vec3(lookVec.x * 1.8, Math.max(0.4, lookVec.y * 1.2), lookVec.z * 1.8));
        
        // 标记玩家已重置掉落高度，避免高机动冲刺后跌落摔死
        player.resetFallDistance();

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            // 在玩家身后拉出一条漂亮的透明音波/烟雾拖尾粒子
            for (int i = 0; i < 15; i++) {
                serverLevel.sendParticles(ParticleTypes.CLOUD, 
                        player.getX() + (level.getRandom().nextDouble() - 0.5) * 0.5, 
                        player.getY() + 0.5 + (level.getRandom().nextDouble() - 0.5) * 0.5, 
                        player.getZ() + (level.getRandom().nextDouble() - 0.5) * 0.5, 
                        1, 0.0, 0.0, 0.0, 0.0);
            }

            // 消耗 1 点耐久
            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
