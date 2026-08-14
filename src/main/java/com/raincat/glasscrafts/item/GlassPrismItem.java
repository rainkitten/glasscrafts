package com.raincat.glasscrafts.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GlassPrismItem extends Item {
    public GlassPrismItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        ItemStack itemstack = context.getItemInHand();

        // 仅能在白天使用（吸收聚集阳光）
        if (level.isNight() || level.isRaining()) {
            if (level.isClientSide() && player != null) {
                player.displayClientMessage(Component.translatable("message.raincat_glasscrafts.prism_no_sunlight"), true);
            }
            return InteractionResult.FAIL;
        }

        BlockPos placePos = clickedPos.relative(context.getClickedFace());
        BlockState currentState = level.getBlockState(placePos);

        if (BaseFireBlock.canBePlacedAt(level, placePos, context.getHorizontalDirection())) {
            level.playSound(player, placePos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            level.playSound(player, placePos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 0.4F, 2.0F);

            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                // 点火
                BlockState fireState = BaseFireBlock.getState(level, placePos);
                level.setBlock(placePos, fireState, 11);
                
                // 产生强烈聚焦的阳光微粒
                serverLevel.sendParticles(ParticleTypes.FLAME, 
                        placePos.getX() + 0.5, placePos.getY() + 0.2, placePos.getZ() + 0.5, 
                        8, 0.1, 0.1, 0.1, 0.05);

                itemstack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemstack));
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.FAIL;
    }
}
