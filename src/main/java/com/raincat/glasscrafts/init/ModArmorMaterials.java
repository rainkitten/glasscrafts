package com.raincat.glasscrafts.init;

import com.raincat.glasscrafts.GlassCrafts;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, GlassCrafts.MODID);

    // 易碎玻璃护甲：减半防御系数 (总防御7, 韧性0.5)
    public static final Holder<ArmorMaterial> FRAGILE_GLASS = ARMOR_MATERIALS.register("fragile_glass",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1);
                        map.put(ArmorItem.Type.LEGGINGS, 2);
                        map.put(ArmorItem.Type.CHESTPLATE, 3);
                        map.put(ArmorItem.Type.HELMET, 1);
                    }),
                    15,
                    SoundEvents.ARMOR_EQUIP_GENERIC,
                    () -> Ingredient.of(ModBlocks.FRAGILE_GLASS.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(GlassCrafts.MODID, "fragile_glass"))),
                    0.5F,
                    0.0F
            ));

    // 玻璃护甲：减半防御系数 (总防御9, 韧性1.0)
    public static final Holder<ArmorMaterial> GLASS = ARMOR_MATERIALS.register("glass",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1);
                        map.put(ArmorItem.Type.LEGGINGS, 3);
                        map.put(ArmorItem.Type.CHESTPLATE, 4);
                        map.put(ArmorItem.Type.HELMET, 1);
                    }),
                    14,
                    SoundEvents.ARMOR_EQUIP_DIAMOND,
                    () -> Ingredient.of(Blocks.GLASS),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(GlassCrafts.MODID, "glass"))),
                    1.0F,
                    0.0F
            ));

    // 钢化玻璃护甲：减半防御系数 (总防御11, 韧性1.5)
    public static final Holder<ArmorMaterial> TOUGHENED_GLASS = ARMOR_MATERIALS.register("toughened_glass",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 2);
                        map.put(ArmorItem.Type.LEGGINGS, 3);
                        map.put(ArmorItem.Type.CHESTPLATE, 4);
                        map.put(ArmorItem.Type.HELMET, 2);
                    }),
                    18,
                    SoundEvents.ARMOR_EQUIP_NETHERITE,
                    () -> Ingredient.of(ModBlocks.TOUGHENED_GLASS.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(GlassCrafts.MODID, "toughened_glass"))),
                    1.5F,
                    0.0F
            ));

    // 镶金玻璃护甲：减半防御系数 (总防御13, 韧性1.5)
    public static final Holder<ArmorMaterial> GOLD_INLAID_GLASS = ARMOR_MATERIALS.register("gold_inlaid_glass",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 2);
                        map.put(ArmorItem.Type.LEGGINGS, 4);
                        map.put(ArmorItem.Type.CHESTPLATE, 5);
                        map.put(ArmorItem.Type.HELMET, 2);
                    }),
                    22,
                    SoundEvents.ARMOR_EQUIP_GOLD,
                    () -> Ingredient.of(ModBlocks.GOLD_INLAID_GLASS.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(GlassCrafts.MODID, "gold_inlaid_glass"))),
                    1.5F,
                    0.05F
            ));

    // 镶钻玻璃护甲：减半防御系数 (总防御13, 韧性2.0)
    public static final Holder<ArmorMaterial> DIAMOND_INLAID_GLASS = ARMOR_MATERIALS.register("diamond_inlaid_glass",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 2);
                        map.put(ArmorItem.Type.LEGGINGS, 4);
                        map.put(ArmorItem.Type.CHESTPLATE, 5);
                        map.put(ArmorItem.Type.HELMET, 2);
                    }),
                    25,
                    SoundEvents.ARMOR_EQUIP_DIAMOND,
                    () -> Ingredient.of(ModBlocks.DIAMOND_INLAID_GLASS.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(GlassCrafts.MODID, "diamond_inlaid_glass"))),
                    2.0F,
                    0.05F
            ));

    // 虚无玻璃护甲：减半防御系数 (总防御14, 韧性2.5, 击退抗性0.1)
    public static final Holder<ArmorMaterial> VOID_GLASS = ARMOR_MATERIALS.register("void_glass",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 2);
                        map.put(ArmorItem.Type.LEGGINGS, 4);
                        map.put(ArmorItem.Type.CHESTPLATE, 5);
                        map.put(ArmorItem.Type.HELMET, 3);
                    }),
                    30,
                    SoundEvents.ARMOR_EQUIP_NETHERITE,
                    () -> Ingredient.of(ModBlocks.VOID_GLASS.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(GlassCrafts.MODID, "void_glass"))),
                    2.5F,
                    0.1F
            ));
}
