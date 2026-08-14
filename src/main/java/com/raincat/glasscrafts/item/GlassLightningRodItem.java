package com.raincat.glasscrafts.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

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

        player.getCooldowns().addCooldown(this, 40); // 2秒短CD

        // 射线检测：判定准星指向的实体或方块
        Vec3 eyePos = player.getEyePosition();
        Vec3 viewVector = player.getViewVector(1.0F);
        Vec3 reachVector = eyePos.add(viewVector.scale(32.0)); // 32格射程

        // 先检测实体
        AABB searchBox = player.getBoundingBox().expandTowards(viewVector.scale(32.0)).inflate(1.0);
        List<Entity> entities = level.getEntities(player, searchBox, entity -> entity.isAlive() && entity.isPickable());

        Vec3 targetPos = null;

        for (Entity entity : entities) {
            AABB entityBox = entity.getBoundingBox().inflate(0.3);
            Optional<Vec3> clip = entityBox.clip(eyePos, reachVector);
            if (clip.isPresent()) {
                targetPos = clip.get();
                break;
            }
        }

        // 如果没有瞄准实体，则判定指到的方块
        if (targetPos == null) {
            HitResult hitResult = level.clip(new ClipContext(eyePos, reachVector, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                targetPos = hitResult.getLocation();
            }
        }

        if (targetPos != null) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.8F, 1.6F);

            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
                if (lightningBolt != null) {
                    lightningBolt.moveTo(targetPos.x, targetPos.y, targetPos.z);
                    level.addFreshEntity(lightningBolt);
                }
                
                itemstack.hurtAndBreak(2, player, player.getEquipmentSlotForItem(itemstack));
            }
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }

        return InteractionResultHolder.pass(itemstack);
    }
}
