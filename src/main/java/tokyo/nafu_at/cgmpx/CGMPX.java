/*
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package tokyo.nafu_at.cgmpx;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;
import tokyo.nafu_at.cgmpx.home.HomeDataManager;
import tokyo.nafu_at.cgmpx.home.HomeManager;
import tokyo.nafu_at.cgmpx.listener.*;
import tokyo.nafu_at.cgmpx.manager.*;
import tokyo.nafu_at.cgmpx.spawn.LobbyManager;
import tokyo.nafu_at.cgmpx.spawn.SpawnManager;
import tokyo.nafu_at.cgmpx.timer.AFKTimer;
import tokyo.nafu_at.cgmpx.timer.GodTimer;
import tokyo.nafu_at.cgmpx.timer.TabUpdater;

public class CGMPX extends JavaPlugin {
    private static CGMPX instance;
    private CGMPXConfig config;
    private AFKTimer afkTimer;
    private Chat vault;
    private DynmapAPI dynmap;

    public static CGMPX getInstance() {
        if (instance == null)
            instance = (CGMPX) Bukkit.getServer().getPluginManager().getPlugin("CGMPX");
        return instance;
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage("[" + ChatColor.YELLOW + getInstance().getName() + ChatColor.RESET + "] " + message);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage("[" + ChatColor.YELLOW + getInstance().getName() + ChatColor.RESET + "] " + message);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new CGMPXConfig();
        config.reloadConfig();
        afkTimer = new AFKTimer();
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
            if (rsp != null) {
                vault = rsp.getProvider();
                getLogger().info("Coordination with Vault has been completed.");
            }
        }
        if (Bukkit.getServer().getPluginManager().getPlugin("dynmap") != null) {
            dynmap = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
            getLogger().info("Coordination with Dynmap has been completed.");
        }

        // RegisterHomePoint
        for (Player player : getServer().getOnlinePlayers())
            HomeDataManager.getData(player);

        // Set CommandExecutor
        getCommand("afk").setExecutor(new AFKManager());
        getCommand("back").setExecutor(new BackManager());
        getCommand("cgm").setExecutor(new GamemodeManager());
        getCommand("day").setExecutor(new TimeManager());
        getCommand("freeze").setExecutor(new FreezeManager());
        getCommand("fly").setExecutor(new FlyManager());
        getCommand("god").setExecutor(new GodManager());
        getCommand("heal").setExecutor(new HealManager());
        getCommand("home").setExecutor(new HomeManager());
        getCommand("lobby").setExecutor(new LobbyManager());
        getCommand("nick").setExecutor(new NickManager());
        getCommand("forcenick").setExecutor(new NickManager());
        getCommand("night").setExecutor(new TimeManager());
        getCommand("rain").setExecutor(new WeatherManager());
        getCommand("nickreset").setExecutor(new NickManager());
        getCommand("sun").setExecutor(new WeatherManager());
        getCommand("spawn").setExecutor(new SpawnManager());
        getCommand("tp").setExecutor(new TeleportManager());
        getCommand("tpa").setExecutor(new TeleportManager());
        getCommand("tphere").setExecutor(new TeleportManager());
        getCommand("workbench").setExecutor(new InventoryManager());
        getCommand("enderchest").setExecutor(new InventoryManager());
        getCommand("vanish").setExecutor(new VanishManager());
        if (dynmap != null)
            getCommand("fullvanish").setExecutor(new VanishManager());
        else
            getCommand("fullvanish").setExecutor(new DisableCommand());

        // Resist Event
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveEventListeer(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportEventListener(), this);

        // Run TaskTimer
        getServer().getScheduler().runTaskTimer(this, afkTimer, 0L, 20L);
        if (config.isEnableTabPrefix())
            getServer().getScheduler().runTaskTimer(this, new TabUpdater(), 0L, 60L);
        getServer().getScheduler().runTaskTimer(this, new GodTimer(), 0L, 100L);
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        for (Player player : getServer().getOnlinePlayers())
            HomeDataManager.saveData(player);
    }

    public CGMPXConfig getPluginConfig() {
        return config;
    }

    public AFKTimer getAfkTimer() {
        return afkTimer;
    }

    public Chat getVault() {
        return vault;
    }

    public DynmapAPI getDynmap() {
        return dynmap;
    }
}
