package fr.saildrag.FirstRank.dataBase;

import fr.maxlego08.sarah.MigrationManager;
import fr.saildrag.FirstRank.FirstRank;
import fr.saildrag.library.storage.Service;
import fr.saildrag.FirstRank.event.UserListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class UserManager {
    String STORAGE_TABLE_NAME = "virtual_storage";
    private final FirstRank plugin;
    private final Service<UserStorage, UserStorageDTO> userStorageService;
    private final Map<UUID, User> users;

    public UserManager(FirstRank plugin) {
        this.plugin = plugin;
        this.userStorageService = new Service<>(plugin, UserStorageDTO.class, new UserStorageRepository(), STORAGE_TABLE_NAME);

        this.users = new HashMap<>();

        plugin.getStorage().registerMigration(new UserStorageMigration(STORAGE_TABLE_NAME));

        Bukkit.getOnlinePlayers().forEach(this::handleJoin);

        Bukkit.getServer().getPluginManager().registerEvents(new UserListener(this), plugin);
    }

    public void handleJoin(Player player) {
        this.getUser(player);
    }

    public void handleQuit(Player player) {
        User user = this.users.remove(player.getUniqueId());
        if(user != null) {
            this.saveUser(user);
        }
    }

    public User getUser(String name) {
        return this.users.values().stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(generateUser(Bukkit.getPlayerUniqueId(name), name));
    }

    @NotNull
    public User getUser(Player player) {
        if(this.users.containsKey(player.getUniqueId())) {
            return this.users.get(player.getUniqueId());
        }
        return this.generateUser(player.getUniqueId(), player.getName());
    }
    @NotNull
    public User getUser(UUID uuid){
        if(this.users.containsKey(uuid)) {
            return this.users.get(uuid);
        }
        return this.generateUser(uuid, Bukkit.getOfflinePlayer(uuid).getName());
    }

    public List<User> getUsers() {
        return new ArrayList<>(this.users.values());
    }

    private User generateUser(UUID uuid, String name){
        User user = new User(uuid, name);
        this.userStorageService.where("player_id", uuid.toString()).forEach(element ->
                user.addVirtualStorage(element.getId(), element));
        this.users.put(uuid, user);
        this.cacheUser(user);
        return user;
    }

    private void cacheUser(User user) {
        this.users.put(user.getUUID(), user);

        Bukkit.getScheduler().runTaskLaterAsynchronously(
                this.plugin,
                () -> {
                    User userInner = this.users.remove(user.getUUID());
                    if (userInner != null) {
                        saveUser(userInner);
                    }
                },
                15 * 60 * 20L
        );
    }

    public void saveUser(User user) {
        this.userStorageService.save(
                user.getVirtualStorage().stream()
                        .filter(virtualStorage -> virtualStorage.getValue() != 0)
                        .toList()
        );
    }

    public void save() {
        this.users.values().forEach(this::saveUser);
    }
}
