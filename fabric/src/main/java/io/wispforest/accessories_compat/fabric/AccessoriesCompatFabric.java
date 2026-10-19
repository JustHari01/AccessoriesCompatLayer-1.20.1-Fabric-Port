package io.wispforest.accessories_compat.fabric;

import io.wispforest.accessories_compat.api.ModCompatibilityModule;
import io.wispforest.accessories_compat.networking.s2c.ModuleExportedSlots;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class AccessoriesCompatFabric implements ModInitializer {
    static final ResourceLocation EXPORTED_SLOTS_CHANNEL =
        new ResourceLocation("accessories_compat_layer", "module_exported_slots");

    private static final int MAX_MODULES = 512;
    private static final int MAX_SLOTS_PER_MODULE = 1024;

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> sendExportedSlots(player));
    }

    private static void sendExportedSlots(ServerPlayer player) {
        if (!ServerPlayNetworking.canSend(player, EXPORTED_SLOTS_CHANNEL)) return;

        ModuleExportedSlots packet = ModCompatibilityModule.createSyncPacket();
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(packet.exportedSlots().size());

        packet.exportedSlots().forEach((moduleId, slots) -> {
            buf.writeUtf(moduleId, 32767);
            buf.writeVarInt(slots.size());
            slots.forEach(slot -> buf.writeUtf(slot, 32767));
        });

        ServerPlayNetworking.send(player, EXPORTED_SLOTS_CHANNEL, buf);
    }

    static Map<String, Set<String>> readExportedSlots(FriendlyByteBuf buf) {
        int moduleCount = buf.readVarInt();
        if (moduleCount < 0 || moduleCount > MAX_MODULES) return null;

        Map<String, Set<String>> result = new HashMap<>();
        for (int moduleIndex = 0; moduleIndex < moduleCount; moduleIndex++) {
            String moduleId = buf.readUtf(32767);
            int slotCount = buf.readVarInt();
            if (slotCount < 0 || slotCount > MAX_SLOTS_PER_MODULE) return null;

            Set<String> slots = new HashSet<>();
            for (int slotIndex = 0; slotIndex < slotCount; slotIndex++) {
                slots.add(buf.readUtf(32767));
            }
            result.put(moduleId, slots);
        }

        return result;
    }
}
