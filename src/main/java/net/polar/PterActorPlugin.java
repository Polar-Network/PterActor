package net.polar;

import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public interface PterActorPlugin {

    PterActorConfig getPterActorConfig();

    PteroClient getPteroClient();

    void setPteroClient(PteroClient client);

    default void init() {
        reloadPterActorConfig();
        PteroClient client = PteroBuilder.createClient(getPterActorConfig().pterodactylHost(), getPterActorConfig().pterodactylKey());
        setPteroClient(client);
    }

    default void shutdown() {
    }

    void reloadPterActorConfig();

    default @Nullable Component getShutdownMessage(int second) {
        String message = getPterActorConfig().shutdownMessages().get(second);
        if (message == null) return null;
        return MiniMessage.miniMessage().deserialize(message);
    }

    default CompletableFuture<ClientServer> getPterActorServer() {

        CompletableFuture<ClientServer> future = new CompletableFuture<>();
        runAsync(() -> {
            try {
                future.complete(getPteroClient().retrieveServerByIdentifier(getPterActorConfig().primaryServerId()).execute());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    void runAsync(Runnable runnable);

    void scheduleRestart(int seconds);

    default void scheduleConfigRestart() {
        scheduleRestart(getPterActorConfig().shutdownInterval());
    }

    default void instantRestart() {
        getPterActorServer().thenAccept(server -> server.restart().executeAsync());
    }

}
