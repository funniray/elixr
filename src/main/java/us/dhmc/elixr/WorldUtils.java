package us.dhmc.elixr;


import cn.nukkit.level.Location;

public class WorldUtils {
    
    /**
     * Creates lightning that doesn't strike the ground, only thunder is heard
     * @param loc the location to strike lightning above
     */
    public static void thunder( Location loc ){
        loc.y = 350D;
        //TODO: loc.getLevel().strikeLightningEffect(loc);
    }
}