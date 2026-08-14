package com.raincat.glasscrafts.init;

import com.raincat.glasscrafts.GlassCrafts;
import com.raincat.glasscrafts.effect.GlassShardEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, GlassCrafts.MODID);

    // "扎死我了" Status Effect: 每 3 秒扣除 2 点血量
    public static final Holder<MobEffect> GLASS_SHARD = MOB_EFFECTS.register("glass_shard",
            () -> new GlassShardEffect(MobEffectCategory.HARMFUL, 0xE74C3C));
}
