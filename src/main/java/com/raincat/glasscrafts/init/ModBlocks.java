package com.raincat.glasscrafts.init;

import com.raincat.glasscrafts.GlassCrafts;
import com.raincat.glasscrafts.block.GlassChandelierBlock;
import com.raincat.glasscrafts.block.GlassTorchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(GlassCrafts.MODID);

    // 5 大等级基础玻璃方块 (硬度降低)
    public static final DeferredBlock<Block> FRAGILE_GLASS = registerGlass("fragile_glass", 0.1F, 0.1F);
    public static final DeferredBlock<Block> TOUGHENED_GLASS = registerGlass("toughened_glass", 1.0F, 3.0F);
    public static final DeferredBlock<Block> GOLD_INLAID_GLASS = registerGlass("gold_inlaid_glass", 1.5F, 4.5F);
    public static final DeferredBlock<Block> DIAMOND_INLAID_GLASS = registerGlass("diamond_inlaid_glass", 2.0F, 6.0F);
    public static final DeferredBlock<Block> VOID_GLASS = registerGlass("void_glass", 3.5F, 15.0F, true);

    // 玻璃火把与玻璃吊灯
    public static final DeferredBlock<Block> GLASS_TORCH = BLOCKS.registerBlock("glass_torch", 
            properties -> new GlassTorchBlock(properties), 
            BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).lightLevel(state -> 14).noOcclusion());

    public static final DeferredBlock<Block> GLASS_CHANDELIER = BLOCKS.registerBlock("glass_chandelier", 
            properties -> new GlassChandelierBlock(properties), 
            BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(state -> 15).noOcclusion());

    private static DeferredBlock<Block> registerGlass(String name, float strength, float blast) {
        return BLOCKS.registerBlock(name, TransparentBlock::new,
                BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(strength, blast));
    }

    private static DeferredBlock<Block> registerGlass(String name, float strength, float blast, boolean noOcclusion) {
        return BLOCKS.registerBlock(name, TransparentBlock::new,
                BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(strength, blast).noOcclusion());
    }
}
