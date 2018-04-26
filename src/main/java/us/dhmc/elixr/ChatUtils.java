package us.dhmc.elixr;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Location;

public class ChatUtils {

    public static void notifyNearby(Location loc, int radius, String msg) {
        for (final Player p : Server.getInstance().getOnlinePlayers().values()) {
            if (loc.getLevel().equals(p.getLevel())) {
                if( loc.distance( p.getLocation() ) <= radius ) {
                    p.sendMessage( msg );
                }
            }
        }
    }
}