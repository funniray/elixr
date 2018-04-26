package us.dhmc.elixr;


import cn.nukkit.utils.TextFormat;

public class Messenger {

	protected final String plugin_name;
	
	/**
	 * 
	 * @param plugin_name
	 */
	public Messenger( String plugin_name ){
		this.plugin_name = plugin_name;
	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public String playerHeaderMsg(String msg){
		if(msg != null){
            return TextFormat.GOLD + "[" + plugin_name + "] " + TextFormat.WHITE + msg;
		}
		return "";
	}
	
	/**
     * 
     * @param msg
     * @return
     */
    public String playerSuccess(String msg){
        if(msg != null){
            return TextFormat.GOLD + "[" + plugin_name + "] " + TextFormat.GREEN + msg;
        }
        return "";
    }
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public String playerSubduedHeaderMsg(String msg){
		if(msg != null){
            return TextFormat.GOLD + "[" + plugin_name + "] " + TextFormat.GRAY + msg;
		}
		return "";
	}

	/**
	 * 
	 * @param msg
	 * @return
	 */
	public String playerMsg(String msg){
		if(msg != null){
            return TextFormat.WHITE + msg;
		}
		return "";
	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public String playerSubduedMsg(String msg){
		if(msg != null){
            return TextFormat.GRAY + msg;
		}
		return "";
	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public String[] playerMsg(String[] msg){
		if(msg != null){
			for(int i = 0; i < msg.length; i++){
				msg[i] = playerMsg(msg[i]);
			}
		}
		return msg;
	}

    public String playerHelp( String cmd, String help ){
        return TextFormat.GRAY + "/" + cmd + TextFormat.WHITE + " - " + help;
    }
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public String playerError(String msg){
		if(msg != null){
            return TextFormat.GOLD + "[" + plugin_name + "] " + TextFormat.RED + msg;
		}
		return "";
	}
}