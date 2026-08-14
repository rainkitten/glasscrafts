package com.raincat.glasscrafts.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class GlassLightningRodItem extends Item {
    public GlassLightningRodItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        // 仅在雷雨天可以使用
        if (!level.isThundering()) {
            if (level.isClientSide()) {
                player.displayClientMessage(Component.translatable("message.raincat_glasscrafts.lightning_rod_no_thunder"), true);
            }
            return InteractionResultHolder.fail(itemstack);
        }

        player.getCooldowns().addCooldown(this, 200); // 10秒冷却

        // 准星射线检测判定准星指向的方块位置
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos targetPos = hitResult.getBlockPos();

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.8F, 1.6F);

            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                // 在目标位置生成雷电
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
                if (lightningBolt != null) {
                    lightningBolt.moveTo(targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5);
                    level.addFreshEntity(lightningBolt);
                }
                
                itemstack.hurtAndBreak(2, player, player.getEquipmentSlotForItem(itemstack));
            }
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }

        return InteractionResultHolder.pass(itemstack);
    }
}
