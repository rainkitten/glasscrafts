package com.raincat.glasscrafts.entity;

import com.raincat.glasscrafts.init.ModItems;
import com.raincat.glasscrafts.init.ModMobEffects;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class GlassPearlEntity extends Snowball {

    public GlassPearlEntity(EntityType<? extends GlassPearlEntity> entityType, Level level) {
        super(entityType, level);
    }

    public GlassPearlEntity(Level level, LivingEntity shooter) {
        super(level, shooter);
    }

    public GlassPearlEntity(Level level, double x, double y, double z) {
        super(level, x, y, z);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GLASS_PEARL.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        if (target instanceof LivingEntity livingTarget) {
            // 造成 5 点直接玻璃伤害
            livingTarget.hurt(this.damageSources().thrown(this, this.getOwner()), 5.0F);
            // 附加 15 秒 (300 ticks) "扎死我了" 效果
            livingTarget.addEffect(new MobEffectInstance(ModMobEffects.GLASS_SHARD, 300, 0));
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            // 产生玻璃碎片粒子
            for (int i = 0; i < 32; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(this.getDefaultItem())), 
                        this.getX(), this.getY(), this.getZ(), 
                        (this.random.nextDouble() - 0.5) * 0.3, 
                        this.random.nextDouble() * 0.3, 
                        (this.random.nextDouble() - 0.5) * 0.3);
            }
            this.discard();
        }
    }
}
