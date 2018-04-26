package us.dhmc.elixr;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Location;
import cn.nukkit.math.BlockFace;

import java.util.ArrayList;
import java.util.Collection;


public class BlockUtils {

	/**
	 * Determines if the material of an existing block at a location is
	 * something that's commonly acceptable to replace.
	 * 
	 * @param m the material of the block
	 * @return if the material is acceptable to replace
	 */
    public static boolean isAcceptableForBlockPlace(int m) {
		switch(m){
            case BlockID.AIR:
            case BlockID.FIRE:
            case BlockID.GRAVEL:
            case BlockID.LAVA:
            case BlockID.TALL_GRASS:
            case BlockID.SAND:
            case BlockID.SNOW:
            case BlockID.SNOW_BLOCK:
            case BlockID.STILL_LAVA:
            case BlockID.STILL_WATER:
            case BlockID.WATER:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Recursively grabs a list of all blocks directly above Block
	 * that are anticipated to fall.
	 * 
	 * @param block the block to fetch blocks above
	 * @return the list of blocks directly above the block
	 */
    public static ArrayList<Block> findFallingBlocksAboveBlock(final Block block) {
		ArrayList<Block> falling_blocks = new ArrayList<Block>();
		
		// Get block above
        Block above = block.getSide(BlockFace.UP);
		if(BlockUtils.isFallingBlock(above)){
			falling_blocks.add(above);
			ArrayList<Block> fallingBlocksAbove = findFallingBlocksAboveBlock( above );
			if(fallingBlocksAbove.size() > 0){
				for(Block _temp : fallingBlocksAbove){
					falling_blocks.add(_temp);
				}
			}
		}
		return falling_blocks;
	}
	
	/**
	 * Determine whether or not a block is capable of falling.
	 * 
	 * Seems like there's got to be another way to do this...
	 * @param block the block to check for the ability to fall
	 * @return whether the block is capable of falling
	 */
	public static boolean isFallingBlock( Block block ){
        int m = block.getId();

        switch (m){
            case BlockID.SAND:
            case BlockID.GRAVEL:
            case BlockID.ANVIL:
                return true;
            default:
                return false;
        }
	}
	
	/**
	 * Searches for detachable blocks on the four acceptable sides of a block.
	 * 
	 * @param block the block to check the sides of
	 * @return the list of detachable block on the sides of the block
	 */
	public static ArrayList<Block> findSideFaceAttachedBlocks( final Block block ){
		
		ArrayList<Block> detaching_blocks = new ArrayList<Block>();
		
		// Check each of the four sides
        Block blockToCheck = block.getSide(BlockFace.EAST);
        if (BlockUtils.isSideFaceDetachableMaterial(blockToCheck.getId())) {
			detaching_blocks.add(blockToCheck);
		}
        blockToCheck = block.getSide(BlockFace.WEST);
        if (BlockUtils.isSideFaceDetachableMaterial(blockToCheck.getId())) {
			detaching_blocks.add(blockToCheck);
		}
        blockToCheck = block.getSide(BlockFace.NORTH);
        if (BlockUtils.isSideFaceDetachableMaterial(blockToCheck.getId())) {
			detaching_blocks.add(blockToCheck);
		}
        blockToCheck = block.getSide(BlockFace.SOUTH);
        if (BlockUtils.isSideFaceDetachableMaterial(blockToCheck.getId())) {
			detaching_blocks.add(blockToCheck);
		}
		
		return detaching_blocks;
		
	}
	
	/**
	 * Searches around a block for the first block of the given material
     *
	 * @param block the block to search around
	 * @param m the material of the surrounding block to look for
	 * @return the first surrounding block of the given material found
	 */
    public static Block findFirstSurroundingBlockOfType(Block block, int m) {
        Block blockToCheck = block.getSide(BlockFace.EAST);
        if (blockToCheck.getId() == m) {
			return blockToCheck;
		}
        blockToCheck = block.getSide(BlockFace.WEST);
        if (blockToCheck.getId() == m) {
			return blockToCheck;
		}
        blockToCheck = block.getSide(BlockFace.NORTH);
        if (blockToCheck.getId() == m) {
			return blockToCheck;
		}
        blockToCheck = block.getSide(BlockFace.SOUTH);
        if (blockToCheck.getId() == m) {
			return blockToCheck;
		}
		return null;
	}
	
	/**
	 * Determine whether or not a block using the given material is going to detach
	 * from the side of a block.
	 *
	 * @param m the material to check for detaching
	 * @return whether a block with a given material will detach from the side of a block
	 */
    public static boolean isSideFaceDetachableMaterial(int m) {
        switch (m){
            case BlockID.COCOA:
            case BlockID.WALL_SIGN:
            case BlockID.LADDER:
            case BlockID.LEVER:
            case BlockID.IRON_TRAPDOOR:
            case BlockID.NETHER_PORTAL:
            case BlockID.PISTON: // Fake entry, the base always breaks if the extension is lost
            case BlockID.PISTON_EXTENSION:
            case BlockID.PISTON_HEAD:
            case BlockID.STICKY_PISTON:
            case BlockID.REDSTONE_TORCH:
            case BlockID.UNLIT_REDSTONE_TORCH:
            case BlockID.STONE_BUTTON:
            case BlockID.TRAPDOOR:
            case BlockID.TORCH:
            case BlockID.TRIPWIRE_HOOK:
            case BlockID.WOODEN_BUTTON:
            case BlockID.VINE:
                return true;
            default:
                return false;
        }
	}
	
	/**
	 * Searches for detachable blocks on the four acceptable sides of a block.
	 * 
	 * @param block
	 * @return
	 */
	public static ArrayList<Block> findTopFaceAttachedBlocks( final Block block ){
		ArrayList<Block> detaching_blocks = new ArrayList<Block>();
		
		// Find any block on top of this that will detach
        Block blockToCheck = block.getSide(BlockFace.UP);
        if (BlockUtils.isTopFaceDetachableMaterial(blockToCheck.getId())) {
			detaching_blocks.add(blockToCheck);
            if (blockToCheck.getId() == BlockID.CACTUS || blockToCheck.getId() == BlockID.SUGARCANE_BLOCK) {
				// For cactus and sugar cane, we can even have blocks above
				ArrayList<Block> additionalBlocks = findTopFaceAttachedBlocks(blockToCheck);
				if(!additionalBlocks.isEmpty()){
					for(Block _temp : additionalBlocks){
						detaching_blocks.add(_temp);
					}
				}
			}
		}
		
		return detaching_blocks;
		
	}
	
	
	/**
	 * Determine whether or not a block is going to detach
	 * from the top of a block.
     *
	 * @param m the material to check for detaching
	 * @return whether a block with a given material will detach from the top of a block
	 */
    public static boolean isTopFaceDetachableMaterial(int m) {
		switch(m){
            case BlockID.ACTIVATOR_RAIL:
            case BlockID.BROWN_MUSHROOM:
            case BlockID.CACTUS:
            case BlockID.CARROT_BLOCK:
            case BlockID.DEAD_BUSH:
            case BlockID.DETECTOR_RAIL:
            case BlockID.DOUBLE_PLANT:
            case BlockID.FLOWER_POT_BLOCK:
            case BlockID.HEAVY_WEIGHTED_PRESSURE_PLATE:
            case BlockID.IRON_TRAPDOOR:
            case BlockID.IRON_DOOR_BLOCK:
            case BlockID.LIGHT_WEIGHTED_PRESSURE_PLATE:
            case BlockID.LEVER:
            case BlockID.TALL_GRASS:
            case BlockID.MELON_STEM:
            case BlockID.NETHER_WART_BLOCK:
            case BlockID.POTATO_BLOCK:
            case BlockID.POWERED_RAIL:
            case BlockID.PUMPKIN_STEM:
            case BlockID.RAIL:
            case BlockID.RED_MUSHROOM:
            case BlockID.ROSE:
            case ItemID.REDSTONE:
            case BlockID.POWERED_COMPARATOR:
            case BlockID.UNPOWERED_COMPARATOR:
            case BlockID.REDSTONE_TORCH:
            case BlockID.UNLIT_REDSTONE_TORCH:
            case BlockID.REDSTONE_WIRE:
            case BlockID.SAPLING:
            case BlockID.WALL_SIGN:
            case BlockID.SIGN_POST:
            case BlockID.SKULL_BLOCK:
            case BlockID.SNOW:
            case BlockID.STONE_PRESSURE_PLATE:
            case BlockID.SUGARCANE_BLOCK:
            case BlockID.TORCH:
            case BlockID.TRIPWIRE:
            case BlockID.WATER_LILY:
            case BlockID.WHEAT_BLOCK:
            case BlockID.WOOD_DOOR_BLOCK:
            case BlockID.WOODEN_PRESSURE_PLATE:
            case BlockID.DANDELION:
				return true;
			default:
				return false;
		}
	}
	
	
	/**
	 * Determine whether or not a block location is filled
	 * by a material that means an attachable material
	 * is now detached.
	 * 
	 * @param m
	 * @return
	 */
    public static boolean materialMeansBlockDetachment(int m) {
		switch(m){
            case 0:
            case BlockID.FIRE:
            case BlockID.WATER:
            case BlockID.STILL_WATER:
            case BlockID.LAVA:
            case BlockID.STILL_LAVA:
				return true;
			default:
				return false;
		}
	}
	
	
	/**
	 * Searches for detachable entities in a
	 * 
	 * @param block
	 * @return
	 */
    public static ArrayList<Entity> findHangingEntities(final Block block) {
		
		ArrayList<Entity> entities = new ArrayList<Entity>();


        Collection<Entity> foundEntities = block.getLevel().getChunk(block.getChunkX(), block.getChunkZ()).getEntities().values();
        if (foundEntities.size() > 0) {
			for(Entity e : foundEntities){
				// Some modded servers seems to list entities in the chunk
				// that exists in other worlds. No idea why but we can at
				// least check for it.
				// https://snowy-evening.com/botsko/prism/318/
                if (!block.getLevel().equals(e.getLevel())) continue;
				// Let's limit this to only entities within 1 block of the current.
				if( block.getLocation().distance( e.getLocation() ) < 2 && isHangingEntity(e) ){
					entities.add(e);
				}
			}
		}
		
		return entities;
		
	}
	
	
	/**
	 * Is an entity a hanging type, attachable to a block.
	 * @param entity the entity to check for being a hanging type
	 * @return if an entity is a hanging type attachable to a block
	 */
	public static boolean isHangingEntity( Entity entity ){
        int type = (int) entity.getId();

        switch (type) {
            case ItemID.ITEM_FRAME:
            case ItemID.PAINTING:
                return true;
            default:
                return false;
        }
	}
	
	
	/**
	 * Gets the other block that is part of a double length block
     *
	 * @param block the block to get the sibling of
	 */
	public static Block getSiblingForDoubleLengthBlock(Block block){
		/**
		 * Handle special double-length blocks
		 */

        //I don't think this is used in Nukkit

        return null;
	}
	
	
//	/**
//	 * 
//	 * @param mat
//	 * @param loc
//	 * @param radius
//	 */
//	public static ArrayList<BlockStateChange> removeMaterialFromRadius(Material mat, Location loc, int radius){
//		Material[] materials = { mat };
//		return removeMaterialsFromRadius(materials, loc, radius);
//	}
//	
//	
//	/**
//	 * 
//	 * @param mat
//	 * @param loc
//	 * @param radius
//	 */
//	public static ArrayList<BlockStateChange> removeMaterialsFromRadius(Material[] materials, Location loc, int radius){
//		ArrayList<BlockStateChange> blockStateChanges = new ArrayList<BlockStateChange>();
//		if(loc != null && radius > 0 && materials != null && materials.length > 0){
//			int x1 = loc.getBlockX();
//			int y1 = loc.getBlockY();
//			int z1 = loc.getBlockZ();
//			World world = loc.getWorld();
//			for(int x = x1-radius; x <= x1+radius; x++){
//				for(int y = y1-radius; y <= y1+radius; y++){
//					for(int z = z1-radius; z <= z1+radius; z++){
//						loc = new Location(world, x, y, z);
//						Block b = loc.getBlock();
//						if(b.getType().equals(Material.AIR)) continue;
//						if( Arrays.asList(materials).contains(loc.getBlock().getType()) ){
//							BlockState originalBlock = loc.getBlock().getState();
//							loc.getBlock().setType(Material.AIR);
//							BlockState newBlock = loc.getBlock().getState();
//							blockStateChanges.add(new BlockStateChange(originalBlock,newBlock));
//						}
//					}
//				}
//			}
//		}
//		return blockStateChanges;
//	}
//	
//	
//	/**
//	 * Extinguish all the fire in a radius
//	 * @param loc The location you want to extinguish around
//	 * @param radius The radius around the location you are extinguish
//	 */
//	public static ArrayList<BlockStateChange> extinguish(Location loc, int radius){
//		return removeMaterialFromRadius(Material.FIRE, loc, radius);
//	}
//	
//	
//	/**
//	 * Drains lava and water within (radius) around (loc).
//	 * @param loc
//	 * @param radius
//	 */
//	public static ArrayList<BlockStateChange> drain(Location loc, int radius){
//		Material[] materials = { Material.LAVA, Material.STATIONARY_LAVA, Material.WATER, Material.STATIONARY_WATER };
//		return removeMaterialsFromRadius(materials, loc, radius);
//	}
//	
//	
//	/**
//	 * Drains lava blocks (radius) around player's loc.
//	 * @param loc
//	 * @param radius
//	 */
//	public static ArrayList<BlockStateChange> drainlava(Location loc, int radius){
//		Material[] materials = { Material.LAVA, Material.STATIONARY_LAVA };
//		return removeMaterialsFromRadius(materials, loc, radius);
//	}
//	
//	
//	/**
//	 * Drains water blocks (radius) around player's loc.
//	 * @param loc
//	 * @param radius
//	 */
//	public static ArrayList<BlockStateChange> drainwater(Location loc, int radius){
//		Material[] materials = { Material.WATER, Material.STATIONARY_WATER };
//		return removeMaterialsFromRadius(materials, loc, radius);
//	}
	
	
	/**
	 * Lower door halves get byte values based on which direction the front
	 * of the door is facing.
	 * 
	 * 0 = West
	 * 1 = North
	 * 2 = East
	 * 3 = South
	 * 
	 * The upper halves of both door types always have a value of 8.
	 * 
	 * @param originalBlock
	 * @param typeid
	 * @param subid
	 */
	public static void properlySetDoor( Block originalBlock, int typeid, byte subid ){
        //Not used in Nukkit
	}
	
	
	/**
	 * Checks if material given is the material of a door
     *
	 * @param m the material to check for being a door material
	 * @return whether the material is a door material
	 */
    public static boolean isDoor(int m) {
		switch(m){
            case BlockID.ACACIA_DOOR_BLOCK:
            case BlockID.BIRCH_DOOR_BLOCK:
            case BlockID.DARK_OAK_DOOR_BLOCK:
            case BlockID.JUNGLE_DOOR_BLOCK:
            case BlockID.IRON_DOOR_BLOCK:
            case BlockID.SPRUCE_DOOR_BLOCK:
            case BlockID.WOOD_DOOR_BLOCK:
				return true;
			default:
				return false;
		}
	}
	
	
	/**
	 * Given the lower block of a bed, we translate that to the top
	 * half, figuring out which direction and data value it gets.
	 * 
	 * @param originalBlock
	 * @param typeid
	 * @param subid
	 */
	public static void properlySetBed( Block originalBlock, int typeid, byte subid ){
		Block top = null;
		int new_subid = 0;
		switch(subid){
			case 3:
                top = originalBlock.getSide(BlockFace.EAST);
				new_subid = 11;
				break;
			case 2:
                top = originalBlock.getSide(BlockFace.NORTH);
				new_subid = 10;
				break;
			case 1:
                top = originalBlock.getSide(BlockFace.WEST);
				new_subid = 9;
				break;
			case 0:
                top = originalBlock.getSide(BlockFace.SOUTH);
				new_subid = 8;
				break;
		}
		if(top != null){
            //top.setTypeId(typeid); TODO:
            //top.setData((byte)new_subid);
		} else {
			System.out.println("Error setting bed: block top location was illegal. Data value: " + subid + " New data value: " + new_subid);
		}
	}
	
	
	/**
	 * Properly sets the second-tier of a double-block tall plant.
	 * 
	 * @param originalBlock
	 * @param typeid
	 * @param subid
	 */
	public static void properlySetDoublePlant( Block originalBlock, int typeid, byte subid ){
        if (originalBlock.getId() != BlockID.DOUBLE_PLANT) return;
        Block above = originalBlock.getSide(BlockFace.UP);
        if (!isAcceptableForBlockPlace(above.getId())) return;
		// choose an acceptable subid
		if( typeid == 175 && subid < 8 ) subid = 8;
        //above.setTypeId(typeid); TODO
        //above.setData((byte)subid);
	}
	
	
	/**
	 * 
	 * @param m
	 * @return
	 */
    public static boolean canFlowBreakMaterial(int m) {
		switch(m){
            case BlockID.ACTIVATOR_RAIL:
            case BlockID.BROWN_MUSHROOM:
            case BlockID.CACTUS:
            case BlockID.CARROT_BLOCK:
            case BlockID.COCOA: // different from pop off list
            case BlockID.DEAD_BUSH:
            case BlockID.DETECTOR_RAIL:
            case BlockID.DOUBLE_PLANT:
            case BlockID.POTATO_BLOCK:
            case BlockID.FLOWER_POT_BLOCK:
            case BlockID.IRON_DOOR_BLOCK:
            case BlockID.LADDER: // different from pop off list
            case BlockID.LEVER:
            case BlockID.TALL_GRASS:
            case BlockID.MELON_STEM:
            case BlockID.NETHER_WART_BLOCK:
            case BlockID.POWERED_RAIL:
            case BlockID.PUMPKIN_STEM:
            case BlockID.RAIL:
            case BlockID.RED_MUSHROOM:
            case BlockID.ROSE:
            case BlockID.POWERED_COMPARATOR:
            case BlockID.UNPOWERED_COMPARATOR:
            case BlockID.REDSTONE_TORCH:
            case BlockID.UNLIT_REDSTONE_TORCH:
            case BlockID.REDSTONE_WIRE:
            case BlockID.SAPLING:
            case BlockID.WALL_SIGN:
            case BlockID.SIGN_POST:
            case BlockID.SKULL_BLOCK:
            case BlockID.SUGARCANE_BLOCK:
            case BlockID.STONE_PRESSURE_PLATE:
            case BlockID.TORCH:
            case BlockID.TRIPWIRE:
            case BlockID.TRIPWIRE_HOOK: // different from pop off list
            case BlockID.VINE: // different from pop off list
            case BlockID.WATER_LILY:
            case BlockID.WHEAT_BLOCK:
            case BlockID.WOOD_DOOR_BLOCK:
            case BlockID.WOODEN_PRESSURE_PLATE:
            case BlockID.DANDELION:
				return true;
			default:
				return false;
		}
	}
	
	
	/**
	 * 
	 * @param m
	 * @return
	 */
    public static boolean materialRequiresSoil(int m) {
		switch(m){
            case BlockID.WHEAT_BLOCK:
            case BlockID.POTATO_BLOCK:
            case BlockID.CARROT_BLOCK:
            case BlockID.MELON_STEM:
            case BlockID.PUMPKIN_STEM:
				return true;
			default:
				return false;
		}
	}


    public static ArrayList<Block> findConnectedBlocksOfType(int type, Block currBlock, ArrayList<Location> foundLocations) {
    	
    	ArrayList<Block> foundBlocks = new ArrayList<Block>();
    	
    	if(foundLocations == null){
    		foundLocations = new ArrayList<Location>();
    	}
        	
    	foundLocations.add(currBlock.getLocation());

        for (BlockFace face : BlockFace.values()) {
            Block newblock = currBlock.getSide(face);
            // ensure it matches the type and wasn't already found
            if (newblock.getId() == type && !foundLocations.contains(newblock.getLocation())) {
                foundBlocks.add(newblock);
                ArrayList<Block> additionalBlocks = findConnectedBlocksOfType(type, newblock, foundLocations);
                if (additionalBlocks.size() > 0) {
                    foundBlocks.addAll(additionalBlocks);
                }
    		}
    	}

        return foundBlocks;
        
    }
    
    
    /**
     * 
     * @param m
     * @param loc
     * @return
     */
    public static Block getFirstBlockOfMaterialBelow(int m, Location loc) {
    	for(int y = (int) loc.getY(); y > 0; y--){
            loc.y = y;
            if (loc.getLevel().getBlock(loc).getId() == m) {
                return loc.getLevel().getBlock(loc);
    		}
    	}
    	return null;
    }
    
    
    /**
     * 
     * @param m
     * @return
     */
    public static boolean isGrowableStructure(int m) {
		switch(m){
            case BlockID.LEAVES:
            case BlockID.LOG:
            case BlockID.BROWN_MUSHROOM_BLOCK:
            case BlockID.RED_MUSHROOM_BLOCK:
				return true;
			default:
				return false;
		}
	}
    
    
    /**
     * There are several items that are officially different
     * ItemStacks, but for the purposes of what we're doing
     * are really considered one core item. This attempts
     * to be a little lenient on matching the ids.
     * 
     * Example: Redstone lamp (off) is 123, (on) is 124 but 
     * either id means it's a redstone lamp.
     * 
     * @param id1
     * @param id2
     * @return
     */
    public static boolean areBlockIdsSameCoreItem( int id1, int id2 ){
    	
    	// Get the obvious one out of the way.
    	if(id1 == id2) return true;
    	
    	// Grass/Dirt
    	if( (id1 == 2 || id1 == 3) && (id2 == 2 || id2 == 3) ){
    		return true;
    	}
    	
    	// Mycel/Dirt
    	if( (id1 == 110 || id1 == 3) && (id2 == 110 || id2 == 3) ){
    		return true;
    	}
    	
    	// Water
    	if( (id1 == 8 || id1 == 9) && (id2 == 8 || id2 == 9) ){
    		return true;
    	}
    	
    	// Lava
    	if( (id1 == 10 || id1 == 11) && (id2 == 10 || id2 == 11) ){
    		return true;
    	}
    	
    	// Redstone torch
    	if( (id1 == 75 || id1 == 76) && (id2 == 75 || id2 == 76) ){
    		return true;
    	}
    	
    	// Repeater
    	if( (id1 == 93 || id1 == 94) && (id2 == 93 || id2 == 94) ){
    		return true;
    	}
    	
    	// Redstone lamp
    	if( (id1 == 123 || id1 == 124) && (id2 == 123 || id2 == 124) ){
    		return true;
    	}
    	
    	// Furnace
    	if( (id1 == 61 || id1 == 62) && (id2 == 61 || id2 == 62) ){
    		return true;
    	}
    	
    	// Redstone comparator
    	if( (id1 == 149 || id1 == 150) && (id2 == 149 || id2 == 150) ){
    		return true;
    	}
    	
    	return false;
    	
    }
}
