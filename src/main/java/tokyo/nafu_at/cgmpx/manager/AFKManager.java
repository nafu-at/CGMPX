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

package tokyo.nafu_at.cgmpx.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tokyo.nafu_at.cgmpx.CGMPX;
import tokyo.nafu_at.cgmpx.PlayerStatus;
import tokyo.nafu_at.cgmpx.PlayerStatusCache;
import tokyo.nafu_at.cgmpx.timer.AFKTimer;

public class AFKManager implements CommandExecutor {
    private final boolean enableGod = CGMPX.getInstance().getPluginConfig().isUseGodMode();
    private final AFKTimer afkTimer = CGMPX.getInstance().getAfkTimer();
    private final String afkmessage = CGMPX.getInstance().getPluginConfig().getPlayerAFKMessage();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        Player p = (Player) commandSender;
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(p);

        if (status.isAfk())
            afkTimer.resetAFKTime(p);
        else {
            status.setAfk(true);
            if (string.length == 0) {
                if (!afkmessage.isEmpty())
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes
                            ('&', afkmessage.replaceAll("%Player%", p.getDisplayName())));
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; string.length > i; i++) {
                    sb.append(string[i]);
                }
                if (!afkmessage.isEmpty())
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', afkmessage.replaceAll(
                            "%Player%", p.getDisplayName())) + ChatColor.GRAY + ": " + sb.toString());
            }
            if (enableGod)
                status.setGod(true);
        }
        return true;
    }
}
