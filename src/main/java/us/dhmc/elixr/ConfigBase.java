package us.dhmc.elixr;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ConfigBase {
	
	/**
	 * 
	 */
	protected Plugin plugin;
	
	/**
	 * 
	 */
	protected Config config;
	
	/**
	 * 
	 * @param plugin
	 */
	public ConfigBase( Plugin plugin ) {
		this.plugin = plugin;
	}

	public Config getConfig() {
		config = plugin.getConfig();
		return config;
	}
	
	/**
	 * Returns base directory for config
	 * @return
	 */
	protected File getDirectory(){
		File dir = new File(plugin.getDataFolder()+"");
		return dir;
	}
	
	/**
	 * Returns chosen filename with directory
	 * @return
	 */
	protected File getFilename( String filename ){
		File file = new File(getDirectory(), filename + ".yml");
		return file;
	}

	protected Config loadConfig(String default_folder, String filename) {
		File file = getFilename( filename );
		if(file.exists()){
			return new Config(file);
		} else {
			// Look for defaults in the jar
			URL defConfigStream = plugin.getClass().getResource(default_folder + filename + ".yml");
		    if (defConfigStream != null){
				try {
					return new Config(new File(defConfigStream.toURI()));
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		    return null;
		}
	}
	
	/**
	 * 
	 * @param config
	 */
	protected void saveConfig(String filename, Config config) {
		File file = getFilename( filename );
		config.save(file);
	}
	
	/**
	 * 
	 */
	protected void write(String filename, Config config) {
		try {
			BufferedWriter bw = new BufferedWriter( new FileWriter( getFilename( filename ), true ) );
			saveConfig( filename, config );
			bw.flush();
			bw.close();
		} catch (IOException e){

        }
	}
}