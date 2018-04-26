package us.dhmc.elixr;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;


public class EnchantmentUtils {
	
	
	/**
	 * Given an enchantment, does the current item have any that conflict
	 * @return
	 */
	public static boolean hasConflictingEnchanment(Item item, Enchantment ench) {
		Enchantment[] enchantments = item.getEnchantments();
		boolean conflict = false;
		for (Enchantment e : enchantments) {
			if (ench.isCompatibleWith(e) || ench == e) {
				conflict = true;
			}
		}
		return conflict;
	}
	
	
	/**
	 * Return the enchantment based on a common name
	 * @param name
	 * @return
	 */
	public static Enchantment getEnchantmentFromCommonName( String name ){
		int id = 1000;
		if(name.equalsIgnoreCase("aquaaffinity")){
			id = Enchantment.ID_WATER_WORKER;
		}
		else if(name.equalsIgnoreCase("bane")){
			id = Enchantment.ID_DAMAGE_ARTHROPODS;
		}
		else if(name.equalsIgnoreCase("efficiency")){
			id = Enchantment.ID_EFFICIENCY;
		}
		else if(name.equalsIgnoreCase("explosion")){
			id = Enchantment.ID_PROTECTION_EXPLOSION;
		}
		else if(name.equalsIgnoreCase("fall")){
			id = Enchantment.ID_PROTECTION_FALL;
		}
		else if(name.equalsIgnoreCase("fire")){
			id = Enchantment.ID_PROTECTION_FIRE;
		}
		else if(name.equalsIgnoreCase("fireaspect")){
			id = Enchantment.ID_FIRE_ASPECT;
		}
		else if(name.equalsIgnoreCase("flame")){
			id = Enchantment.ID_BOW_FLAME;
		}
		else if(name.equalsIgnoreCase("fortune")){
			id = Enchantment.ID_FORTUNE_DIGGING;
		}
		else if(name.equalsIgnoreCase("infinity")){
			id = Enchantment.ID_BOW_INFINITY;
		}
		else if(name.equalsIgnoreCase("knockback")){
			id = Enchantment.ID_KNOCKBACK;
		}
		else if(name.equalsIgnoreCase("looting")){
			id = Enchantment.ID_LOOTING;
		}
		else if(name.equalsIgnoreCase("lure")){
			id = Enchantment.ID_LURE;
		}
		else if(name.equalsIgnoreCase("power")){
			id = Enchantment.ID_BOW_POWER;
		}
		else if(name.equalsIgnoreCase("projectile")){
			id = Enchantment.ID_PROTECTION_PROJECTILE;
		}
		else if(name.equalsIgnoreCase("protection")){
			id = Enchantment.ID_PROTECTION_ALL;
		}
		else if(name.equalsIgnoreCase("punch")){
			id = Enchantment.ID_BOW_KNOCKBACK;
		}
		else if(name.equalsIgnoreCase("respiration")){
			id = Enchantment.ID_WATER_BREATHING;
		}
		else if(name.equalsIgnoreCase("sharpness")){
			id = Enchantment.ID_DAMAGE_ALL;
		}
		else if(name.equalsIgnoreCase("silktouch")){
			id = Enchantment.ID_SILK_TOUCH;
		}
		else if(name.equalsIgnoreCase("smite")){
			id = Enchantment.ID_DAMAGE_SMITE;
		}
		else if(name.equalsIgnoreCase("unbreaking")){
			id = Enchantment.ID_DURABILITY;
		}
		return Enchantment.get(id);
	}


	/**
	 * Return the common name for an enchantment
	 * @param ench
	 * @param level
	 * @return
	 */
	public static String getClientSideEnchantmentName( Enchantment ench, int level ){
		
		String ench_name = "";

		int id = ench.getId();

		if (id == Enchantment.ID_BOW_POWER) {
			ench_name = "power";
		} else if (id == Enchantment.ID_BOW_FLAME) {
			ench_name = "flame";
		} else if (id == Enchantment.ID_BOW_INFINITY) {
			ench_name = "infinity";
		} else if (id == Enchantment.ID_BOW_KNOCKBACK) {
			ench_name = "punch";
		} else if (id == Enchantment.ID_DAMAGE_ALL) {
			ench_name = "sharpness";
		} else if (id == Enchantment.ID_DAMAGE_ARTHROPODS) {
			ench_name = "bane of anthropods";
		} else if (id == Enchantment.ID_DAMAGE_SMITE) {
			ench_name = "damage undead";
		} else if (id == Enchantment.ID_EFFICIENCY) {
			ench_name = "efficiency";
		} else if (id == Enchantment.ID_DURABILITY) {
			ench_name = "unbreaking";
		} else if (id == Enchantment.ID_FORTUNE_DIGGING) {
			ench_name = "fortune";
		} else if (id == Enchantment.ID_LOOTING) {
			ench_name = "looting";
		} else if (id == Enchantment.ID_WATER_BREATHING) {
			ench_name = "respiration";
		} else if (id == Enchantment.ID_PROTECTION_ALL) {
			ench_name = "protection";
		} else if (id == Enchantment.ID_PROTECTION_EXPLOSION) {
			ench_name = "blast protection";
		} else if (id == Enchantment.ID_PROTECTION_FALL) {
			ench_name = "feather falling";
		} else if (id == Enchantment.ID_PROTECTION_FIRE) {
			ench_name = "fire protection";
		} else if (id == Enchantment.ID_PROTECTION_PROJECTILE) {
			ench_name = "projectile protection";
		} else if (id == Enchantment.ID_WATER_WORKER) {
			ench_name = "aqua affinity";
		}
		else {
			// can leave as-is: SILK_TOUCH, FIRE_ASPECT, KNOCKBACK, THORNS, LUCK, LURE
			ench_name = ench.getName().toLowerCase().replace("_", " ");
		}
		
		if(level == 1){
			ench_name += " I";
		}
		else if(level == 2){
			ench_name += " II";
		}
		else if(level == 3){
			ench_name += " III";
		}
		else if(level == 4){
			ench_name += " IV";
		}
		else if(level == 5){
			ench_name += " V";
		}
		
		return ench_name;
	
	}
}