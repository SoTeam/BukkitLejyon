package so.team.bukkitlejyon.api;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.entity.Player;

import so.team.bukkitlejyon.BL;

public class PluginMesajAPI {
	  
	public void oyuncuLejyonuCek(Player p){
      try {
	          ByteArrayOutputStream b = new ByteArrayOutputStream();
	          DataOutputStream out = new DataOutputStream(b);
	          out.writeUTF("LejyonuCek");
	          out.writeUTF(p.getName());
	          p.sendPluginMessage(BL.instance, "BungeeLejyon", b.toByteArray());

	        } catch (IOException e) {
	          e.printStackTrace();
	     }
	}
	
	public void oyuncuR�tbesini�ek(Player p){
	      try {
		          ByteArrayOutputStream b = new ByteArrayOutputStream();
		          DataOutputStream out = new DataOutputStream(b);
		          out.writeUTF("R�tbeCek");
		          out.writeUTF(p.getName());
		          p.sendPluginMessage(BL.instance, "BungeeLejyon", b.toByteArray());

		        } catch (IOException e) {
		          e.printStackTrace();
		        }
	}
	
	public void yeniLejyon(Player p,String lejyonAd�){
	      try {
		          ByteArrayOutputStream b = new ByteArrayOutputStream();
		          DataOutputStream out = new DataOutputStream(b);
		          out.writeUTF("YeniLejyon");
		          out.writeUTF(p.getName());
		          out.writeUTF(lejyonAd�);
		          p.sendPluginMessage(BL.instance, "BungeeLejyon", b.toByteArray());

		        } catch (IOException e) {
		          e.printStackTrace();
		     }
		}

}
