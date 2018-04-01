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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import tokyo.nafu_at.cgmpx.CGMPX;
import tokyo.nafu_at.cgmpx.spawn.SpawnDataManager;

public class PlayerRespawnEventListener implements Listener {
    private final boolean useWorldRespawm = CGMPX.getInstance().getPluginConfig().isRespawnToWorldSpawn();
    private final boolean forcedRespawn = CGMPX.getInstance().getPluginConfig().isForcedRespawnToWorldSpawn();

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent e) {
        if (useWorldRespawm) {
            if (!forcedRespawn) {
                if (e.getPlayer().getBedSpawnLocation() != null) {
                    return;
                }
            }
            e.setRespawnLocation(SpawnDataManager.getLocation(e.getPlayer().getLocation().getWorld().getName()));
        }
    }
}
