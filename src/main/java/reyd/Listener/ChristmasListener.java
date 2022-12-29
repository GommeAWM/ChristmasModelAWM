package reyd.Listener;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import reyd.CModelMain;

public class ChristmasListener implements Listener {


    /*
     *
     * Merry Christmas
     *
     * */

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (entity.namedTag.getBoolean("chm")) {
            e.setCancelled();
            if (e instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) e;
                if (ev.getDamager() instanceof Player) {
                    Player player = (Player) ev.getDamager();

                    if (CModelMain.getInstance().christmasEdit.isEmpty()){
                        return;
                    } else {
                        if (CModelMain.getInstance().christmasEdit.contains(player.getName())){
                            entity.namedTag.remove("chm");
                            player.sendMessage("§aYou have successfully rеmoved the entity's godmode!");
                            player.sendMessage("§aNow you can kill entity!");
                            CModelMain.getInstance().christmasEdit.remove(player.getName());
                        }
                    }

                }
            }
        }
    }

}
