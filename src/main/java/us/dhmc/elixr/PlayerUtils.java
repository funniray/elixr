package us.dhmc.elixr;


import cn.nukkit.Player;
import cn.nukkit.math.BlockFace;
public class PlayerUtils {
    
    
    /**
     * 
     * @param p
     */
    public static void resetPlayer( Player p ){
        
        // Inventory
        p.getInventory().close(p);
        p.getInventory().clearAll();
        p.getInventory().setArmorContents(null);
        InventoryUtils.updateInventory(p);
        
        // Only if player alive
        if (p.isAlive()) {
            
            // Health/Food
            p.setHealth( p.getMaxHealth() );
            //TODO:p.setHunger( 20 );
            
            // Potion Effects
            p.removeAllEffects();
            
            // On fire
            p.setOnFire(0);
            
        }
        
        // Scoreboard
        
        // Turn off flight
        p.setAllowFlight( false );

    }

	/**
	 * http://forums.bukkit.org/threads/directions.91550/#post-1265631
	 * @param player
	 * @return
	 */
	public static BlockFace getPlayerFacing(Player player){

        float y = (float) player.getLocation().getYaw();
        if( y < 0 ) y += 360;
        y %= 360;
        int i = (int) ((y + 8) / 90);
       
        if(i == 0) return BlockFace.WEST;
        else if(i == 4) return BlockFace.NORTH;
        else if(i == 8) return BlockFace.EAST;
        else if(i == 12) return BlockFace.SOUTH;
 
        return BlockFace.WEST;
	 
	}
}