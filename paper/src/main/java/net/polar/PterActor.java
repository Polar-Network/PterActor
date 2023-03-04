package net.polar;

import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.polar.informer.InformerType;
import net.polar.placeholer.TimePlaceholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PterActor extends JavaPlugin implements PterActorPlugin {

    private static PterActor instance;
    private PteroClient client;
    private PterActorConfig pterActorConfig;
    private final AtomicLong restartTimeLeft = new AtomicLong(0);

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.init();
        scheduleConfigRestart();
        new TimePlaceholder().register();
    }

    @Override
    public void onDisable() {
        this.shutdown();
    }

    @Override
    public void runAsync(Runnable runnable) {
        getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }

    @Override
    public void scheduleRestart(int seconds) {
        AtomicInteger counter = new AtomicInteger(seconds);
        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            int current = counter.decrementAndGet();
            restartTimeLeft.set(current * 1000L);
            Component msg = getShutdownMessage(current);
            if (msg == null) return;

            switch(this.getPterActorConfig().informerType()) {
                case CHAT -> {
                    getServer().broadcast(msg);
                }
                case TITLE -> {
                    getServer().getOnlinePlayers().forEach(player -> {
                        player.showTitle(Title.title(msg, Component.empty()));
                    });
                }
                case BOSSBAR -> {
                    getServer().getOnlinePlayers().forEach(player -> {
                        player.showBossBar(BossBar.bossBar(msg, 1.0f, BossBar.Color.RED, BossBar.Overlay.PROGRESS));
                    });
                }
                case ACTIONBAR -> {
                    getServer().getOnlinePlayers().forEach(player -> {
                        player.sendActionBar(msg);
                    });
                }
            }
            if (current == 0) {
                instantRestart();
            }
        }, seconds * 20L, 20L);
    }


    @Override
    public PteroClient getPteroClient() {
        return client;
    }

    @Override
    public void setPteroClient(PteroClient client) {
        this.client = client;
    }

    @Override
    public PterActorConfig getPterActorConfig() {
        return pterActorConfig;
    }

    @Override
    public void reloadPterActorConfig() {
        String host = getConfig().getString("pterodactyl.host");
        String key = getConfig().getString("pterodactyl.key");
        String serverId = getConfig().getString("pterodactyl.server-id");
        InformerType informer = InformerType.valueOf(getConfig().getString("informer"));

        int restartInterval = getConfig().getInt("restart-interval") * 60;

        ConfigurationSection restart = getConfig().getConfigurationSection("restart");
        if (restart == null) {
            getLogger().warning("Restart section is not found in config.yml");
            return;
        }
        Map<Integer, String> rm = new HashMap<>();
        restart.getKeys(false).forEach(k -> {
            int time = Integer.parseInt(k);
            String msg = restart.getString(k);
            rm.put(time, msg);
        });
        this.pterActorConfig = new PterActorConfig(host, key, serverId, rm, restartInterval, informer);
    }

    public static PterActor getInstance() {
        return instance;
    }

    public AtomicLong getRestartTimeLeft() {
        return restartTimeLeft;
    }
}
