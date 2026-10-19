package io.wispforest.accessories_compat.trinkets.wrapper;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketEnums;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public record TrinketFromAccessory(Accessory accessory) implements Trinket {

    @Override
    public void tick(ItemStack stack, SlotReference ref, LivingEntity entity) {
        var slotName = ((WrappedTrinketInventory) ref.inventory()).container.getSlotName();

        var reference = io.wispforest.accessories.api.slot.SlotReference.of(entity, slotName, ref.index());

        accessory.tick(stack, reference);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference ref, LivingEntity entity) {
        var slotName = ((WrappedTrinketInventory) ref.inventory()).container.getSlotName();

        var reference = io.wispforest.accessories.api.slot.SlotReference.of(entity, slotName, ref.index());

        accessory.onEquip(stack, reference);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference ref, LivingEntity entity) {
        var slotName = ((WrappedTrinketInventory) ref.inventory()).container.getSlotName();

        var reference = io.wispforest.accessories.api.slot.SlotReference.of(entity, slotName, ref.index());

        accessory.onUnequip(stack, reference);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference ref, LivingEntity entity) {
        var slotName = ((WrappedTrinketInventory) ref.inventory()).container.getSlotName();

        var reference = io.wispforest.accessories.api.slot.SlotReference.of(entity, slotName, ref.index());

        return accessory.canEquip(stack, reference);
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference ref, LivingEntity entity) {
        var slotName = ((WrappedTrinketInventory) ref.inventory()).container.getSlotName();

        var reference = io.wispforest.accessories.api.slot.SlotReference.of(entity, slotName, ref.index());

        return accessory.canUnequip(stack, reference);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, SlotReference ref, LivingEntity entity, UUID id) {
        var slotName = ((WrappedTrinketInventory) ref.inventory()).container.getSlotName();

        var reference = io.wispforest.accessories.api.slot.SlotReference.of(entity, slotName, ref.index());

        var builder = new AccessoryAttributeBuilder(reference);

        accessory.getDynamicModifiers(stack, reference, builder);

        return builder.getAttributeModifiers(true);
    }

    @Override
    public TrinketEnums.DropRule getDropRule(ItemStack stack, SlotReference ref, LivingEntity entity) {
        var slotName = ((WrappedTrinketInventory) ref.inventory()).container.getSlotName();

        var reference = io.wispforest.accessories.api.slot.SlotReference.of(entity, slotName, ref.index());

        var damageSource = entity.getLastDamageSource();

        if (damageSource == null) damageSource = entity.level().damageSources().generic();

        return TrinketsWrappingUtils.convertDropRule(accessory.getDropRule(stack, reference, damageSource));
    }

    @Override
    public void onBreak(ItemStack stack, SlotReference ref, LivingEntity entity) {
        var slotName = ((WrappedTrinketInventory) ref.inventory()).container.getSlotName();

        var reference = io.wispforest.accessories.api.slot.SlotReference.of(entity, slotName, ref.index());

        accessory.onBreak(stack, reference);
    }

}
