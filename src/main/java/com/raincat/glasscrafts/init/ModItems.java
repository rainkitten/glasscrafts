package com.raincat.glasscrafts.init;

import com.raincat.glasscrafts.GlassCrafts;
import com.raincat.glasscrafts.item.GlassPearlItem;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(GlassCrafts.MODID);

    // 基础玻璃方块物品
    public static final DeferredItem<BlockItem> FRAGILE_GLASS = ITEMS.registerSimpleBlockItem("fragile_glass", ModBlocks.FRAGILE_GLASS);
    public static final DeferredItem<BlockItem> TOUGHENED_GLASS = ITEMS.registerSimpleBlockItem("toughened_glass", ModBlocks.TOUGHENED_GLASS);
    public static final DeferredItem<BlockItem> GOLD_INLAID_GLASS = ITEMS.registerSimpleBlockItem("gold_inlaid_glass", ModBlocks.GOLD_INLAID_GLASS);
    public static final DeferredItem<BlockItem> DIAMOND_INLAID_GLASS = ITEMS.registerSimpleBlockItem("diamond_inlaid_glass", ModBlocks.DIAMOND_INLAID_GLASS);
    public static final DeferredItem<BlockItem> VOID_GLASS = ITEMS.registerSimpleBlockItem("void_glass", ModBlocks.VOID_GLASS);

    // 新增的物品和方块物品
    public static final DeferredItem<BlockItem> GLASS_TORCH = ITEMS.registerSimpleBlockItem("glass_torch", ModBlocks.GLASS_TORCH);
    public static final DeferredItem<BlockItem> GLASS_CHANDELIER = ITEMS.registerSimpleBlockItem("glass_chandelier", ModBlocks.GLASS_CHANDELIER);
    
    // 玻璃珍珠
    public static final DeferredItem<GlassPearlItem> GLASS_PEARL = ITEMS.registerItem("glass_pearl", 
            properties -> new GlassPearlItem(properties.stacksTo(16).rarity(Rarity.UNCOMMON)));
            
    // 玻璃弓箭
    public static final DeferredItem<BowItem> GLASS_BOW = ITEMS.registerItem("glass_bow", 
            properties -> new BowItem(properties.durability(64).rarity(Rarity.COMMON)));

    // 1. 易碎玻璃系列 (Fragile Glass) - 耐久乘数 1 (极限超低耐久：头盔11/胸甲16/护腿15/靴子13)
    public static final DeferredItem<SwordItem> FRAGILE_GLASS_LONGSWORD = ITEMS.registerItem("fragile_glass_longsword",
            properties -> new SwordItem(ModToolTiers.FRAGILE_GLASS, properties.attributes(SwordItem.createAttributes(ModToolTiers.FRAGILE_GLASS, 3, -2.4F))));
    public static final DeferredItem<PickaxeItem> FRAGILE_GLASS_PICKAXE = ITEMS.registerItem("fragile_glass_pickaxe",
            properties -> new PickaxeItem(ModToolTiers.FRAGILE_GLASS, properties.attributes(PickaxeItem.createAttributes(ModToolTiers.FRAGILE_GLASS, 1, -2.8F))));
    public static final DeferredItem<AxeItem> FRAGILE_GLASS_AXE = ITEMS.registerItem("fragile_glass_axe",
            properties -> new AxeItem(ModToolTiers.FRAGILE_GLASS, properties.attributes(AxeItem.createAttributes(ModToolTiers.FRAGILE_GLASS, 5, -3.0F))));
    public static final DeferredItem<ShovelItem> FRAGILE_GLASS_SHOVEL = ITEMS.registerItem("fragile_glass_shovel",
            properties -> new ShovelItem(ModToolTiers.FRAGILE_GLASS, properties.attributes(ShovelItem.createAttributes(ModToolTiers.FRAGILE_GLASS, 1.5F, -3.0F))));
    public static final DeferredItem<HoeItem> FRAGILE_GLASS_HOE = ITEMS.registerItem("fragile_glass_hoe",
            properties -> new HoeItem(ModToolTiers.FRAGILE_GLASS, properties.attributes(HoeItem.createAttributes(ModToolTiers.FRAGILE_GLASS, -3, 0.0F))));

    public static final DeferredItem<ArmorItem> FRAGILE_GLASS_HELMET = registerArmor("fragile_glass_helmet", ModArmorMaterials.FRAGILE_GLASS, ArmorItem.Type.HELMET, 1);
    public static final DeferredItem<ArmorItem> FRAGILE_GLASS_CHESTPLATE = registerArmor("fragile_glass_chestplate", ModArmorMaterials.FRAGILE_GLASS, ArmorItem.Type.CHESTPLATE, 1);
    public static final DeferredItem<ArmorItem> FRAGILE_GLASS_LEGGINGS = registerArmor("fragile_glass_leggings", ModArmorMaterials.FRAGILE_GLASS, ArmorItem.Type.LEGGINGS, 1);
    public static final DeferredItem<ArmorItem> FRAGILE_GLASS_BOOTS = registerArmor("fragile_glass_boots", ModArmorMaterials.FRAGILE_GLASS, ArmorItem.Type.BOOTS, 1);

    // 2. 普通玻璃系列 (Glass) - 耐久乘数 2 (极限超低耐久：头盔22/胸甲32/护腿30/靴子26)
    public static final DeferredItem<SwordItem> GLASS_LONGSWORD = ITEMS.registerItem("glass_longsword",
            properties -> new SwordItem(ModToolTiers.GLASS, properties.attributes(SwordItem.createAttributes(ModToolTiers.GLASS, 3, -2.4F))));
    public static final DeferredItem<PickaxeItem> GLASS_PICKAXE = ITEMS.registerItem("glass_pickaxe",
            properties -> new PickaxeItem(ModToolTiers.GLASS, properties.attributes(PickaxeItem.createAttributes(ModToolTiers.GLASS, 1, -2.8F))));
    public static final DeferredItem<AxeItem> GLASS_AXE = ITEMS.registerItem("glass_axe",
            properties -> new AxeItem(ModToolTiers.GLASS, properties.attributes(AxeItem.createAttributes(ModToolTiers.GLASS, 5, -3.0F))));
    public static final DeferredItem<ShovelItem> GLASS_SHOVEL = ITEMS.registerItem("glass_shovel",
            properties -> new ShovelItem(ModToolTiers.GLASS, properties.attributes(ShovelItem.createAttributes(ModToolTiers.GLASS, 1.5F, -3.0F))));
    public static final DeferredItem<HoeItem> GLASS_HOE = ITEMS.registerItem("glass_hoe",
            properties -> new HoeItem(ModToolTiers.GLASS, properties.attributes(HoeItem.createAttributes(ModToolTiers.GLASS, -3, 0.0F))));

    public static final DeferredItem<ArmorItem> GLASS_HELMET = registerArmor("glass_helmet", ModArmorMaterials.GLASS, ArmorItem.Type.HELMET, 2);
    public static final DeferredItem<ArmorItem> GLASS_CHESTPLATE = registerArmor("glass_chestplate", ModArmorMaterials.GLASS, ArmorItem.Type.CHESTPLATE, 2);
    public static final DeferredItem<ArmorItem> GLASS_LEGGINGS = registerArmor("glass_leggings", ModArmorMaterials.GLASS, ArmorItem.Type.LEGGINGS, 2);
    public static final DeferredItem<ArmorItem> GLASS_BOOTS = registerArmor("glass_boots", ModArmorMaterials.GLASS, ArmorItem.Type.BOOTS, 2);

    // 3. 钢化玻璃系列 (Toughened Glass) - 耐久乘数 4 (低耐久：头盔44/胸甲64/护腿60/靴子52)
    public static final DeferredItem<SwordItem> TOUGHENED_GLASS_LONGSWORD = ITEMS.registerItem("toughened_glass_longsword",
            properties -> new SwordItem(ModToolTiers.TOUGHENED_GLASS, properties.attributes(SwordItem.createAttributes(ModToolTiers.TOUGHENED_GLASS, 3, -2.4F))));
    public static final DeferredItem<PickaxeItem> TOUGHENED_GLASS_PICKAXE = ITEMS.registerItem("toughened_glass_pickaxe",
            properties -> new PickaxeItem(ModToolTiers.TOUGHENED_GLASS, properties.attributes(PickaxeItem.createAttributes(ModToolTiers.TOUGHENED_GLASS, 1, -2.8F))));
    public static final DeferredItem<AxeItem> TOUGHENED_GLASS_AXE = ITEMS.registerItem("toughened_glass_axe",
            properties -> new AxeItem(ModToolTiers.TOUGHENED_GLASS, properties.attributes(AxeItem.createAttributes(ModToolTiers.TOUGHENED_GLASS, 5, -3.0F))));
    public static final DeferredItem<ShovelItem> TOUGHENED_GLASS_SHOVEL = ITEMS.registerItem("toughened_glass_shovel",
            properties -> new ShovelItem(ModToolTiers.TOUGHENED_GLASS, properties.attributes(ShovelItem.createAttributes(ModToolTiers.TOUGHENED_GLASS, 1.5F, -3.0F))));
    public static final DeferredItem<HoeItem> TOUGHENED_GLASS_HOE = ITEMS.registerItem("toughened_glass_hoe",
            properties -> new HoeItem(ModToolTiers.TOUGHENED_GLASS, properties.attributes(HoeItem.createAttributes(ModToolTiers.TOUGHENED_GLASS, -3, 0.0F))));

    public static final DeferredItem<ArmorItem> TOUGHENED_GLASS_HELMET = registerArmor("toughened_glass_helmet", ModArmorMaterials.TOUGHENED_GLASS, ArmorItem.Type.HELMET, 4);
    public static final DeferredItem<ArmorItem> TOUGHENED_GLASS_CHESTPLATE = registerArmor("toughened_glass_chestplate", ModArmorMaterials.TOUGHENED_GLASS, ArmorItem.Type.CHESTPLATE, 4);
    public static final DeferredItem<ArmorItem> TOUGHENED_GLASS_LEGGINGS = registerArmor("toughened_glass_leggings", ModArmorMaterials.TOUGHENED_GLASS, ArmorItem.Type.LEGGINGS, 4);
    public static final DeferredItem<ArmorItem> TOUGHENED_GLASS_BOOTS = registerArmor("toughened_glass_boots", ModArmorMaterials.TOUGHENED_GLASS, ArmorItem.Type.BOOTS, 4);

    // 4. 镶金玻璃系列 (Gold Inlaid Glass) - 耐久乘数 3 (低耐久：头盔33/胸甲48/护腿45/靴子39)
    public static final DeferredItem<SwordItem> GOLD_INLAID_GLASS_LONGSWORD = ITEMS.registerItem("gold_inlaid_glass_longsword",
            properties -> new SwordItem(ModToolTiers.GOLD_INLAID_GLASS, properties.attributes(SwordItem.createAttributes(ModToolTiers.GOLD_INLAID_GLASS, 3, -2.4F))));
    public static final DeferredItem<PickaxeItem> GOLD_INLAID_GLASS_PICKAXE = ITEMS.registerItem("gold_inlaid_glass_pickaxe",
            properties -> new PickaxeItem(ModToolTiers.GOLD_INLAID_GLASS, properties.attributes(PickaxeItem.createAttributes(ModToolTiers.GOLD_INLAID_GLASS, 1, -2.8F))));
    public static final DeferredItem<AxeItem> GOLD_INLAID_GLASS_AXE = ITEMS.registerItem("gold_inlaid_glass_axe",
            properties -> new AxeItem(ModToolTiers.GOLD_INLAID_GLASS, properties.attributes(AxeItem.createAttributes(ModToolTiers.GOLD_INLAID_GLASS, 5, -3.0F))));
    public static final DeferredItem<ShovelItem> GOLD_INLAID_GLASS_SHOVEL = ITEMS.registerItem("gold_inlaid_glass_shovel",
            properties -> new ShovelItem(ModToolTiers.GOLD_INLAID_GLASS, properties.attributes(ShovelItem.createAttributes(ModToolTiers.GOLD_INLAID_GLASS, 1.5F, -3.0F))));
    public static final DeferredItem<HoeItem> GOLD_INLAID_GLASS_HOE = ITEMS.registerItem("gold_inlaid_glass_hoe",
            properties -> new HoeItem(ModToolTiers.GOLD_INLAID_GLASS, properties.attributes(HoeItem.createAttributes(ModToolTiers.GOLD_INLAID_GLASS, -3, 0.0F))));

    public static final DeferredItem<ArmorItem> GOLD_INLAID_GLASS_HELMET = registerArmor("gold_inlaid_glass_helmet", ModArmorMaterials.GOLD_INLAID_GLASS, ArmorItem.Type.HELMET, 3);
    public static final DeferredItem<ArmorItem> GOLD_INLAID_GLASS_CHESTPLATE = registerArmor("gold_inlaid_glass_chestplate", ModArmorMaterials.GOLD_INLAID_GLASS, ArmorItem.Type.CHESTPLATE, 3);
    public static final DeferredItem<ArmorItem> GOLD_INLAID_GLASS_LEGGINGS = registerArmor("gold_inlaid_glass_leggings", ModArmorMaterials.GOLD_INLAID_GLASS, ArmorItem.Type.LEGGINGS, 3);
    public static final DeferredItem<ArmorItem> GOLD_INLAID_GLASS_BOOTS = registerArmor("gold_inlaid_glass_boots", ModArmorMaterials.GOLD_INLAID_GLASS, ArmorItem.Type.BOOTS, 3);

    // 5. 镶钻玻璃系列 (Diamond Inlaid Glass) - 耐久乘数 4 (低耐久：头盔44/胸甲64/护腿60/靴子52)
    public static final DeferredItem<SwordItem> DIAMOND_INLAID_GLASS_LONGSWORD = ITEMS.registerItem("diamond_inlaid_glass_longsword",
            properties -> new SwordItem(ModToolTiers.DIAMOND_INLAID_GLASS, properties.attributes(SwordItem.createAttributes(ModToolTiers.DIAMOND_INLAID_GLASS, 3, -2.4F))));
    public static final DeferredItem<PickaxeItem> DIAMOND_INLAID_GLASS_PICKAXE = ITEMS.registerItem("diamond_inlaid_glass_pickaxe",
            properties -> new PickaxeItem(ModToolTiers.DIAMOND_INLAID_GLASS, properties.attributes(PickaxeItem.createAttributes(ModToolTiers.DIAMOND_INLAID_GLASS, 1, -2.8F))));
    public static final DeferredItem<AxeItem> DIAMOND_INLAID_GLASS_AXE = ITEMS.registerItem("diamond_inlaid_glass_axe",
            properties -> new AxeItem(ModToolTiers.DIAMOND_INLAID_GLASS, properties.attributes(AxeItem.createAttributes(ModToolTiers.DIAMOND_INLAID_GLASS, 5, -3.0F))));
    public static final DeferredItem<ShovelItem> DIAMOND_INLAID_GLASS_SHOVEL = ITEMS.registerItem("diamond_inlaid_glass_shovel",
            properties -> new ShovelItem(ModToolTiers.DIAMOND_INLAID_GLASS, properties.attributes(ShovelItem.createAttributes(ModToolTiers.DIAMOND_INLAID_GLASS, 1.5F, -3.0F))));
    public static final DeferredItem<HoeItem> DIAMOND_INLAID_GLASS_HOE = ITEMS.registerItem("diamond_inlaid_glass_hoe",
            properties -> new HoeItem(ModToolTiers.DIAMOND_INLAID_GLASS, properties.attributes(HoeItem.createAttributes(ModToolTiers.DIAMOND_INLAID_GLASS, -3, 0.0F))));

    public static final DeferredItem<ArmorItem> DIAMOND_INLAID_GLASS_HELMET = registerArmor("diamond_inlaid_glass_helmet", ModArmorMaterials.DIAMOND_INLAID_GLASS, ArmorItem.Type.HELMET, 4);
    public static final DeferredItem<ArmorItem> DIAMOND_INLAID_GLASS_CHESTPLATE = registerArmor("diamond_inlaid_glass_chestplate", ModArmorMaterials.DIAMOND_INLAID_GLASS, ArmorItem.Type.CHESTPLATE, 4);
    public static final DeferredItem<ArmorItem> DIAMOND_INLAID_GLASS_LEGGINGS = registerArmor("diamond_inlaid_glass_leggings", ModArmorMaterials.DIAMOND_INLAID_GLASS, ArmorItem.Type.LEGGINGS, 4);
    public static final DeferredItem<ArmorItem> DIAMOND_INLAID_GLASS_BOOTS = registerArmor("diamond_inlaid_glass_boots", ModArmorMaterials.DIAMOND_INLAID_GLASS, ArmorItem.Type.BOOTS, 4);

    // 6. 虚无玻璃系列 (Void Glass) - 耐久乘数 1 (与易碎玻璃相同的极限超低耐久：头盔11/胸甲16/护腿15/靴子13)
    public static final DeferredItem<SwordItem> VOID_GLASS_LONGSWORD = ITEMS.registerItem("void_glass_longsword",
            properties -> new SwordItem(ModToolTiers.VOID_GLASS, properties.attributes(SwordItem.createAttributes(ModToolTiers.VOID_GLASS, 3, -2.4F))));
    public static final DeferredItem<PickaxeItem> VOID_GLASS_PICKAXE = ITEMS.registerItem("void_glass_pickaxe",
            properties -> new PickaxeItem(ModToolTiers.VOID_GLASS, properties.attributes(PickaxeItem.createAttributes(ModToolTiers.VOID_GLASS, 1, -2.8F))));
    public static final DeferredItem<AxeItem> VOID_GLASS_AXE = ITEMS.registerItem("void_glass_axe",
            properties -> new AxeItem(ModToolTiers.VOID_GLASS, properties.attributes(AxeItem.createAttributes(ModToolTiers.VOID_GLASS, 5, -3.0F))));
    public static final DeferredItem<ShovelItem> VOID_GLASS_SHOVEL = ITEMS.registerItem("void_glass_shovel",
            properties -> new ShovelItem(ModToolTiers.VOID_GLASS, properties.attributes(ShovelItem.createAttributes(ModToolTiers.VOID_GLASS, 1.5F, -3.0F))));
    public static final DeferredItem<HoeItem> VOID_GLASS_HOE = ITEMS.registerItem("void_glass_hoe",
            properties -> new HoeItem(ModToolTiers.VOID_GLASS, properties.attributes(HoeItem.createAttributes(ModToolTiers.VOID_GLASS, -3, 0.0F))));

    public static final DeferredItem<ArmorItem> VOID_GLASS_HELMET = registerArmor("void_glass_helmet", ModArmorMaterials.VOID_GLASS, ArmorItem.Type.HELMET, 1);
    public static final DeferredItem<ArmorItem> VOID_GLASS_CHESTPLATE = registerArmor("void_glass_chestplate", ModArmorMaterials.VOID_GLASS, ArmorItem.Type.CHESTPLATE, 1);
    public static final DeferredItem<ArmorItem> VOID_GLASS_LEGGINGS = registerArmor("void_glass_leggings", ModArmorMaterials.VOID_GLASS, ArmorItem.Type.LEGGINGS, 1);
    public static final DeferredItem<ArmorItem> VOID_GLASS_BOOTS = registerArmor("void_glass_boots", ModArmorMaterials.VOID_GLASS, ArmorItem.Type.BOOTS, 1);

    private static DeferredItem<ArmorItem> registerArmor(String name, net.minecraft.core.Holder<ArmorMaterial> material, ArmorItem.Type type, int durabilityMultiplier) {
        return ITEMS.registerItem(name, properties -> new ArmorItem(material, type, properties.durability(type.getDurability(durabilityMultiplier))));
    }
}
