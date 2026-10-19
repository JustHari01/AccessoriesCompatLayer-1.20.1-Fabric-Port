package io.wispforest.accessories_compat.trinkets.wrapper;

import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.Trinket;
import io.wispforest.accessories.Accessories;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.DropRule;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;

public record AccessoryFromTrinket(Trinket trinket) implements Accessory {

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        var ref = TrinketsWrappingUtils.createTrinketsReference(reference);

        if (ref.isEmpty()) {
            Accessory.super.tick(stack, reference);

            return;
        }

        this.trinket.tick(stack, ref.get(), reference.entity());
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        var ref = TrinketsWrappingUtils.createTrinketsReference(reference);

        if (ref.isEmpty()) {
            Accessory.super.onEquip(stack, reference);

            return;
        }

        this.trinket.onEquip(stack, ref.get(), reference.entity());
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        var ref = TrinketsWrappingUtils.createTrinketsReference(reference);

        if (ref.isEmpty()) {
            Accessory.super.onUnequip(stack, reference);

            return;
        }

        this.trinket.onUnequip(stack, ref.get(), reference.entity());
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        var ref = TrinketsWrappingUtils.createTrinketsReference(reference);

        if (ref.isEmpty()) return Accessory.super.canEquip(stack, reference);

        return this.trinket.canEquip(stack, ref.get(), reference.entity());
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference reference) {
        var ref = TrinketsWrappingUtils.createTrinketsReference(reference);

        if (ref.isEmpty()) return Accessory.super.canUnequip(stack, reference);

        return this.trinket.canUnequip(stack, ref.get(), reference.entity());
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        var ref = TrinketsWrappingUtils.createTrinketsReference(reference);

        if (ref.isEmpty()) {
            Accessory.super.getDynamicModifiers(stack, reference, builder);
        } else {
            var id = SlotAttributes.getUuid(ref.get());

            this.trinket.getModifiers(stack, ref.get(), reference.entity(), id).asMap()
                .forEach((attribute, modifiers) -> {
                    for (var modifier : modifiers) {
                        if (modifier.getId().equals(id)) {
                            builder.addStackable(attribute, Accessories.of("trinket_converted_attribute"), modifier.getAmount(), modifier.getOperation());
                        } else {
                            builder.addExclusive(attribute, modifier);
                        }
                    }
                });
        }
    }

    @Override
    public DropRule getDropRule(ItemStack stack, SlotReference reference, DamageSource source) {
        var ref = TrinketsWrappingUtils.createTrinketsReference(reference);

        if (ref.isEmpty()) return Accessory.super.getDropRule(stack, reference, source);

        return TrinketsWrappingUtils.convertDropRule(this.trinket.getDropRule(stack, ref.get(), reference.entity()));
    }

    @Override
    public void onBreak(ItemStack stack, SlotReference reference) {
        var ref = TrinketsWrappingUtils.createTrinketsReference(reference);

        if (ref.isEmpty()) {
            Accessory.super.onBreak(stack, reference);
        } else {
            this.trinket.onBreak(stack, ref.get(), reference.entity());
        }
    }

}
