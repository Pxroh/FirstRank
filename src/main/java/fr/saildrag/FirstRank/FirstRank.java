package fr.saildrag.FirstRank;

import fr.maxlego08.menu.api.InventoryManager;
import fr.maxlego08.menu.api.command.CommandManager;
import fr.saildrag.FirstRank.core.RequirementManager;
import fr.saildrag.FirstRank.dataBase.UserManager;
import fr.saildrag.library.SaildragPlugin;
import fr.saildrag.FirstRank.event.zMenuListener;
import fr.saildrag.FirstRank.lists.ListCommand;
import fr.saildrag.FirstRank.lists.Message;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class FirstRank extends SaildragPlugin<FirstRank> {
    private static FirstRank plugin;
    private UserManager userManager;

    @Override
    public void onEnable() {
        plugin = this;
        onEnable(this);

        long start = System.currentTimeMillis();
        this.plugin.getUtils().getLogger().success("&e=== ENABLE START ===");
        onEnable(this);
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new zMenuListener(this), this);

        this.userManager = new UserManager(this);
        getStorage().onEnable();

        new BukkitRunnable() {
            @Override
            public void run() {
                userManager.save();
            }
        }.runTaskTimerAsynchronously(this, 0L, 20L * 60L * 60L * 2L); // 20 ticks * 60 sec * 60 min * 2 = 2 heure

        this.plugin.getUtils().getLogger().success("&e=== ENABLE DONE" + " &7(&6" + (System.currentTimeMillis() - start) + "ms&7)&e ===");
    }

    @Override
    public void onDisable() {
        long start = System.currentTimeMillis();
        this.plugin.getUtils().getLogger().success("&e=== DISABLE START ===");
        Bukkit.getScheduler().cancelTasks(this);
        this.userManager.save();
        getStorage().onDisable();
        this.plugin.getUtils().getLogger().success("&e=== DISABLE DONE" + " &7(&6" + (System.currentTimeMillis() - start) + "ms&7)&e ===");
    }

    public static FirstRank getInstance(){
        return plugin;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    public void loadConfig() {
        saveDefaultConfig();
        reloadConfig();
        RequirementManager.loadConfig(this);
        setDebug();
        loadLang(ListCommand.class, Message.class, null);
        loadCommand();
        loadInventories();
    }

    public void loadCommand() {
        File file = new File(this.plugin.getDataFolder(), "config.yml");

        CommandManager commandManager = zMenuListener.getCommandManager();
        commandManager.unregisterCommands(this.plugin);
        commandManager.loadCommand(this.plugin, file);
    }

    public void loadInventories() {

        // Check if file exist
        File folder = new File(this.plugin.getDataFolder(), "inventories");
        if (!folder.exists()) {
            folder.mkdir();
            registerDefaultFiles();
        }

        InventoryManager inventoryManager = zMenuListener.getInventoryManager();
        inventoryManager.deleteInventories(this.plugin);

        files(folder, zMenuListener::loadInventory);
    }


    private void registerDefaultFiles() {
        List<String> files = new ArrayList<>();
        files.add("inventories/rank.yml");
        files.add("inventories/preview.yml");

        files.forEach(e -> {
            if (!new File(this.plugin.getDataFolder(), e).exists()) {
                plugin.saveResource(e, false);
            }
        });
    }

    private void files(File folder, Consumer<File> consumer) {
        try (Stream<Path> s = Files.walk(Paths.get(folder.getPath()))) {
            s.skip(1).map(Path::toFile).filter(File::isFile).filter(e -> e.getName().endsWith(".yml")).forEach(consumer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
