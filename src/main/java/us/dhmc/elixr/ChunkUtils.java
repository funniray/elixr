package us.dhmc.elixr;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.format.Chunk;
import cn.nukkit.math.Vector3;

import java.util.ArrayList;
import java.util.List;


public class ChunkUtils {
	

	/**
	 * Resets the preview border blocks
	 * @param player
	 * @param blocks
	 */
	public static void resetPreviewBoundaryBlocks(Player player, List<Block> blocks) {
		for (Block block : blocks){
			//TODO: player.sendBlockChange(block.getLocation(), block.getType(), block.getData());
		}
	}
	
	
	/**
	 * Sets a block in preview mode around the border of a chunk
	 * @param player
	 * @param blocks
	 */
	public static void setPreviewBoundaryBlocks(Player player, List<Block> blocks, int m) {
		for (Block block : blocks){
			//TODO: player.sendBlockChange(block.getLocation(), m, (byte)0);
		}
	}
	
	
	/**
	 * Returns the minimum vector for the chunk
	 * @param chunk
	 * @return
	 */
	public static Vector3 getChunkMinVector(Chunk chunk) {
		int blockMinX = chunk.getX() * 16;
		int blockMinZ = chunk.getZ() * 16;
		return new Vector3(blockMinX, 0, blockMinZ);
	}
	
	
	/**
	 * Returns the maximum vector for the chunk
	 * @param chunk
	 * @return
	 */
	public static Vector3 getChunkMaxVector(Chunk chunk) {
		int blockMinX = chunk.getX() * 16;
		int blockMinZ = chunk.getZ() * 16;
		int blockMaxX = blockMinX + 15;
		int blockMaxZ = blockMinZ + 15;
		return new Vector3(blockMaxX, 254, blockMaxZ);
	}

	
	/**
	 * Returns an array of boundary blocks at a single Y for the current chunk TODO
	 * @param chunk
	 * @return
	 */
	public static ArrayList<Block> getBoundingBlocksAtY( Chunk chunk, int y ){

		return null;
	    
	}
}