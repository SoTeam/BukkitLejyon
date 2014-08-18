package so.team.bukkitlejyon;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import so.team.bukkitlejyon.cmdBlock.api.InputGui;
import so.team.bukkitlejyon.cmdBlock.api.InputPlayer;

public class TextGui {
	
	public void yeniLejyon(Player player)
	{
	InputPlayer iplayer = BL.il.getPlayer(player);
	 
	iplayer.openGui(new InputGui() {
	 
	    @Override
	    public String getDefaultText() {
	        return "�6�lLejyon ad�: �r";
	    }
	 
	    @Override
	    public void onConfirm(InputPlayer player, String input) {
	    	if (input.length() < 19)
	    	{
		    	System.out.println(input);
		    	player.getPlayer().sendMessage("�6Lejyon ad�n� kutudan hi� bir �ey silmeden yaz�n.");
		    	yeniLejyonTekrar(player.getPlayer());	
	    	}
	    	else if (input.length() < 24)
	    	{
		    	//BL.menu.LejyonMen�s�n�A�(player.getPlayer(), input.substring(18));
	    	}
	    	else
	    	{
	    		//BL.menu.LejyonMen�s�n�A�(player.getPlayer(), input.substring(19));
	    	}	   
	    }
	 
	    @Override
	    public void onCancel(InputPlayer player) {	    	
	    	player.getPlayer().sendMessage("�6Lejyon kurmaya haz�r hissetti�inde yine bekleriz.");
	    } 
	 
	});
	}
	
	
	
	public void yeniLejyonTekrar(final Player player)
	{
	      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BL.instance, new Runnable()
	      {
	    	  public void run()
	          {
	    			  yeniLejyon(player);    		  
	          }
	      }, 5L);

	}

}
