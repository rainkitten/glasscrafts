package com.raincat.glasscrafts.event;

import com.raincat.glasscrafts.GlassCrafts;
import com.raincat.glasscrafts.init.ModArmorMaterials;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = GlassCrafts.MODID)
public class GlassArmorEventHandler {

    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };

    /**
     * 虚无玻璃盔甲特性 1：免疫火属性与凋零伤害
     */
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;

        if (hasAnyVoidGlassArmor(entity)) {
            // 免疫火和凋零伤害
            if (event.getSource().is(net.minecraft.tags.DamageTypeTags.IS_FIRE) ||
                event.getSource().is(net.minecraft.world.damagesource.DamageTypes.WITHER)) {
                event.setCanceled(true);
            }
        }
    }

    /**
     * 虚无玻璃盔甲特性 2：若穿戴虚无玻璃头盔，赋予水下呼吸效果
     */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;

        ItemStack helmet = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.isEmpty() && helmet.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getMaterial().equals(ModArmorMaterials.VOID_GLASS)) {
                entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 260, 0, false, false, true));
            }
        }
    }

    private static boolean hasAnyVoidGlassArmor(LivingEntity entity) {
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (!stack.isEmpty() && stack.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial().equals(ModArmorMaterials.VOID_GLASS)) {
                    return true;
                }
            }
        }
        return false;
    }
}
