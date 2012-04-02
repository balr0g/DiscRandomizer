package com.terxiii.DiscRandomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscRandomizer extends JavaPlugin {
	public static Logger log;
	public DiscChunkListener dcl;
	public DiscEntityListener del;
	protected static Random rng = new Random();
	@Override
	public void onDisable() {
		dcl.isEnabled = false;
		del.isEnabled = false;
		log.info("[RandDisc] Discs are no longer random.");
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		log = Logger.getLogger("Minecraft");
		PluginManager pm = this.getServer().getPluginManager();
		if(dcl==null && del == null)
		{
			dcl = new DiscChunkListener();
			del = new DiscEntityListener();
			pm.registerEvents(del, this);
			pm.registerEvents(dcl, this);
		}
		dcl.isEnabled = true;
		del.isEnabled = true;
		log.info("[RandDisc] Disks are now random.");
	}
	
	public static void swapDisc(ItemStack is)
	{
		int id = is.getTypeId();
		if(id >= 2256 && id <= 2266)
		{
			is.setTypeId(2256+rng.nextInt(11));
		}
	}
	
	public static List<ItemStack> chestSearch(Inventory ch, List<Material> matList)
	{
		HashMap<Integer, ? extends ItemStack> modifying;
		int loc;
		@SuppressWarnings("rawtypes")
		Iterator it;
		
		List<ItemStack> ret = new ArrayList<ItemStack>();
		boolean contains = false;
		for(Material mat : matList) {
			if(ch.contains(mat))
			{
				modifying = ch.all(mat);
				
				it = modifying.entrySet().iterator();
				
				while(it.hasNext())
				{
					@SuppressWarnings("unchecked")
					Map.Entry<Integer, ? extends ItemStack> pairs = (Map.Entry<Integer, ? extends ItemStack>)it.next();
					loc = (Integer) pairs.getKey();
					ret.add(ch.getItem(loc));
				}
			}
		}
		return ret;
	}
}
