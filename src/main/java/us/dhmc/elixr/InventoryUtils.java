package us.dhmc.elixr;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;

import java.util.HashMap;
import java.util.Map.Entry;


/**
 * 
 * @author botskonet
 *
 */
public class InventoryUtils {
    
    /**
     * Easier to supress depracation errors
     * @param p
     */
    @SuppressWarnings("deprecation")
    public static void updateInventory( Player p ){
        //p.updateInventory();
    }
    
    /**
     * Does the player have anything in their inv?
     * @param p
     * @return
     */
    public static boolean playerInvIsEmpty( Player p ){
        for (Item item : p.getInventory().getContents().values()) {
            if( item != null ) return false;
        }
        return true;
    }
    
    /**
     * Does the player have any armor?
     * @param p
     * @return
     */
    public static boolean playerArmorIsEmpty( Player p ){
        for (Item item : p.getInventory().getArmorContents()) {
            if (item != null && item.getId() != 0) return false;
        }
        return true;
    }
	
	/**
	 * Returns the slot id of a specific item type, or -1 if none
	 * @param inv
	 * @param item_id
	 * @param sub_id
	 * @return
	 */
    public static int inventoryHasItem(Inventory inv, int item_id, int sub_id) {
		int currentSlot = 0;
        for (Item item : inv.getContents().values()) {
            if (item != null && item.getId() == item_id && item.getDamage() == sub_id) {
				return currentSlot;
			}
			currentSlot++;
		}
		return -1;
	}
	
	/**
     * 
     * @param player
     * @param desiredQuantity
     * @return
     */
    public static Item extractItemsMatchingHeldItemFromPlayer(Player player, int desiredQuantity) {

        if (player == null || !ItemUtils.isValidItem(player.getInventory().getItemInHand())) {
            throw new IllegalArgumentException("Invalid player or invalid held item.");
        }
        
        int quantityFound = 0;
        Item itemDefinition = player.getInventory().getItemInHand().clone();
        
        for( int slot = 0; slot < player.getInventory().getSize(); slot++ ){
            Item item = player.getInventory().getItem(slot);
            if( item == null ) continue;
            if( ItemUtils.equals( item, itemDefinition, true ) ){
                
                // check how many items we need
                int diff = desiredQuantity - quantityFound;

                // Consume whole stack
                if (diff > item.getCount()) {
                    quantityFound += item.getCount();
                    player.getInventory().clear(slot);
                }
                // Only need a portion
                else {
                   quantityFound += diff;
                    item.setCount(item.getCount() - diff);
                   player.getInventory().setItem( slot, item );
                }
            }
            if( desiredQuantity == quantityFound ) break;
        }

        itemDefinition.setCount(quantityFound);
        
        return itemDefinition;
        
    }
	
	/**
	 * Moves a specific item to the player's hand, returns false if the item doesn't exist in the inventory
	 * @param inv
	 * @param item_id
	 * @param sub_id
	 * @return
	 */
    public static boolean moveItemToHand(PlayerInventory inv, int item_id, byte sub_id) {
		int slot = inventoryHasItem( inv, item_id, sub_id );
		if( slot > -1 ){
            Item item = inv.getItem(slot);
			inv.clear(slot);
			// If the player has an item in-hand, switch to a vacant spot
			if( !playerHasEmptyHand(inv) ){
				inv.setItem(slot, inv.getItemInHand());
			}
			inv.setItemInHand(item);
			return true;
		}
		return false;
	}
	
	/**
	 * Whether or not the player has an empty hand
	 * @param inv
	 * @return
	 */
	public static boolean playerHasEmptyHand( PlayerInventory inv ){
        return (inv.getItemInHand().getId() == 0);
    }

    public static Item[] addItemToInventory(Inventory inv, Item item) {
		return inv.addItem(item);
	}

    public static boolean handItemToPlayer(PlayerInventory inv, Item item) {
		// Ensure there's at least one empty inv spot
        if (inv.firstEmpty(new Item(0)) != -1) {
            Item originalItem = inv.getItemInHand().clone();
			// If the player has an item in-hand, switch to a vacant spot
			if( !playerHasEmptyHand( inv ) ){
				// We need to manually add the item stack to a different
				// slot because by default, bukkit combines items with addItem
				// and that was causing items to be lost unless they were the max
				// stack size
				for(int i = 0; i <= inv.getSize(); i++){
                    if (i == inv.getHeldItemIndex()) continue;
                    Item current = inv.getItem(i);
					if( current == null ){
						inv.setItem(i, originalItem);
						break;
					}
				}
			}
			inv.setItemInHand(item);
			return true;
		}
		return false;
	}
	
	/**
	 * Subtract a specific quantity from an inventory slots item stack.
	 * @param inv
	 * @param slot
	 * @param quant
	 */
	public static void subtractAmountFromPlayerInvSlot( PlayerInventory inv, int slot, int quant ){
        Item itemAtSlot = inv.getItem(slot);
		if( itemAtSlot != null && quant <= 64 ){
            itemAtSlot.setCount(itemAtSlot.getCount() - quant);
            if (itemAtSlot.getCount() == 0) {
				inv.clear(slot);
			}
		}
	}
	
