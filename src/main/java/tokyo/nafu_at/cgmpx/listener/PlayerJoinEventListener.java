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

package tokyo.nafu_at.cgmpx.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tokyo.nafu_at.cgmpx.CGMPX;
import tokyo.nafu_at.cgmpx.PlayerStatus;
import tokyo.nafu_at.cgmpx.PlayerStatusCache;
import tokyo.nafu_at.cgmpx.home.HomeDataManager;
import tokyo.nafu_at.cgmpx.spawn.SpawnDataManager;

import static tokyo.nafu_at.cgmpx.utils.Teleporter.safeTeleport;

public class PlayerJoinEventListener implements Listener {
    private final boolean firstJoinSpawn = CGMPX.getInstance().getPluginConfig().isEnableFirstJoinSpawn();
    private final boolean joinFixedSpawn = CGMPX.getInstance().getPluginConfig().isJoinFixedSpawn();

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        if (Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId()).hasPlayedBefore()) {
            if (joinFixedSpawn)
                if (SpawnDataManager.getFixedSpawn() != null)
                    safeTeleport(e.getPlayer(), SpawnDataManager.getFixedSpawn());
        } else {
            if (firstJoinSpawn)
                if (SpawnDataManager.getFirstSpawn() != null)
                    safeTeleport(e.getPlayer(), SpawnDataManager.getFirstSpawn());
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            HomeDataManager.getData(e.getPlayer());
            PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
            if (status.isVanish())
                e.getPlayer().hidePlayer(player);
        }
    }
}
