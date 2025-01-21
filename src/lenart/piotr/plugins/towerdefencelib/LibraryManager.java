package lenart.piotr.plugins.towerdefencelib;

import lenart.piotr.plugins.towerdefencelib.exceptions.PluginNotInitializedException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class LibraryManager {
    LibraryManager() {}
    public static LibraryManager getManager(JavaPlugin plugin) throws PluginNotInitializedException {
        Plugin tdfPlugin = plugin.getServer().getPluginManager().getPlugin("TowerDefenceLibrary");
        if (tdfPlugin == null) throw new PluginNotInitializedException("TowerDefenceLibrary not found");
        Main tdfMainPlugin = (Main) tdfPlugin;
        return tdfMainPlugin.getLibraryManager();
    }
}
