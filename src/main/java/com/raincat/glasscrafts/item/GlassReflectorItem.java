package com.raincat.glasscrafts.item;

import net.minecraft.world.item.Item;

public class GlassReflectorItem extends Item {
    public GlassReflectorItem(Item.Properties properties) {
        super(properties);
    }
    // 具体的反弹弹射物逻辑在 GlassToolEventHandler 事件监听器中实现以保证最佳稳定度
}
