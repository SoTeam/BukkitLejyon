package so.team.bukkitlejyon.api;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class NPC {

	String isim;
	World world;
	public int id;
	public static JavaPlugin plugin;
	Location l;
	int elindeki;
	private UUID uuid;

	public static ArrayList<Location> koordinatlar = new ArrayList<Location>();
	public static ArrayList<NPC> npcler = new ArrayList<NPC>();

	@SuppressWarnings("rawtypes")
	private void setPrivateField(Class type, Object object, String name,
			Object value) {
		try {
			Field f = type.getDeclaredField(name);
			f.setAccessible(true);
			f.set(object, value);
			f.setAccessible(false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public NPC(World world, String isim, int id, Location koordinat, int elindekiID) {
		this.isim = isim;
		this.world = world;
		this.id = id;
		this.l = koordinat;
		this.elindeki = elindekiID;
		this.uuid = UUID.randomUUID();
		DataWatcher d = new DataWatcher(null);
		d.a(0, (Object) (byte) 0);
		d.a(1, (Object) (short) 0);
		d.a(8, (Object) (byte) 0);
		PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn();
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "a", id);
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "b",
				new GameProfile(uuid, isim));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "c",
				((int) l.getX() * 32));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "d",
				((int) l.getY() * 32));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "e",
				((int) l.getZ() * 32));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "f",
				getCompressedAngle(l.getYaw()));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "g",
				getCompressedAngle(l.getPitch()));
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "h",
				elindekiID);
		setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "i", d);

		PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport();
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "a", id);
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "b",
				((int) l.getX() * 32));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "c",
				((int) l.getY() * 32));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "d",
				((int) l.getZ() * 32));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "e",
				getCompressedAngle(l.getYaw()));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "f",
				getCompressedAngle(l.getPitch()));

		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawn);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(tp);
		}
		koordinatlar.add(l);
		npcler.add(this);
	}

	public void gönder(Location koordinat) {
		PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport();
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "a", id);
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "b",
				((int) (koordinat.getX() * 32)));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "c",
				((int) (koordinat.getY() * 32)));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "d",
				((int) (koordinat.getZ() * 32)));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "e",
				getCompressedAngle(koordinat.getYaw()));
		setPrivateField(PacketPlayOutEntityTeleport.class, tp, "f",
				getCompressedAngle(koordinat.getPitch()));
		koordinatlar.remove(l);
		npcler.remove(this);
		this.l = koordinat;
		koordinatlar.add(l);
		npcler.add(this);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(tp);
		}

	}

	private byte getCompressedAngle(float value) {
		return (byte) ((value * 256.0F) / 360.0F);
	}

	private byte getCompressedAngle2(float value) {
		return (byte) ((value * 256.0F) / 360.0F);
	}

	public void sil() {
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(id);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public void itemleriAyarla(ItemStack elindeki, ItemStack botlarý, ItemStack pantolon,
			ItemStack göðüslük, ItemStack kask) {

		PacketPlayOutEntityEquipment[] ps = new PacketPlayOutEntityEquipment[] {
				new PacketPlayOutEntityEquipment(id, 1,
						CraftItemStack.asNMSCopy(botlarý)),
				new PacketPlayOutEntityEquipment(id, 2,
						CraftItemStack.asNMSCopy(pantolon)),
				new PacketPlayOutEntityEquipment(id, 3,
						CraftItemStack.asNMSCopy(göðüslük)),
				new PacketPlayOutEntityEquipment(id, 4,
						CraftItemStack.asNMSCopy(kask)),
				new PacketPlayOutEntityEquipment(id, 0,
						CraftItemStack.asNMSCopy(elindeki)) };
		for (PacketPlayOutEntityEquipment pack : ps) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(pack);
			}
		}
	}

	@Deprecated
	public void isimAyarla(String yeniÝsim) {
		DataWatcher d = new DataWatcher(null);
		d.a(0, (Object) (byte) 0);
		d.a(1, (Object) (short) 0);
		d.a(8, (Object) (byte) 0);
		d.a(10, (Object) (String) yeniÝsim);
		// d.a(11, (Object) (byte) 0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(
				id, d, true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet40);
		}
	}

	public void gizle(Player player) {
		DataWatcher d = new DataWatcher(null);
		d.a(0, (Object) (byte) 32);
		d.a(1, (Object) (short) 0);
		d.a(8, (Object) (byte) 0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(
				id, d, true);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet40);
	}

	public void göster(Player player) {
		DataWatcher d = new DataWatcher(null);
		d.a(0, (Object) (byte) 0);
		d.a(1, (Object) (short) 0);
		d.a(8, (Object) (byte) 0);
		PacketPlayOutEntityMetadata packet40 = new PacketPlayOutEntityMetadata(
				id, d, true);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet40);
	}


	public double getX() {
		return l.getX();
	}

	public double getY() {
		return l.getY();
	}

	public double getZ() {
		return l.getZ();
	}

	public Location getLocation() {
		return l;
	}

	public static void serialize() {
		File file = new File(plugin.getDataFolder(), "npcler.yml");
		FileConfiguration npc = YamlConfiguration.loadConfiguration(file);
		int current = 1;
		for (NPC human : npcler) {
			npc.set("npcler.npc " + current + ".isim", human.isim);
			npc.set("npcler.npc " + current + ".world", human.world.getName());
			npc.set("npcler.npc " + current + ".id", human.id);
			npc.set("npcler.npc " + current + ".koordinat",
					locationToString(human.l));
			npc.set("npcler.npc " + current + ".item", human.elindeki);
			current++;
		}
		try {
			npc.save(file);
		} catch (IOException e) {
			System.out.println("HATA: " + e.getMessage());
		}
	}

	public static ArrayList<NPC> deserialize() {
		ArrayList<NPC> npcler = new ArrayList<NPC>();
		File file = new File(plugin.getDataFolder() + "/npcler.yml");
		FileConfiguration npcs = YamlConfiguration.loadConfiguration(file);
		int current = 1;
		while (npcs.isConfigurationSection("npcler.npc " + current)) {
			ConfigurationSection sec = npcs
					.getConfigurationSection("npcler.npc " + current);
			NPC npc = new NPC(Bukkit.getWorld(sec.getString("world")),
					sec.getString("isim"), sec.getInt("id"),
					locationFromString(sec.getString("koordinat")),
					sec.getInt("item"));
			npcler.add(npc);
			koordinatlar.add(npc.l);
			current++;
		}
		try {
			npcs.set("npcler", null);
			npcs.save(file);
		} catch (IOException e) {
			System.out.println("HATA: " + e.getMessage());
		}
		return npcler;
	}

	public static Location locationFromString(String string) {
		String[] location = string.split(",");
		return new Location(Bukkit.getWorld(location[0]),
				Double.parseDouble(location[1]),
				Double.parseDouble(location[2]),
				Double.parseDouble(location[3]));
	}

	public static String locationToString(Location loc) {
		return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY()
				+ "," + loc.getZ();
	}
}