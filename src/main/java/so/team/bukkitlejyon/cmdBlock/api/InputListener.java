package so.team.bukkitlejyon.cmdBlock.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import so.team.bukkitlejyon.BL;
import so.team.bukkitlejyon.cmdBlock.plugin.InputGuiPlayer;

public class InputListener
  implements Listener
{
  private Map<String, InputGuiPlayer> players = new HashMap<String, InputGuiPlayer>();
  
  public InputGuiPlayer getPlayer(Player player)
  {
    String name = player.getName();
    if (this.players.containsKey(name)) {
      return (InputGuiPlayer)this.players.get(name);
    }
    InputGuiPlayer iplayer = new InputGuiPlayer(BL.instance, player);
    this.players.put(name, iplayer);
    
    return iplayer;
  }
  
  public void removePlayer(Player player)
  {
    this.players.remove(player.getName());
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e)
  {
    getPlayer(e.getPlayer());
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e)
  {
    removePlayer(e.getPlayer());
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e)
  {
    InputGuiPlayer player = getPlayer(e.getPlayer());
    if (player.isCheckingMovement()) {
      player.setCancelled();
    }
  }
}
