package com.raincat.glasscrafts.event;

import com.raincat.glasscrafts.GlassCrafts;
import com.raincat.glasscrafts.init.ModArmorMaterials;
import com.raincat.glasscrafts.init.ModItems;
import com.raincat.glasscrafts.init.ModMobEffects;
import com.raincat.glasscrafts.init.ModToolTiers;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = GlassCrafts.MODID)
public class GlassToolEventHandler {

    private static final RandomSource RANDOM = RandomSource.create();
    private static final float SELF_HARM_FRAGILE = 0.15F;
    private static final float SELF_HARM_GLASS = 0.08F;
    private static final float SELF_HARM_TOUGHENED = 0.05F;
    private static final float SELF_HARM_GOLD = 0.03F;
    private static final float SELF_HARM_DIAMOND = 0.01F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.isEmpty() || isVoidGlassItem(mainHand)) {
            return;
        }

        LivingEntity target = event.getEntity();
        if (isFragileGlassItem(mainHand)) {
            target.addEffect(new MobEffectInstance(ModMobEffects.GLASS_SHARD, 60, 0));
        }

        trySelfHarm(player, mainHand);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player == null || player.level().isClientSide()) {
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.isEmpty() || isVoidGlassItem(mainHand)) {
            return;
        }

        trySelfHarm(player, mainHand);
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getState().is(Blocks.BEDROCK) && event.getEntity().getMainHandItem().is(ModItems.VOID_GLASS_PICKAXE.get())) {
            event.setNewSpeed(100.0F);
        }
    }

    @SubscribeEvent
    public static void onLeftClickBedrock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (player == null || player.level().isClientSide()) {
            return;
        }

        BlockState state = event.getLevel().getBlockState(event.getPos());
        if (!state.is(Blocks.BEDROCK)) {
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        if (!mainHand.is(ModItems.VOID_GLASS_PICKAXE.get())) {
            return;
        }

        event.setCanceled(true);
        event.getLevel().removeBlock(event.getPos(), false);
        player.spawnAtLocation(new ItemStack(Blocks.BEDROCK.asItem()));
        mainHand.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1.0F, 0.8F);
    }

    private static boolean isFragileGlassItem(ItemStack stack) {
        return stack.getItem() instanceof TieredItem tieredItem && tieredItem.getTier() == ModToolTiers.FRAGILE_GLASS;
    }

    private static boolean isVoidGlassItem(ItemStack stack) {
        if (stack.getItem() instanceof TieredItem tieredItem) {
            return tieredItem.getTier() == ModToolTiers.VOID_GLASS;
        }
        if (stack.getItem() instanceof ArmorItem armorItem) {
            return armorItem.getMaterial().equals(ModArmorMaterials.VOID_GLASS);
        }
        return false;
    }

    private static float getSelfHarmChance(ItemStack stack) {
        if (stack.getItem() instanceof TieredItem tieredItem) {
            if (tieredItem.getTier() == ModToolTiers.FRAGILE_GLASS) return SELF_HARM_FRAGILE;
            if (tieredItem.getTier() == ModToolTiers.GLASS) return SELF_HARM_GLASS;
            if (tieredItem.getTier() == ModToolTiers.TOUGHENED_GLASS) return SELF_HARM_TOUGHENED;
            if (tieredItem.getTier() == ModToolTiers.GOLD_INLAID_GLASS) return SELF_HARM_GOLD;
            if (tieredItem.getTier() == ModToolTiers.DIAMOND_INLAID_GLASS) return SELF_HARM_DIAMOND;
        }
        if (stack.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getMaterial().equals(ModArmorMaterials.FRAGILE_GLASS)) return SELF_HARM_FRAGILE;
            if (armorItem.getMaterial().equals(ModArmorMaterials.GLASS)) return SELF_HARM_GLASS;
            if (armorItem.getMaterial().equals(ModArmorMaterials.TOUGHENED_GLASS)) return SELF_HARM_TOUGHENED;
            if (armorItem.getMaterial().equals(ModArmorMaterials.GOLD_INLAID_GLASS)) return SELF_HARM_GOLD;
            if (armorItem.getMaterial().equals(ModArmorMaterials.DIAMOND_INLAID_GLASS)) return SELF_HARM_DIAMOND;
        }
        return 0.0F;
    }

    private static void trySelfHarm(Player player, ItemStack stack) {
        float selfHarmChance = getSelfHarmChance(stack);
        if (selfHarmChance <= 0.0F || RANDOM.nextFloat() >= selfHarmChance) {
            return;
        }

        player.addEffect(new MobEffectInstance(ModMobEffects.GLASS_SHARD, 60, 0)); // 3秒debuff
        player.hurt(player.damageSources().generic(), 1.0F); // 立即扣1点血 (半颗心)
        player.displayClientMessage(Component.translatable("message.raincat_glasscrafts.pricked_self"), true);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.5F, 1.5F);
    }
}
