package net.guizhanss.villagertrade.core.services;

import java.util.List;
import java.util.function.UnaryOperator;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.command.CommandSender;

import net.guizhanss.guizhanlib.minecraft.utils.ChatUtil;
import net.guizhanss.guizhanlib.slimefun.addon.AddonConfig;
import net.guizhanss.villagertrade.VillagerTrade;

public final class LocalizationService {

    private static final String DEFAULT_PREFIX = "&f[&aVillagerTrade&f]&7 ";
    private static final String FILENAME_LANGUAGE = "lang.yml";

    private final AddonConfig languages;

    private String prefix;

    public LocalizationService(VillagerTrade plugin) {
        languages = new AddonConfig(plugin, FILENAME_LANGUAGE);
        languages.reload();

        afterReload();
    }

    public void reloadAll() {
        languages.reload();

        afterReload();
    }

    private void afterReload() {
        prefix = getString("prefix", DEFAULT_PREFIX);
    }

    @Nonnull
    public String getString(@Nonnull String key) {
        return getString(key, "");
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public String getString(String key, String def) {
        return languages.getString(key, def);
    }


    @Nonnull
    public List<String> getStringList(@Nonnull String key) {
        return languages.getStringList(key);
    }

    @Nonnull
    public String getCommandDescription(@Nonnull String command) {
        return getString("messages.commands." + command + ".description", "");
    }

    @ParametersAreNonnullByDefault
    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatUtil.color(prefix + message));
    }

    @ParametersAreNonnullByDefault
    public void sendKeyedMessage(CommandSender sender, String key) {
        sendKeyedMessage(sender, key, msg -> msg);
    }

    @ParametersAreNonnullByDefault
    public void sendKeyedMessage(CommandSender sender, String key, UnaryOperator<String> function) {
        sendMessage(sender, function.apply(getString("messages." + key)));
    }
}
