package io.wispforest.accessories_compat.trinkets.mixin;

import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.emi.trinkets.api.SlotAttributes;
import io.wispforest.accessories_compat.trinkets.wrapper.TrinketsWrappingUtils;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

@Mixin(SlotAttributes.class)
public abstract class SlotAttributesMixin {

    @WrapMethod(method = "addSlotModifier", remap = false)
    private static void adjustAddSlotModifier(Multimap<Attribute, AttributeModifier> map, String slot, UUID id, double amount, AttributeModifier.Operation operation, Operation<Void> original) {
        var data = TrinketsWrappingUtils.splitGroupInfo(slot);
        var slotType = TrinketsWrappingUtils.trinketsToAccessories_Slot(data.left(), data.right());

        io.wispforest.accessories.api.attributes.SlotAttribute.addSlotModifier(map, slotType, slot, id, amount, operation);
    }
}
