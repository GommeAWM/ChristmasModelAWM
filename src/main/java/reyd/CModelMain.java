package reyd;

import cn.nukkit.plugin.PluginBase;
import reyd.Command.ChristmasCMD;
import reyd.Listener.ChristmasListener;

import java.util.ArrayList;
import java.util.List;

public class CModelMain extends PluginBase {

    /*
    *
    * Merry Christmas
    *
    * */

    private static CModelMain instance;

    public List<String> christmasEdit = new ArrayList<>();

    @Override
    public void onLoad() {
        instance = this;
        this.saveResource("snowman.png");
        this.saveResource("snowman.json");
        this.saveResource("gift.png");
        this.saveResource("gift.json");
        this.saveResource("dog.png");
        this.saveResource("dog.json");
    }

    @Override
    public void onEnable() {

        getServer().getCommandMap().register("help", new ChristmasCMD());
        getServer().getPluginManager().registerEvents(new ChristmasListener(), this);

        this.getLogger().alert("Merry Christmas!");
        this.getLogger().alert("FOR SUPPORT/SUGGESTIONS/CUSTOM PLUGINS USE OUR DISCORD CHANNEL: https://discord.gg/mAZ9YMV3");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static CModelMain getInstance() {
        return instance;
    }
}
