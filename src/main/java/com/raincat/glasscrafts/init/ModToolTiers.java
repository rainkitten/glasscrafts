package com.raincat.glasscrafts.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    // 易碎玻璃：9点高伤，提高耐久 (60点)，极高效率 (20.0F)
    public static final Tier FRAGILE_GLASS = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            60,
            20.0F,
            9.0F,
            15,
            () -> Ingredient.of(ModBlocks.FRAGILE_GLASS.get())
    );

    // 玻璃：9点伤害，提高耐久 (120点)，极高效率 (24.0F)
    public static final Tier GLASS = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            120,
            24.0F,
            9.0F,
            14,
            () -> Ingredient.of(Blocks.GLASS)
    );

    // 钢化玻璃：12点伤害，提高耐久 (300点)，超级效率 (30.0F)
    public static final Tier TOUGHENED_GLASS = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            300,
            30.0F,
            12.0F,
            18,
            () -> Ingredient.of(ModBlocks.TOUGHENED_GLASS.get())
    );

    // 镶金玻璃：14点伤害，提高耐久 (500点)，超级效率 (36.0F)
    public static final Tier GOLD_INLAID_GLASS = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            500,
            36.0F,
            14.0F,
            22,
            () -> Ingredient.of(ModBlocks.GOLD_INLAID_GLASS.get())
    );

    // 镶钻玻璃：17点伤害，大幅提高耐久 (1000点)，神速效率 (45.0F)
    public static final Tier DIAMOND_INLAID_GLASS = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            1000,
            45.0F,
            17.0F,
            25,
            () -> Ingredient.of(ModBlocks.DIAMOND_INLAID_GLASS.get())
    );

    // 虚无玻璃：32点极限伤害，大幅提高耐久 (600点)，瞬破级神速效率 (80.0F)
    public static final Tier VOID_GLASS = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            600,
            80.0F,
            32.0F,
            30,
            () -> Ingredient.of(ModBlocks.VOID_GLASS.get())
    );
}
