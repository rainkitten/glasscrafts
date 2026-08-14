package com.raincat.glasscrafts.event;

import com.raincat.glasscrafts.GlassCrafts;
import com.raincat.glasscrafts.init.ModItems;
import com.raincat.glasscrafts.init.ModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = GlassCrafts.MODID)
public class GlassBowEventHandler {

    @SubscribeEvent
    public static void onArrowHit(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        if (target.level().isClientSide()) return;

        // 如果伤害来源是投射物 (箭矢等)
        if (event.getSource().getDirectEntity() instanceof Projectile projectile) {
            // 如果投射物的发射者是玩家，并且使用的是玻璃弓
            if (projectile.getOwner() instanceof LivingEntity shooter) {
                // 判断玩家手上是否拿着玻璃弓
                if (shooter.getMainHandItem().is(ModItems.GLASS_BOW.get()) || 
                    shooter.getOffhandItem().is(ModItems.GLASS_BOW.get())) {
                    
                    // 命中时，有概率将玻璃碎屑附着在目标上，造成 8 秒【扎死我了】效果
                    target.addEffect(new MobEffectInstance(ModMobEffects.GLASS_SHARD, 160, 0));
                }
            }
        }
    }
}
