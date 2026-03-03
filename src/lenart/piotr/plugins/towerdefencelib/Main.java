package lenart.piotr.plugins.towerdefencelib;

import lenart.piotr.plugins.towerdefencelib.game.SimpleGameSetup;
import lenart.piotr.plugins.towerdefencelib.inventory.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    LibraryManager manager;

    @Override
    public void onEnable() {
        InventoryManager.init(this);

        manager = new LibraryManager();

        SimpleGameSetup gameSetup = new SimpleGameSetup(this);
        gameSetup.SetupGame();
    }

    public LibraryManager getLibraryManager() {
        return manager;
    }
}
