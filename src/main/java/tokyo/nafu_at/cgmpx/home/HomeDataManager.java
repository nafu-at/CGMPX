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

package tokyo.nafu_at.cgmpx.home;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import tokyo.nafu_at.cgmpx.CGMPX;
import tokyo.nafu_at.cgmpx.PlayerStatus;
import tokyo.nafu_at.cgmpx.PlayerStatusCache;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class HomeDataManager {
    private static final int maxHome = CGMPX.getInstance().getPluginConfig().getMaxHomePoint();
    private static final File dir = new File(CGMPX.getInstance().getDataFolder().getPath() + "/Homes");

    private static boolean checkFile(String path) {
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(path);
        return file.exists();
    }

    public static void saveData(Player player) {
        File yml = new File(dir.getPath() + "/" + player.getUniqueId().toString() + ".yml");
        if (!checkFile(yml.getPath()))
            try {
                yml.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        FileConfiguration conf = YamlConfiguration.loadConfiguration(yml);
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
        Map<String, Location> homes = status.getHomes();
        try {
            for (String home : conf.getConfigurationSection("homes").getKeys(false)) {
                if (!homes.containsKey(home))
                    conf.set("homes." + home, null);
            }
        } catch (NullPointerException e) {

        }
        for (String home : homes.keySet()) {
            Location location = homes.get(home);
            World world = location.getWorld();
            int x = (int) location.getX();
            int y = (int) location.getY();
            int z = (int) location.getZ();

            if (world == null)
                continue;

            conf.set("homes." + home + ".world", world.getName());
            conf.set("homes." + home + ".x", x);
            conf.set("homes." + home + ".y", y);
            conf.set("homes." + home + ".z", z);
        }

        try {
            conf.save(yml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getData(Player player) {
        File yml = new File(dir.getPath() + "/" + player.getUniqueId().toString() + ".yml");
        if (!checkFile(yml.getPath()))
            try {
                yml.createNewFile();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        FileConfiguration conf = YamlConfiguration.loadConfiguration(yml);
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
        Map<String, Location> homes = status.getHomes();
        try {
            for (String homename : conf.getConfigurationSection("homes").getKeys(false)) {
                World world = Bukkit.getWorld(conf.getString("homes." + homename + ".world"));
                int x = conf.getInt("homes." + homename + ".x");
                int y = conf.getInt("homes." + homename + ".y");
                int z = conf.getInt("homes." + homename + ".z");

                if (world == null)
                    continue;

                homes.put(homename, new Location(world, x, y, z));
            }
        } catch (NullPointerException e) {
            return false;
        }
        status.setHomes(homes);
        return true;
    }

    protected static boolean setHome(Player player, String homename, Location location) {
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
        Map<String, Location> homes = status.getHomes();

        if (homes.size() >= maxHome)
            if (!player.hasPermission("cgmpx.bypasslimit"))
                return false;

        if (homename == null) {
            int i = homes.size() + 1;
            homename = "home" + i;
        }

        homes.put(homename, location);
        status.setHomes(homes);
        return true;
    }

    protected static boolean removeHome(Player player, String homename) {
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
        Map<String, Location> homes = status.getHomes();

        if (!homes.containsKey(homename))
            return false;
        homes.remove(homename);

        status.setHomes(homes);
        return true;
    }
}
