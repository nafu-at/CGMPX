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

public class GodManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (commandSender instanceof Player) {
            if (string.length == 0) {
                if (commandSender.hasPermission("cgmpx.god")) {
                    Player p = (Player) commandSender;
                    togglegod(p);
                } else
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
            } else {
                if (commandSender.hasPermission("cgmpx.admin.god")) {
                    Player target = Bukkit.getPlayer(string[0]);
                    if (target != null)
                        togglegod(target);
                } else
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
            }
        }
        return true;
    }

    public void togglegod(Player player) {
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
        if (status.isGod()) {
            status.setGod(false);
            sendMessage(player, "You are no longer a god.");
        } else {
            status.setGod(true);
            sendMessage(player, "You became a god.");
        }
    }
}
