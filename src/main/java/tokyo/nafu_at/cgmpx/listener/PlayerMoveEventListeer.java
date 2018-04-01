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

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tokyo.nafu_at.cgmpx.CGMPX;
import tokyo.nafu_at.cgmpx.PlayerStatus;
import tokyo.nafu_at.cgmpx.PlayerStatusCache;
import tokyo.nafu_at.cgmpx.timer.AFKTimer;

import java.util.List;

public class PlayerMoveEventListeer implements Listener {
    private final AFKTimer afkTimer = CGMPX.getInstance().getAfkTimer();
    private final boolean antiPush = CGMPX.getInstance().getPluginConfig().isUseAntiPush();

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(e.getPlayer());
        if (e.getFrom().getBlockX() == e.getTo().getBlockX()
                && e.getFrom().getBlockZ() == e.getTo().getBlockZ()
                && e.getFrom().getBlockY() == e.getTo().getBlockY()) {
            if (status.isFreeze()) {
                e.setCancelled(true);
                return;
            }
            if (antiPush && status.isAfk()) {
                List<Entity> near =
                        (List<Entity>) e.getFrom().getWorld().getNearbyEntities(e.getFrom(), 1, 2, 1);
                for (Entity entity : near) {
                    if (entity.getType().equals(EntityType.PLAYER) &&
                            !entity.getName().equals(e.getPlayer().getName())) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
            afkTimer.resetAFKTime(e.getPlayer());
        }
    }
}
