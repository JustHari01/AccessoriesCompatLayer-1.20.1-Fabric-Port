package io.wispforest.accessories_compat.fabric;

import io.wispforest.accessories_compat.api.ModCompatibilityModule;
import io.wispforest.accessories_compat.networking.s2c.ModuleExportedSlots;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class AccessoriesCompatFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
            client.execute(ModCompatibilityModule::clearClientSyncData)
        );

        ClientPlayNetworking.registerGlobalReceiver(AccessoriesCompatFabric.EXPORTED_SLOTS_CHANNEL,
            (client, handler, buf, responseSender) -> {
                var exportedSlots = AccessoriesCompatFabric.readExportedSlots(buf);
                if (exportedSlots == null) return;

                client.execute(() -> ModCompatibilityModule.handleSyncPacket(new ModuleExportedSlots(exportedSlots)));
            }
        );
    }
}
