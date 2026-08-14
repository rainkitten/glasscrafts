package com.raincat.glasscrafts.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlassCharmItem extends Item {
    public GlassCharmItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        // 赋予玩家 15 秒的冷却时间 (300 ticks)
        player.getCooldowns().addCooldown(this, 40); // 2秒短冷却

        if (!level.isClientSide()) {
            // 瞬间清除玩家身上所有的负面Buff，并且提供 5 秒的急迫 II 状态
            player.clearFire();
            player.removeEffect(MobEffects.POISON);
            player.removeEffect(MobEffects.WITHER);
            player.removeEffect(MobEffects.BLINDNESS);
            player.removeEffect(MobEffects.CONFUSION);
            player.removeEffect(MobEffects.WEAKNESS);
            player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            player.removeEffect(MobEffects.DIG_SLOWDOWN);
            
            // 附赠 10 秒的加速 (Speed I) 与急迫 (Haste I) 常用效果
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0));

            // 消耗 1 点耐久
            itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
