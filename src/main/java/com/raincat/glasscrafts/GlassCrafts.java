package com.raincat.glasscrafts;

import com.raincat.glasscrafts.init.ModArmorMaterials;
import com.raincat.glasscrafts.init.ModBlocks;
import com.raincat.glasscrafts.init.ModCreativeTabs;
import com.raincat.glasscrafts.init.ModItems;
import com.raincat.glasscrafts.init.ModMobEffects;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(GlassCrafts.MODID)
public class GlassCrafts {
    public static final String MODID = "raincat_glasscrafts";
    public static final Logger LOGGER = LogUtils.getLogger();

    public GlassCrafts(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Initializing GlassCrafts (Pure Equipment & Tools Edition)...");

        // Register registries to mod event bus
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        ModMobEffects.MOB_EFFECTS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
    }
}
