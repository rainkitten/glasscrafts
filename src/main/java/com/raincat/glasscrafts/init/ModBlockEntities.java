package com.raincat.glasscrafts.init;

import com.raincat.glasscrafts.GlassCrafts;
import com.raincat.glasscrafts.blockentity.GlassChestBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, GlassCrafts.MODID);

    public static final Supplier<BlockEntityType<GlassChestBlockEntity>> GLASS_CHEST =
            BLOCK_ENTITIES.register("glass_chest", () ->
                    BlockEntityType.Builder.of(GlassChestBlockEntity::new, ModBlocks.GLASS_CHEST.get()).build(null));
}
