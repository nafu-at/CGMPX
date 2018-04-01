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

package tokyo.nafu_at.cgmpx.utils;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import tokyo.nafu_at.cgmpx.CGMPX;

public class Teleporter {
    private static final boolean safeTeleport = CGMPX.getInstance().getPluginConfig().isUseSafeTeleport();

    public static boolean safeTeleport(Player player, Location location) {
        if (safeTeleport && player.getGameMode().equals(GameMode.SURVIVAL)) {
            int x = location.getBlockX();
            int y = 256;
            int z = location.getBlockZ();

            do {
                if (!player.getWorld().getBlockAt(x, y, z).getType().equals(Material.AIR)) {
                    player.teleport(new Location(location.getWorld(), x, y + 1, z));
                    return true;
                } else
                    y--;
            } while (y >= 0);
        } else {
            player.teleport(location);
            return true;
        }
        return false;
    }
}
