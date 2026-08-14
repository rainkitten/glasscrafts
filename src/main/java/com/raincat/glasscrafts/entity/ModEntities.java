package com.raincat.glasscrafts.entity;

import com.raincat.glasscrafts.GlassCrafts;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, GlassCrafts.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<GlassPearlEntity>> GLASS_PEARL =
            ENTITY_TYPES.register("glass_pearl", () -> EntityType.Builder.<GlassPearlEntity>of(GlassPearlEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("glass_pearl"));
}
