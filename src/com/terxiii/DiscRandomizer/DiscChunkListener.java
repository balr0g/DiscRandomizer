package com.terxiii.DiscRandomizer;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.world.ChunkPopulateEvent;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DiscChunkListener implements Listener {
	
	public boolean isEnabled = false;
	
	@EventHandler
	public void onChunkPopulate(ChunkPopulateEvent e)
	{
		if(isEnabled)
		{
			BlockState[] bsts = e.getChunk().getTileEntities();
			Chest swpc;
			Inventory swpi;
			
			for(int count=0;count<bsts.length;count++)
			{
				if(bsts[count] instanceof Chest)
				{
					swpc = (Chest)bsts[count];
					swpi = swpc.getInventory();
					
					List<ItemStack> records = new ArrayList<ItemStack>();
					List<Material> recordTypes = new ArrayList<Material>();
					
					for(int i = 2256; i <= 2266; i++ ) {
						recordTypes.add(Material.getMaterial(i));
					}
					
					records = DiscRandomizer.chestSearch(swpi, recordTypes);
					
					for(int ch=0;ch<records.size();ch++)
					{
						if(ch==0)
						{
							DiscRandomizer.log.info("Chest has records in it!");
						}
						DiscRandomizer.swapDisc(records.get(ch));
					}
				}
			}
		}
	}
}
