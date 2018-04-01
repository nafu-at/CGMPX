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

package tokyo.nafu_at.cgmpx.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tokyo.nafu_at.cgmpx.PlayerStatus;
import tokyo.nafu_at.cgmpx.PlayerStatusCache;

public class GodTimer implements Runnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
            if (status.isGod())
                player.setNoDamageTicks(120);
        }
    }
}
