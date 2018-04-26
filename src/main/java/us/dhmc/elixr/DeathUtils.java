package us.dhmc.elixr;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.mob.EntitySkeleton;
import cn.nukkit.entity.mob.EntityWitherSkeleton;
import cn.nukkit.entity.projectile.EntityArrow;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDeathEvent;
import cn.nukkit.item.Item;

public class DeathUtils {

	public static String getCauseNiceName( Entity entity ){
		
		EntityDamageEvent e = entity.getLastDamageCause();
		
		if(e == null){
			return "unknown";
		}
		
		// Determine the root cause
		EntityDamageEvent.DamageCause damageCause = e.getCause();
		Entity killer = null;

		// If was damaged by an entity
		if(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
			// Arrow?
			if (entityDamageByEntityEvent.getDamager() instanceof EntityArrow) {
				EntityArrow arrow = (EntityArrow) entityDamageByEntityEvent.getDamager();
				Entity source = arrow.shootingEntity;
				if (source instanceof Player) {
					killer = (source);
				}
			} else {
				killer = entityDamageByEntityEvent.getDamager();
			}
		}

        if( entity instanceof Player ){
        	
        	Player player = (Player) entity;

	        // Detect additional suicide. For example, when you potion
	        // yourself with instant damage it doesn't show as suicide.
	        if( killer instanceof Player ){
	        	// Themself
	        	if(((Player)killer).getName().equals( player.getName() )){
	        		return "suicide";
	        	}
		        // translate bukkit events to nicer names
				if ((damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || damageCause.equals(EntityDamageEvent.DamageCause.PROJECTILE))) {
		        	return "pvp";
		        }
	        }
        }
        
        // Causes of death for either entities or players
		if (damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
        	return "mob";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
        	return "skeleton";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
        	return "creeper";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.CONTACT)) {
        	return "cactus";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
        	return "tnt";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.FIRE) || damageCause.equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
        	return "fire";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.MAGIC)) {
        	return "potion";
        }
        return damageCause.name().toLowerCase();
	}

	public static String getAttackerName( Entity victim ){
		
		// Determine base cause
		String cause = getCauseNiceName( victim );
		
        if( victim instanceof Player ){
			Entity killer = ((Player) victim).getKiller();
        	if( killer != null ){
        		return killer.getName();
        	}
        }
        	
        if(cause == "mob"){
        	
        	Entity killer = ((EntityDamageByEntityEvent)victim.getLastDamageCause()).getDamager();

        	// Playa!
        	if( killer instanceof Player ){
        		return ((Player)killer).getName(); 
        	}
        	// Which skeleton type?
			if (killer instanceof EntityWitherSkeleton) {
				return "witherskeleton";
			} else if (killer instanceof EntitySkeleton) {
				return "skeleton";
			}
        	// Shot!
			else if (killer instanceof EntityArrow) {
        		return "skeleton";
        	}
        	else {
				return killer.getName().toLowerCase();
        	}
        }
        return cause;
	}

	public static String getVictimName( Entity victim ){
		
        if( victim instanceof Player ){
        	return ((Player)victim).getName();
        } else {

			// Playa!
			if (victim instanceof Player) {
				return ((Player) victim).getName();
			}
			// Which skeleton type?
			if (victim instanceof EntityWitherSkeleton) {
				return "witherskeleton";
			} else if (victim instanceof EntitySkeleton) {
				return "skeleton";
			}
			// Shot!
			else if (victim instanceof EntityArrow) {
				return "skeleton";
			} else {
				return victim.getName().toLowerCase();
			}
        }
	}
	
	
	/**
	 * Determines the owner of a tamed wolf.
	 * @param event
	 * @return
	 */
	public static String getTameWolfOwner(EntityDeathEvent event){
		return "TODO";
	}
	
	
	/**
	 * Determines the weapon used to kill an entity.
	 * @param p
	 * @return
	 */
	public static String getWeapon(Player p){
        String death_weapon = "";
        if(p.getKiller() instanceof Player){
			Item weapon = ((Player) p.getKiller()).getInventory().getItemInHand();
			death_weapon = weapon.getName().toLowerCase();
        	death_weapon = death_weapon.replaceAll("_", " ");
        	if(death_weapon.equalsIgnoreCase("air")){
        		death_weapon = " hands";
        	}
        }
        return death_weapon;
	}
}