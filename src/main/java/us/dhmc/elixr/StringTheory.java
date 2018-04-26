package us.dhmc.elixr;


import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.Map;

import static cn.nukkit.utils.TextFormat.WHITE;

public class StringTheory {
    
    /**
     *
     */
    public StringTheory(){

        // Add a default message formatter
        baseline(":msg", new StringBaseline(WHITE){
            @Override
            public void format(String fancy) {

            }
        });
    }

    /**
     * Sends the final message
     *
     * @param sender
     * @param content
     */
    public void send(CommandSender sender, String content) {
//        System.out.println("raw json: " + msg.toJSONString());
//        System.out.println("raw text: " + msg.toOldMessageFormat());
        sender.sendMessage(content);
        tokens.clear();
    }

    private Map<String, String> tokens = new HashMap<String, String>();
    private Map<String, TokenFilter> filters = new HashMap<String, TokenFilter>();
    private Map<String, StringBaseline> baselines = new HashMap<String, StringBaseline>();

    /**
     * Return an original sendMessage-compatible string.
     *
     * @param content
     * @return
     */
    protected String text(String content) {
        return content;
    }
    
    
    /**
     * Define a baseline message format, the color will be re-used
     * after each token insertion to maintain consistency.
     * @param key
     * @param value
     */
    public void baseline( String key, StringBaseline value ){
        baselines.put( key, value );
    }
    
    /**
     * Define a token filter/formatter. 
     * @param key
     * @param value
     */
    public void filter( String key, TokenFilter value ){
        filters.put( key, value );
    }
    
    /**
     * Assign a value to a token
     * @param key
     * @param value
     * @return
     */
    public StringTheory know( String key, String value ){
        tokens.put( key, value );
        return this;
    }
    public StringTheory know( String key, Float value ){
        tokens.put( key, ""+value );
        return this;
    }
    public StringTheory know( String key, int value ){
        tokens.put( key, ""+value );
        return this;
    }
    
    /**
     * Parse a string, using baselines, filters, and tokens
     * @param content
     * @return
     */
    public String parse(String content) {
        return content;
    }

    public static interface TokenFilter {
        public void format(String fancy, String tokenVal);
    }

    public static class StringBaseline {
        private final TextFormat baseColor;

        public StringBaseline(TextFormat baseColor) {
            this.baseColor = baseColor;
        }

        public void format(String fancy) {
        }

        public TextFormat getColor() {
            return baseColor;
        }
    }
}