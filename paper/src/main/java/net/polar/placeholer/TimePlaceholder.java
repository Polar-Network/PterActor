package net.polar.placeholer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.polar.PterActor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;

public class TimePlaceholder extends PlaceholderExpansion {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public @NotNull String getIdentifier() {
        return "pteractor";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(player != null && player.isOnline())
            return onPlaceholderRequest(player.getPlayer(), params);
        return null;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(params.equalsIgnoreCase("time"))
            return dateFormat.format(PterActor.getInstance().getRestartTimeLeft());
        return null;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "PolarMC Team";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }
}
