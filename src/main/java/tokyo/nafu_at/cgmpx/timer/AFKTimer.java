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
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tokyo.nafu_at.cgmpx.CGMPX;
import tokyo.nafu_at.cgmpx.PlayerStatus;
import tokyo.nafu_at.cgmpx.PlayerStatusCache;

import java.util.HashMap;
import java.util.Map;

public class AFKTimer implements Runnable {
    private final int threshold = CGMPX.getInstance().getPluginConfig().getAfkTimeInterval();
    private final boolean enableGod = CGMPX.getInstance().getPluginConfig().isUseGodMode();
    private final String afkmessage = CGMPX.getInstance().getPluginConfig().getPlayerAFKMessage();
    private final String backmessage = CGMPX.getInstance().getPluginConfig().getPlayerAFKBackMessage();

    private Map<Player, Integer> interval;

    public AFKTimer() {
        interval = new HashMap<>();
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerStatus status = PlayerStatusCache.getPlayerStatus(p);
            if (interval.containsKey(p) || status.isAfk()) {
                if (threshold <= interval.get(p) || status.isAfk()) {
                    if (!status.isVanish()) {
                        if (!status.isAfk()) {
                            status.setAfk(true);
                            if (!afkmessage.isEmpty())
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes(
                                        '&', afkmessage.replaceAll("%Player%", p.getDisplayName())));
                            if (enableGod)
                                status.setGod(true);
                        }
                    }
                } else {
                    interval.put(p, interval.get(p) + 1);
                }
            } else {
                interval.put(p, 1);
            }
        }
    }

    public void resetAFKTime(Player p) {
        interval.put(p, 0);
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(p);
        if (!status.isVanish() && status.isAfk()) {
            status.setAfk(false);
            if (!backmessage.isEmpty())
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes(
                        '&', backmessage.replaceAll("%Player%", p.getDisplayName())));
            if (enableGod)
                status.setGod(false);
        }
    }
}
