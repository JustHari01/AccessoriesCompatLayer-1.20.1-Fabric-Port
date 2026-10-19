package io.wispforest.accessories_compat.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.client.DefaultAccessoryRenderer;
import com.llamalad7.mixinextras.sugar.Local;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories_compat.api.ModCompatibilityModule;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AccessoriesRendererRegistry.class)
public abstract class AccessoriesRendererRegistryMixin {
    @ModifyReturnValue(method = "getRender(Lnet/minecraft/world/item/Item;)Lio/wispforest/accessories/api/client/AccessoryRenderer;", at = @At("RETURN"))
    private static AccessoryRenderer alterDefaultBehavior(AccessoryRenderer original, @Local(argsOnly = true) Item item) {
        if (original != DefaultAccessoryRenderer.INSTANCE) return original;

        for (var value : ModCompatibilityModule.getModules().values()) {
            if (value.skipDefaultRenderer(item)) {
                return null;
            }
        }

        return original;
    }
}
