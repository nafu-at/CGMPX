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
import tokyo.nafu_at.cgmpx.PlayerStatus;
import tokyo.nafu_at.cgmpx.PlayerStatusCache;

import static tokyo.nafu_at.cgmpx.CGMPX.sendMessage;

public class FreezeManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (string.length == 1) {
            Player target = Bukkit.getPlayer(string[0]);
            PlayerStatus status = PlayerStatusCache.getPlayerStatus(target);
            if (target != null) {
                if (status.isFreeze()) {
                    status.setFreeze(false);
                    target.setAllowFlight(false);
                    sendMessage(target, "You were released from freeze.");
                } else {
                    status.setFreeze(true);
                    target.setAllowFlight(true);
                    sendMessage(target, "You have been frozen!");
                }
            } else {
                sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
            }
        }
        return true;
    }
}
