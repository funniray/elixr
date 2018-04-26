package us.dhmc.elixr;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.potion.Potion;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author https://gist.github.com/PaulBGD/9831d28b1c7bdba0cddd
 */
public class ItemBuilder {

    private int ID;
    private int amount;
    private final short data;

    private String title = null;
    private final List<String> lore = new ArrayList<String>();
    private final HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
    private TextFormat color;
    private Potion potion;

    public ItemBuilder(int ID) {
        this(ID, 1);
    }

    public ItemBuilder(int ID, int amount) {
        this(ID, amount, (short) 0);
    }

    public ItemBuilder(int ID, short data) {
        this(ID, 1, data);
    }

    public ItemBuilder(int ID, int amount, short data) {
        this.ID = ID;
       this.amount = amount;
       this.data = data;
    }

    public ItemBuilder setColor(TextFormat color) {
        if (!new Item(ID).getName().contains("LEATHER_")) {
            throw new IllegalArgumentException("Can only dye leather armor!");
        }
        this.color = color;
       return this;
    }

    public ItemBuilder setTitle(String title) {
       this.title = title;
       return this;
    }
    
    public ItemBuilder addLores(List<String> lores) {
       this.lore.addAll(lores);
       return this;
    }

    public ItemBuilder addLore(String lore) {
       this.lore.add(lore);
       return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchant, int level) {
       if (enchants.containsKey(enchant)) {
          enchants.remove(enchant);
       }
       enchants.put(enchant, level);
       return this;
    }

    public ItemBuilder setPotion(Potion potion) {
        if (ID != ItemID.POTION) {
            this.ID = ItemID.POTION;
       }
       this.potion = potion;
       return this;
    }

    public Item build() {
        Item item = new Item(this.ID, this.amount, this.data);
        item.setCustomName(this.title);
        //TODO:
       /*item.setLore(this.lore);
       item.addEnchantment(this.enchants);
       if (this.potion != null && item instanceof ItemPotion) {
           ((ItemPotion) item).setPotion(this.potion);
       }*/
        return item;
    }

    public ItemBuilder setAmount(int amount) {
       this.amount = amount;
       return this;
    }

    public ItemBuilder clone() {
        ItemBuilder newBuilder = new ItemBuilder(this.ID);

       newBuilder.setTitle(this.title);
       for (String lore : this.lore) {
          newBuilder.addLore(lore);
       }
       for (Map.Entry<Enchantment, Integer> entry : this.enchants.entrySet()) {
          newBuilder.addEnchantment(entry.getKey(), entry.getValue());
       }
       newBuilder.setColor(this.color);
       newBuilder.potion = this.potion;

       return newBuilder;
    }

    public int getType() {
        return this.ID;
    }

    public ItemBuilder setType(int ID) {
        this.ID = ID;
        return this;
    }

    public String getTitle() {
       return this.title;
    }

    public List<String> getLore() {
       return this.lore;
    }

    public boolean hasEnchantment(Enchantment enchant) {
       return this.enchants.containsKey(enchant);
    }

    public int getEnchantmentLevel(Enchantment enchant) {
       return this.enchants.get(enchant);
    }

    public HashMap<Enchantment, Integer> getAllEnchantments() {
       return this.enchants;
    }

    public boolean isItem(Item item) {
        return isItem( item, false );
    }

    public boolean isItem(Item item, boolean strictDataMatch) {
        if (item.getId() != this.getType()) {
          return false;
       }
        if (!item.hasCustomName() && this.getTitle() != null) {
          return false;
       }
        if (!item.getCustomName().equals(this.getTitle())) {
          return false;
       }
        if (item.getLore().length == 0 && !this.getLore().isEmpty()) {
          return false;
       }
        if (item.getLore().length != 0) {
            for (String lore : item.getLore()) {
             if (!this.getLore().contains(lore)) {
                return false;
             }
          }
       }
        for (Enchantment enchant : item.getEnchantments()) {
          if (!this.hasEnchantment(enchant)) {
             return false;
          }
       }
       return true;
    }
 }