	/**
	 * Drop items at player's location.
	 * @param leftovers
	 * @param player
	 */
    public static void dropItemsByPlayer(HashMap<Integer, Item> leftovers, Player player) {
		if(!leftovers.isEmpty()){
            for (Entry<Integer, Item> entry : leftovers.entrySet()) {
                player.getLevel().dropItem(player.getLocation(), entry.getValue());
			}
		}
	}
	
	/**
	 * Is an inventory fully empty
	 * @param in
	 * @return
	 */
	public static boolean isEmpty(Inventory in) {
		boolean ret = false;
		if (in == null) {
			return true;
		}
        for (Item item : in.getContents().values()) {
			ret |= (item != null);
		}
		return !ret;
	}
    //TODO:
	/*
	public static void movePlayerInventoryToContainer(PlayerInventory inv, Block target, HashMap<Integer,Short> filters ) throws Exception{
		InventoryHolder container = (InventoryHolder) target.getState();
		if( !moveInventoryToInventory( inv, container.getInventory(), false, filters ) ){
			throw new Exception("Target container is full.");
		}
	}
	public static void moveContainerInventoryToPlayer( PlayerInventory inv, Block target, HashMap<Integer,Short> filters ) throws Exception{
		InventoryHolder container = (InventoryHolder) target.getState();
		moveInventoryToInventory( container.getInventory(), inv, false, filters );
	}

	public static boolean moveInventoryToInventory( Inventory from, Inventory to, boolean fullFlag, HashMap<Integer,Short> filters ) {

		HashMap<Integer, Item> leftovers;

		if (to.firstEmpty() != -1 && !fullFlag){
			for (Item item : from.getContents()) {
				if(to.firstEmpty() == -1){
					return false;
				}
				if (item != null && to.firstEmpty() != -1) {
					
					boolean shouldTransfer = false;
					if( filters.size() > 0 ){
		                for( Entry<Integer,Short> entry : filters.entrySet() ){
		                    if( entry.getKey() == item.getTypeId() && entry.getValue() == item.getDurability() ){
		                        shouldTransfer = true;
		                    }
		                }
		            } else {
		                shouldTransfer = true;
		            }
					
					if( !shouldTransfer ) continue;

					leftovers = to.addItem(item);
					if (leftovers.size() == 0) {
						from.removeItem(item);
					} else {
						from.removeItem(item);
						from.addItem(leftovers.get(0));
					}
					
				}
			}
			return true;
		}
		return false;
	}
	*/
	/**
	 * 
	 * @param stack
	 * @param player
	 * @return
	 */
    public static Item[] sortItem(Item[] stack, Player player) {
        return sortItem(stack, 0, stack.length, player);
    }

    /**
     * 
     * @param stack
     * @param start
     * @param end
     * @param player
     * @return
     */
    public static Item[] sortItem(Item[] stack, int start, int end, Player player) {
        stack = stackItems(stack, start, end);
        recQuickSort(stack, start, end - 1);
        return stack;
    }
    
    /**
     * 
     * @param items
     * @param start
     * @param end
     * @return
     */
    private static Item[] stackItems(Item[] items, int start, int end) {
        for (int i = start; i < end; i++) {
            Item item = items[i];

            // Avoid infinite stacks and stacks with durability
            if (item == null || item.getCount() <= 0 || !ItemUtils.canSafelyStack(item)) {
                continue;
            }

            int max_stack = item.getMaxStackSize();
            if (item.getCount() < max_stack) {
                int needed = max_stack - item.getCount(); // Number of needed items until max_stack

                // Find another stack of the same type
                for (int j = i + 1; j < end; j++) {
                    Item item2 = items[j];

                    // Avoid infinite stacks and stacks with durability
                    if (item2 == null || item2.getCount() <= 0 || !ItemUtils.canSafelyStack(item)) {
                        continue;
                    }

                    // Same type?
                    // Blocks store their color in the damage value
                    if (item2.getId() == item.getId() && (!ItemUtils.dataValueUsedForSubitems(item.getId()) || item.getDamage() == item2.getDamage())) {
                        // This stack won't fit in the parent stack
                        if (item2.getCount() > needed) {
                            item.setCount(max_stack);
                            item2.setCount(item2.getCount() - needed);
                            break;
                        } else {
                            item.setCount(item.getCount() + item2.getCount());
                            needed = max_stack - item.getCount();
                            items[j].setCount(0);
                        }
                    }
                }
            }
        }
        return items;
    }
    
    /**
     * 
     * @param list
     * @param first
     * @param second
     */
    private static void swap(Item[] list, int first, int second) {
        Item temp;
        temp = list[first];
        list[first] = list[second];
        list[second] = temp;
    }
    
    /**
     * 
     * @param list
     * @param first
     * @param last
     * @return
     */
    private static int partition(Item[] list, int first, int last) {
        Item pivot;

        int smallIndex;

        swap(list, first, (first + last) / 2);

        pivot = list[first];
        smallIndex = first;

        for (int index = first + 1; index <= last; index++) {

            Item item = list[index];

            if( ItemUtils.equals( item, pivot ) ){
                smallIndex++;
                swap(list, smallIndex, index);
            }
        }

        swap(list, first, smallIndex);
        return smallIndex;

    }
    
    /**
     * 
     * @param list
     * @param first
     * @param last
     */
    private static void recQuickSort(Item[] list, int first, int last) {
        if (first < last) {
            int pivotLocation = partition(list, first, last);
            recQuickSort(list, first, pivotLocation - 1);
            recQuickSort(list, pivotLocation + 1, last);
        }
    }
}