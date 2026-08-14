package com.raincat.glasscrafts.client;

import com.raincat.glasscrafts.GlassCrafts;
import com.raincat.glasscrafts.entity.ModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = GlassCrafts.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // 使用原版的 ThrownItemRenderer 渲染抛掷出的玻璃珍珠
        event.registerEntityRenderer(ModEntities.GLASS_PEARL.get(), ThrownItemRenderer::new);
    }
}
