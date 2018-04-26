package us.dhmc.elixr;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Location;

import java.util.Map;
import java.util.Map.Entry;

public class ItemUtils {
    
    /**
     * 
     * @param item
     * @return
     */
    public static boolean isValidItem(Item item) {
        return (item != null && item.getId() != 0);
    }
    
    /**
     * 
     * @param a
     * @param b
     * @param checkDura
     * @return
     */
    public static boolean isSameType(Item a, Item b, boolean checkDura) {
        
        // Initial type check
        if (a.getId() != b.getId()) return false;
        
        // Durability check
        if (checkDura && a.getDamage() != b.getDamage()) return false;
        
        return true;
        
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(Item a, Item b) {
        return equals(a, b, dataValueUsedForSubitems(a.getId()));
    }
    
    /**
     * 
     * @param a
     * @param b
     * @param checkDura
     * @return
     */
    public static boolean equals(Item a, Item b, boolean checkDura) {

        
        // Type/dura
        if( !isSameType(a,b,checkDura) ) return false;

        return a.getCompoundTag() == b.getCompoundTag();
        
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return
     */
    protected static boolean enchantsEqual(Map<Enchantment, Integer> a, Map<Enchantment, Integer> b) {
        
        // Enchants
        if( a.size() != b.size() ) return false;
        
        // Match enchantments and levels
        for( Entry<Enchantment,Integer> entryA : a.entrySet() ){
           
            // If enchantment not present
            if( !b.containsKey( entryA.getKey() ) ) return false;
            
            // If levels don't match
            if( !b.get( entryA.getKey() ).equals( entryA.getValue() ) ) return false;
            
        }
        
        return true;
        
    }
	
	
	/**
	 * @todo this is buggy, wth?
	 * @return
	 */
    public static String getUsedDurabilityPercentage(Item item) {

        int dura = item.getDamage();
        int max_dura = item.getMaxDurability();
		if(dura > 0 && max_dura > 0 && dura != max_dura){
			double diff = ((dura / max_dura)*100);
			if(diff > 0){
				return Math.floor(diff) + "%";
			}
		}

		return "";
	}
	
	
	/**
	 * Returns the durability remaining
	 * @return
	 */
    public static String getDurabilityPercentage(Item item) {

        int dura = item.getDamage();
        int max_dura = item.getMaxDurability();
		if(dura > 0 && max_dura > 0 && dura != max_dura){
			double diff = max_dura - dura;
			diff = ((diff / max_dura)*100);
			if(diff > 0){
				return Math.floor(diff) + "%";
			}
			return "0%";
		}
		
		return "";
	}
	
	
	/**
	 * Returns a proper full name for an item, which includes meta content as well.
	 * @return string
	 */
    public static String getItemFullNiceName(Item item) {

        //String item_name = "";
		/*TODO:
		// Leather Coloring
		if(item.getType().name().contains("LEATHER_")){
			LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
			if(lam.getColor() != null){
				item_name += "dyed ";
			}
		}
		
		// Skull Owner
		else if(item.getType().equals(Material.SKULL_ITEM)){
			SkullMeta skull = (SkullMeta) item.getItemMeta();
			if(skull.hasOwner()){
				item_name += skull.getOwner() + "'s ";
			}
		}
		
		// Set the base item name
		if(dataValueUsedForSubitems(item.getTypeId())){
			item_name += MaterialAliases.getInstance().getAlias(item.getTypeId(), item.getDurability());
		} else {
			item_name += MaterialAliases.getInstance().getAlias(item.getTypeId(), 0);
		}
		if(item_name.isEmpty()){
			item_name += item.getType().toString().toLowerCase().replace("_", " ");
		}
		
		// Anvils
		if( item.getTypeId() == 145 ){
			if( item.getDurability() == 1 ){
				item_name = "slightly damaged anvil";
			}
			else if( item.getDurability() == 2 ){
				item_name = "very damaged anvil";
			}
		}
		
		// Written books
		if(item.getType().equals( Material.WRITTEN_BOOK )){
	        BookMeta meta = (BookMeta) item.getItemMeta();
			if(meta != null){
				item_name += " '" + meta.getTitle() + "' by " + meta.getAuthor();
			}
		}
		
		// Enchanted books
		else if(item.getType().equals( Material.ENCHANTED_BOOK )){
			EnchantmentStorageMeta bookEnchantments = (EnchantmentStorageMeta) item.getItemMeta();
			if(bookEnchantments.hasStoredEnchants()){
				int i = 1;
				Map<Enchantment,Integer> enchs = bookEnchantments.getStoredEnchants();
				if(enchs.size() > 0){
					item_name += " with";
					for (Map.Entry<Enchantment, Integer> ench : enchs.entrySet()){
						item_name += " " + EnchantmentUtils.getClientSideEnchantmentName( ench.getKey(), ench.getValue() );
						item_name += (i < enchs.size() ? ", " : "");
						i++;
					}
				}
			}
		}
		
		// Enchantments
		int i = 1;
		Map<Enchantment,Integer> enchs = item.getEnchantments();
		if(enchs.size() > 0){
			item_name += " with";
			for (Map.Entry<Enchantment, Integer> ench : enchs.entrySet()){
				item_name += " " + EnchantmentUtils.getClientSideEnchantmentName( ench.getKey(), ench.getValue() );
				item_name += (i < enchs.size() ? ", " : "");
				i++;
			}
		}
		
		// Fireworks
		if( item.getTypeId() == 402 ){
			FireworkEffectMeta fireworkMeta = (FireworkEffectMeta) item.getItemMeta();
			if( fireworkMeta.hasEffect() ){
				FireworkEffect effect = fireworkMeta.getEffect();
				if( !effect.getColors().isEmpty() ){
					item_name += " " + effect.getColors().size() + " colors";
//					int[] effectColors = new int[ effect.getColors().size() ];
//					for (Color effectColor : effect.getColors()){
////						item_name += effectColor.
//					}
				}
				if( !effect.getFadeColors().isEmpty() ){
					item_name += " " + effect.getFadeColors().size() + " fade colors";
//					int[] fadeColors = new int[ effect.getColors().size() ];
//				    for (Color fadeColor : effect.getFadeColors()){
////				    	item_name += fadeColor.asRGB();
//				    };
				}
				if(effect.hasFlicker()){
					item_name += " flickering";
				}
				if(effect.hasTrail()){
					item_name += " with trail";
				}
			}
		}
		
		// Custom item names
		ItemMeta im = item.getItemMeta();
		if(im != null){
			String displayName = im.getDisplayName();
			if(displayName != null){
				item_name += ", named \"" + displayName + "\"";
			}
		}*/

        return item.getName();
		
	}
	
	
	/**
     * Returns true if an item uses its damage value for something
     * other than durability.
     *
     * @param id
     * @return
     */
    public static boolean dataValueUsedForSubitems( int id ){
    	return  id == 5         // planks
    	        || id == 17     // logs
    	        || id == 162    // logs 2
        		|| id == 18 	// leaves
        		|| id == 24     // sandstone
        		|| id == 31 	// tallgrass
                || id == 35 	// wool
                || id == 38     // flowers
                || id == 43 	// double slab
                || id == 44 	// slab
                || id == 95     // stained glass
                || id == 98 	// stonebrick
                || id == 139    // mossycobblewall
                || id == 155    // quartz
                || id == 159    // hard clay
                || id == 160    // stained glass pane
                || id == 171    // carpet
                || id == 175    // flower/bushes
                || id == 263 	// charcoal
                || id == 351    // dye
                || id == 322    // golden apple
                || id == 349    // fish
                || id == 125    // double wood slab
                || id == 126    // wood slab
                || id == 6		// saplings
                || id == 373    // potions
        		|| id == 383    // creature eggs
    			|| id == 397    // skulls
                || id == 1      // stone
                || id == 3      // dirt
                || id == 19     // sponge
                || id == 168;   // prismarine
    }
    
    
    /**
     * Determines if an itemstack can be stacked. Maz stack size, meta data,
     * and more taken into account.
     * @param item
     */
    public static boolean canSafelyStack(Item item) {
    	// Can't stack
    	if( item.getMaxStackSize() == 1 ){
    		return false;
    	}
    	// Has meta
        if (item.hasCustomName() || item.hasEnchantments() || item.getLore().length > 0) {
    		return false;
    	}
    	return true;
    }
    
    
    /**
     * Drop an item at a given location.
     *
     * @param location The location to drop the item at
     * @param itemStack The item to drop
     */
    public static void dropItem(Location location, Item itemStack) {
        location.getLevel().dropItem(location, itemStack);
    }
    
    
    /**
	 * Drop items at a given location.
	 *
	 * @param location The location to drop the items at
	 * @param is The items to drop
	 * @param quantity The amount of items to drop
	 */
    public static void dropItem(Location location, Item is, int quantity) {
        for (int i = 0; i < quantity; i++) {
            dropItem(location, is);
        }
    }
}