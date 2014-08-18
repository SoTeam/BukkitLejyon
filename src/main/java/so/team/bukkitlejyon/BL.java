package so.team.bukkitlejyon;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import so.team.bukkitlejyon.api.PluginMesajAPI;
import so.team.bukkitlejyon.event.Sa�T�kNPC;
import so.team.bukkitlejyon.npc.NPCFactory;
import so.team.bukkitlejyon.npc.SpawnNPC;

public class BL extends JavaPlugin implements PluginMessageListener {
	
	public static BL instance;
	String menuTitle = "SonOyuncu Lejy�n Men�s�";
	
	//Classlar
		public static PluginMesajAPI pma;
		public static Menu menu;
	//Classlar
		
		
	//Lejyonlar DBsi
		public HashMap<String,Integer> ToplamPuan = new HashMap<String,Integer>();
		public HashMap<String,Integer> AylikPuan = new HashMap<String,Integer>();
		public HashMap<String,Integer> LejyonSeviyesi = new HashMap<String,Integer>();
		public HashMap<String,String> LejyonuKuran = new HashMap<String,String>();
		public HashMap<String,String> MOTD = new HashMap<String,String>();
		public HashMap<String,Long> FaturaTarihi = new HashMap<String,Long>();
	
	//Oyuncular DBsi
		public HashMap<String,String> OyuncuLejyonu = new HashMap<String,String>();
		public HashMap<String,String> OyuncuR�tbesi = new HashMap<String,String>();
		
	//NPC datas�
		public HashMap<String,String> NPCtipi = new HashMap<String,String>();
//		public HashMap<String,String> NPCkafas� = new HashMap<String,String>();
		public HashMap<String,Location> NPCkoordinat� = new HashMap<String,Location>();
		
	    
    public void onEnable() {
    	instance = this;

		//Classlar
			pma = new PluginMesajAPI();
			menu = new Menu();
		//Classlar
    	
		if (getConfig().contains("NPC.1")){
			npcleriY�kle();
		}
			
	    getServer().getMessenger().registerOutgoingPluginChannel(BL.instance, "BungeeLejyon");
	    getServer().getMessenger().registerIncomingPluginChannel(BL.instance, "BungeeLejyon", this);
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Sa�T�kNPC(), this);
        pm.registerEvents(new InventoryClick(), this);
        
    }
    
	public void npcleriY�kle(){
		for (int i = 1;;){
			if (getConfig().contains("NPC." + i)){
	        	NPCtipi.put(getConfig().getString("NPC."+i+".ismi"), getConfig().getString("NPC."+i+".tipi"));
	    //    	NPCkafas�.put(getConfig().getString("NPC."+i+".ismi"), getConfig().getString("NPC."+i+".kafasi"));
	        	NPCkoordinat�.put(getConfig().getString("NPC."+i+".ismi"), Metotlar.str2loc(getConfig().getString("NPC."+i+".koordinati")));
				SpawnNPC.spawnNPC(getConfig().getString("NPC."+i+".ismi"));
	        	i++;
			} else {
				return;
			}
		}
	}
	
	public int m�saitSay�(){
		for (int i = 1;;){
			if (getConfig().contains("NPC." + i)){
	        	i++;
			} else {
				return i;
			}
		}
	}
    
	@Override
	@EventHandler
	public boolean onCommand(final CommandSender sender, Command command,
			String label, String[] args) {
			if (label.equalsIgnoreCase("npc")) {
				if (sender instanceof Player) {
					if (args.length == 0) {
						sender.sendMessage("�e/npc yeni <ismi> [<lejyonYoneticisi | lejyonMarketi>] �r- Yeni npc yarat�r.");
					} else if (args.length == 1){
						if (args[0].toLowerCase().equalsIgnoreCase("reload")){
							BL.instance.reloadConfig();
						}
						if (args[0].toLowerCase().equalsIgnoreCase("respawn")){
							NPCFactory.despawnAll();
							NPCtipi.clear();
				//			NPCkafas�.clear();
							NPCkoordinat�.clear();
							npcleriY�kle();
						}
						if (args[0].toLowerCase().equalsIgnoreCase("despawn")){
							NPCFactory.despawnAll();
						}			
					} else if (args.length == 2){
						if (args[0].toLowerCase().equalsIgnoreCase("yeni")){
							int s�ra = m�saitSay�();
							getConfig().set("NPC."+s�ra+".ismi", args[1]);
							getConfig().set("NPC."+s�ra+".tipi", "diger");
				//			getConfig().set("NPC."+s�ra+".kafasi", args[2]);
							getConfig().set("NPC."+s�ra+".koordinati", Metotlar.loc2str(((Player) sender).getLocation()));
							saveConfig();
				//			NPCkafas�.put(args[1], args[2]);
							NPCkoordinat�.put(args[1], ((Player) sender).getLocation());
							NPCtipi.put(args[1], "diger");
							SpawnNPC.spawnNPC(args[1]);
							sender.sendMessage("�6Yeni npc eklendi, �zellikleri: �e�smi: �c" + args[1] + " �eTipi: �cdiger");
						}
					} else if (args.length == 3) { //npc yeni DonduranAtes lejyonYonetimi
						if (args[0].toLowerCase().equalsIgnoreCase("yeni")){
							if (!(args[2].equalsIgnoreCase("lejyonYoneticisi") || args[2].equalsIgnoreCase("lejyonMarketi"))){
								sender.sendMessage("�cNpc tipi lejyonYoneticisi yada lejyonMarketi olmal�d�r.");
								return false;
							}
							int s�ra = m�saitSay�();
							getConfig().set("NPC."+s�ra+".ismi", args[1]);
							getConfig().set("NPC."+s�ra+".tipi", args[2]);
				//			getConfig().set("NPC."+s�ra+".kafasi", args[2]);
							getConfig().set("NPC."+s�ra+".koordinati", Metotlar.loc2str(((Player) sender).getLocation()));
							saveConfig();
				//			NPCkafas�.put(args[1], args[2]);
							NPCkoordinat�.put(args[1], ((Player) sender).getLocation());
							NPCtipi.put(args[1], args[2]);
							SpawnNPC.spawnNPC(args[1]);
							sender.sendMessage("�6Yeni npc eklendi, �zellikleri: �e�smi: �c" + args[1] + " �eTipi: �c" + args[2]);
						}
					}
				}
			}
			return false;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (channel.equals("BungeeLejyon")) {
	    	ByteArrayDataInput in = ByteStreams.newDataInput(message);
	    	String subchannel = in.readUTF();
	    	if (subchannel.equals("LejyonuCek")) {
	    		String kisi = in.readUTF();
	    		String lejyonu = in.readUTF();
	    			    		
	    		if (lejyonu.equalsIgnoreCase("Yok")){
	    			if (OyuncuLejyonu.containsKey(kisi)){
	    				OyuncuLejyonu.remove(kisi);
	    			}
	    		} else {
	    			OyuncuLejyonu.put(kisi, lejyonu);
	    		}
	    	} else if(subchannel.equals("R�tbeCek")) {
	    		String kisi = in.readUTF();
	    		String r�tbe = in.readUTF();
	    		
	    		if (r�tbe != null){
	    			OyuncuR�tbesi.put(kisi, r�tbe);
	    		} else {
	    			if (OyuncuR�tbesi.containsKey(kisi)){
	    				OyuncuR�tbesi.remove(kisi);
	    			}
	    		}
	    	}
		}
	}
}
