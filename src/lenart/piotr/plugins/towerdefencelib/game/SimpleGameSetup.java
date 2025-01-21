package lenart.piotr.plugins.towerdefencelib.game;

import lenart.piotr.plugins.towerdefencelib.LibraryManager;
import lenart.piotr.plugins.towerdefencelib.exceptions.PluginNotInitializedException;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleGameSetup {
    JavaPlugin plugin;
    public SimpleGameSetup(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void SetupGame() {
        LibraryManager lib;
        try { lib = LibraryManager.getManager(plugin);
        } catch (PluginNotInitializedException e) {
            log("No Library Manager Fond!");
            return;
        }
    }

    void log(String message) {
        plugin.getServer().getConsoleSender().sendMessage(message);
    }
}
