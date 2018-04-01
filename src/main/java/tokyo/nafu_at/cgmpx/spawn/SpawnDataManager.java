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

package tokyo.nafu_at.cgmpx.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tokyo.nafu_at.cgmpx.CGMPX;

import java.io.File;
import java.io.IOException;

public class SpawnDataManager {
    private static final boolean multiWorld = CGMPX.getInstance().getPluginConfig().isMultiWorldSpanwn();
    private static final File dir = new File(CGMPX.getInstance().getDataFolder().getPath() + "/Homes");
    private static final File yml = new File(dir.getPath() + "/spawns.yml");
    private static FileConfiguration data;

    static {
        if (!dir.exists())
            dir.mkdirs();
        if (!yml.exists()) {
            try {
                yml.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        data = YamlConfiguration.loadConfiguration(yml);
    }

    public static void saveLocation(Location location) {
        if (multiWorld) {
            String world = location.getWorld().getName();
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
            float yaw = location.getYaw();
            float pitch = location.getPitch();

            data.set(world + ".X", x);
            data.set(world + ".Y", y);
            data.set(world + ".Z", z);
            data.set(world + ".Yaw", yaw);
            data.set(world + ".Pitch", pitch);
            try {
                data.save(yml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String world = location.getWorld().getName();
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
            float yaw = location.getYaw();
            float pitch = location.getPitch();

            data.set("Spawn" + ".World", world);
            data.set("Spawn" + ".X", x);
            data.set("Spawn" + ".Y", y);
            data.set("Spawn" + ".Z", z);
            data.set("Spawn" + ".Yaw", yaw);
            data.set("Spawn" + ".Pitch", pitch);
            try {
                data.save(yml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Location getLocation(String world) {
        if (multiWorld) {
            if (!data.contains(world))
                return null;
            double x = data.getDouble(world + ".X");
            double y = data.getDouble(world + ".Y");
            double z = data.getDouble(world + ".Z");
            double yaw = data.getDouble(world + ".Yaw");
            double pitch = data.getDouble(world + ".Pitch");

            return new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch);
        } else {
            if (!data.contains("Spawn" + ".World"))
                return null;
            String world2 = data.getString("Spawn" + ".World");
            double x = data.getDouble("Spawn" + ".X");
            double y = data.getDouble("Spawn" + ".Y");
            double z = data.getDouble("Spawn" + ".Z");
            double yaw = data.getDouble("Spawn" + ".Yaw");
            double pitch = data.getDouble("Spawn" + ".Pitch");

            return new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch);
        }
    }

    public static void saveLobby(Location location) {
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        data.set("Lobby" + ".World", world);
        data.set("Lobby" + ".X", x);
        data.set("Lobby" + ".Y", y);
        data.set("Lobby" + ".Z", z);
        data.set("Lobby" + ".Yaw", yaw);
        data.set("Lobby" + ".Pitch", pitch);
        try {
            data.save(yml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getLobby() {
        if (!data.contains("Lobby" + ".World"))
            return null;
        String world = data.getString("Lobby" + ".World");
        double x = data.getDouble("Lobby" + ".X");
        double y = data.getDouble("Lobby" + ".Y");
        double z = data.getDouble("Lobby" + ".Z");
        double yaw = data.getDouble("Lobby" + ".Yaw");
        double pitch = data.getDouble("Lobby" + ".Pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch);
    }

    public static void saveFirstSpawn(Location location) {
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        data.set("FirstSpawn" + ".World", world);
        data.set("FirstSpawn" + ".X", x);
        data.set("FirstSpawn" + ".Y", y);
        data.set("FirstSpawn" + ".Z", z);
        data.set("FirstSpawn" + ".Yaw", yaw);
        data.set("FirstSpawn" + ".Pitch", pitch);
        try {
            data.save(yml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getFirstSpawn() {
        if (!data.contains("FirstSpawn" + ".World"))
            return null;
        String world = data.getString("FirstSpawn" + ".World");
        double x = data.getDouble("FirstSpawn" + ".X");
        double y = data.getDouble("FirstSpawn" + ".Y");
        double z = data.getDouble("FirstSpawn" + ".Z");
        double yaw = data.getDouble("FirstSpawn" + ".Yaw");
        double pitch = data.getDouble("FirstSpawn" + ".Pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch);
    }

    public static void saveFixedSpawn(Location location) {
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        data.set("FixedSpawn" + ".World", world);
        data.set("FixedSpawn" + ".X", x);
        data.set("FixedSpawn" + ".Y", y);
        data.set("FixedSpawn" + ".Z", z);
        data.set("FixedSpawn" + ".Yaw", yaw);
        data.set("FixedSpawn" + ".Pitch", pitch);
        try {
            data.save(yml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getFixedSpawn() {
        if (!data.contains("FixedSpawn" + ".World"))
            return null;
        String world = data.getString("FixedSpawn" + ".World");
        double x = data.getDouble("FixedSpawn" + ".X");
        double y = data.getDouble("FixedSpawn" + ".Y");
        double z = data.getDouble("FixedSpawn" + ".Z");
        double yaw = data.getDouble("FixedSpawn" + ".Yaw");
        double pitch = data.getDouble("FixedSpawn" + ".Pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch);
    }
}
