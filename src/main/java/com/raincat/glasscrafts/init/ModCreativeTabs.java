package com.raincat.glasscrafts.init;

import com.raincat.glasscrafts.GlassCrafts;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GlassCrafts.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GLASSCRAFTS_TAB =
            CREATIVE_MODE_TABS.register("glasscrafts_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.raincat_glasscrafts"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.VOID_GLASS_LONGSWORD.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        // 新增的独立玻璃小用品
                        output.accept(ModItems.GLASS_TORCH.get());
                        output.accept(ModItems.GLASS_CHANDELIER.get());
                        output.accept(ModItems.GLASS_PEARL.get());
                        output.accept(ModItems.GLASS_BOW.get());
                        output.accept(ModItems.GLASS_LENS.get());
                        output.accept(ModItems.GLASS_CHARM.get());
                        output.accept(ModItems.GLASS_SONIC_BELL.get());
                        output.accept(ModItems.GLASS_REGEN_CROWN.get());
                        output.accept(ModItems.GLASS_DASH_WAND.get());
                        output.accept(ModItems.GLASS_ERASE_WAND.get());
                        output.accept(ModItems.GLASS_STEALTH_SIGIL.get());
                        output.accept(ModItems.GLASS_GROW_FERTILIZER.get());
                        
                        output.accept(ModItems.GLASS_ECHO_FLUTE.get());
                        output.accept(ModItems.GLASS_CUSHION_BOOTS.get());
                        output.accept(ModItems.GLASS_LIGHTNING_ROD.get());
                        output.accept(ModItems.GLASS_MAGNET.get());
                        output.accept(ModItems.GLASS_WARP_COMPASS.get());
                        output.accept(ModItems.GLASS_REFLECTOR.get());
                        output.accept(ModItems.GLASS_PRISM.get());
                        output.accept(ModItems.GLASS_OCEAN_SHELL.get());
                        output.accept(ModItems.GLASS_ALCHEMIC_FLASK.get());
                        output.accept(ModItems.GLASS_GOLEM_CORE.get());

                        // 1. 易碎玻璃
                        output.accept(ModItems.FRAGILE_GLASS.get());
                        output.accept(ModItems.FRAGILE_GLASS_LONGSWORD.get());
                        output.accept(ModItems.FRAGILE_GLASS_PICKAXE.get());
                        output.accept(ModItems.FRAGILE_GLASS_AXE.get());
                        output.accept(ModItems.FRAGILE_GLASS_SHOVEL.get());
                        output.accept(ModItems.FRAGILE_GLASS_HOE.get());
                        output.accept(ModItems.FRAGILE_GLASS_HELMET.get());
                        output.accept(ModItems.FRAGILE_GLASS_CHESTPLATE.get());
                        output.accept(ModItems.FRAGILE_GLASS_LEGGINGS.get());
                        output.accept(ModItems.FRAGILE_GLASS_BOOTS.get());

                        // 2. 普通玻璃
                        output.accept(ModItems.GLASS_LONGSWORD.get());
                        output.accept(ModItems.GLASS_PICKAXE.get());
                        output.accept(ModItems.GLASS_AXE.get());
                        output.accept(ModItems.GLASS_SHOVEL.get());
                        output.accept(ModItems.GLASS_HOE.get());
                        output.accept(ModItems.GLASS_HELMET.get());
                        output.accept(ModItems.GLASS_CHESTPLATE.get());
                        output.accept(ModItems.GLASS_LEGGINGS.get());
                        output.accept(ModItems.GLASS_BOOTS.get());

                        // 3. 钢化玻璃
                        output.accept(ModItems.TOUGHENED_GLASS.get());
                        output.accept(ModItems.TOUGHENED_GLASS_LONGSWORD.get());
                        output.accept(ModItems.TOUGHENED_GLASS_PICKAXE.get());
                        output.accept(ModItems.TOUGHENED_GLASS_AXE.get());
                        output.accept(ModItems.TOUGHENED_GLASS_SHOVEL.get());
                        output.accept(ModItems.TOUGHENED_GLASS_HOE.get());
                        output.accept(ModItems.TOUGHENED_GLASS_HELMET.get());
                        output.accept(ModItems.TOUGHENED_GLASS_CHESTPLATE.get());
                        output.accept(ModItems.TOUGHENED_GLASS_LEGGINGS.get());
                        output.accept(ModItems.TOUGHENED_GLASS_BOOTS.get());

                        // 4. 镶金玻璃
                        output.accept(ModItems.GOLD_INLAID_GLASS.get());
                        output.accept(ModItems.GOLD_INLAID_GLASS_LONGSWORD.get());
                        output.accept(ModItems.GOLD_INLAID_GLASS_PICKAXE.get());
                        output.accept(ModItems.GOLD_INLAID_GLASS_AXE.get());
                        output.accept(ModItems.GOLD_INLAID_GLASS_SHOVEL.get());
                        output.accept(ModItems.GOLD_INLAID_GLASS_HOE.get());
                        output.accept(ModItems.GOLD_INLAID_GLASS_HELMET.get());
                        output.accept(ModItems.GOLD_INLAID_GLASS_CHESTPLATE.get());
                        output.accept(ModItems.GOLD_INLAID_GLASS_LEGGINGS.get());
                        output.accept(ModItems.GOLD_INLAID_GLASS_BOOTS.get());

                        // 5. 镶钻玻璃
                        output.accept(ModItems.DIAMOND_INLAID_GLASS.get());
                        output.accept(ModItems.DIAMOND_INLAID_GLASS_LONGSWORD.get());
                        output.accept(ModItems.DIAMOND_INLAID_GLASS_PICKAXE.get());
                        output.accept(ModItems.DIAMOND_INLAID_GLASS_AXE.get());
                        output.accept(ModItems.DIAMOND_INLAID_GLASS_SHOVEL.get());
                        output.accept(ModItems.DIAMOND_INLAID_GLASS_HOE.get());
                        output.accept(ModItems.DIAMOND_INLAID_GLASS_HELMET.get());
                        output.accept(ModItems.DIAMOND_INLAID_GLASS_CHESTPLATE.get());
                        output.accept(ModItems.DIAMOND_INLAID_GLASS_LEGGINGS.get());
                        output.accept(ModItems.DIAMOND_INLAID_GLASS_BOOTS.get());

                        // 6. 虚无玻璃
                        output.accept(ModItems.VOID_GLASS.get());
                        output.accept(ModItems.VOID_GLASS_LONGSWORD.get());
                        output.accept(ModItems.VOID_GLASS_PICKAXE.get());
                        output.accept(ModItems.VOID_GLASS_AXE.get());
                        output.accept(ModItems.VOID_GLASS_SHOVEL.get());
                        output.accept(ModItems.VOID_GLASS_HOE.get());
                        output.accept(ModItems.VOID_GLASS_HELMET.get());
                        output.accept(ModItems.VOID_GLASS_CHESTPLATE.get());
                        output.accept(ModItems.VOID_GLASS_LEGGINGS.get());
                        output.accept(ModItems.VOID_GLASS_BOOTS.get());
                    }).build());
}
