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

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatusCache {
    private static Map<Player, PlayerStatus> status;

    static {
        status = new HashMap<>();
    }

    public static PlayerStatus getPlayerStatus(Player player) {
        if (status.containsKey(player))
            return status.get(player);
        else
            return new PlayerStatus(player);
    }

    public static void deletePlayerStatus(Player player) {
        status.remove(player);
    }

    protected static void setPlayerStatus(Player player, PlayerStatus playerStatus) {
        status.put(player, playerStatus);
    }
}
