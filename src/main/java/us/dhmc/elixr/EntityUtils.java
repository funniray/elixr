package us.dhmc.elixr;


import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Location;

public class EntityUtils {
	
	
	/**
	 * Removes item drops near an entity
	 * @param player
	 * @param radius
	 * @return
	 */
    public static int removeNearbyItemDrops(Player player, int radius) {
		int removed = 0;
		/*TODO
		List<Entity> nearby = player.getNearbyEntities(radius, radius, radius);
		for(Entity e : nearby){
			if(e instanceof EntityItem || e instanceof EntityXPOrb){
                e.kill();
                removed++;
            }
		}*/
		return removed;
	}

	
	/**
	 * Whether or not an entity is within a cube radius
	 * @param loc1
	 * @param radius
	 * @param loc2
	 * @return
	 */
	public static boolean inCube(Location loc1, int radius, Location loc2) {
		if(loc1 == null || loc2 == null) return false;
		return (
                loc1.x + radius > loc2.x
                        && loc1.x - radius < loc2.x
                        && loc1.y + radius > loc2.y
                        && loc1.y - radius < loc2.y
                        && loc1.z + radius > loc2.z
                        && loc1.z - radius < loc2.z
				);
	}
	
	
	/**
	 * Determines which blocks a player my "co-exist" with.
	 * 
	 * @param m
	 * @return
	 * @todo doesn't bukkit have this already?
	 */
    public static boolean playerMayPassThrough(int m) {
		switch(m){
            case 0:
            case ItemID.CARROT:
            case BlockID.DEAD_BUSH:
            case BlockID.DETECTOR_RAIL:
            case ItemID.POTATO:
            case BlockID.POWERED_COMPARATOR:
            case BlockID.UNPOWERED_COMPARATOR:
            case BlockID.POWERED_REPEATER:
            case BlockID.UNPOWERED_REPEATER:
            case ItemID.FLOWER_POT:
            case BlockID.LEVER:
            case BlockID.TALL_GRASS:
            case BlockID.MELON_STEM:
            case BlockID.NETHER_WART_BLOCK:
            case BlockID.POWERED_RAIL:
            case BlockID.PUMPKIN_STEM:
            case BlockID.RAIL:
            case BlockID.RED_MUSHROOM:
            case BlockID.ROSE:
            case ItemID.REDSTONE:
            case BlockID.REDSTONE_TORCH:
            case BlockID.UNLIT_REDSTONE_TORCH:
            case BlockID.REDSTONE_WIRE:
            case BlockID.SAPLING:
            case BlockID.SIGN_POST:
            case BlockID.WALL_SIGN:
            case BlockID.SKULL_BLOCK:
            case BlockID.SNOW:
            case BlockID.SUGARCANE_BLOCK:
            case BlockID.STONE_PRESSURE_PLATE:
            case BlockID.TORCH:
            case BlockID.TRIPWIRE:
            case BlockID.WATER_LILY:
            case BlockID.WHEAT_BLOCK:
            case BlockID.WOODEN_PRESSURE_PLATE:
            case BlockID.WOOD_DOOR_BLOCK:
            case BlockID.DANDELION:
				return true;
			default:
				return false;
		}
	}
}