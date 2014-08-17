package so.team.bukkitlejyon;

import java.net.InetSocketAddress;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import so.team.bukkitlejyon.event.OyunaGirdiðinde;

public class BL extends JavaPlugin implements PluginMessageListener {
	
	public static BL instance;
	String menuTitle = "SonOyuncu Lejyün Menüsü";
	    
    public void onEnable() {
    	instance = this;
    	
    	
	    getServer().getMessenger().registerOutgoingPluginChannel(BL.instance, "BungeeLejyon");
	    getServer().getMessenger().registerIncomingPluginChannel(BL.instance, "BungeeLejyon", this);
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new OyunaGirdiðinde(), this);
          
    }

	  @Override
	  public void onPluginMessageReceived(String channel, Player player, byte[] message) {
	    if (channel.equals("BungeeLejyon")) {
	    	ByteArrayDataInput in = ByteStreams.newDataInput(message);
	    	String subchannel = in.readUTF();
	    	if (subchannel.equals("LejyonaSahipmi")) {
	    		String server = in.readUTF();
	    		boolean lejyonaSahipmi = in.readBoolean();
	    	}
	    }
	  }
}
