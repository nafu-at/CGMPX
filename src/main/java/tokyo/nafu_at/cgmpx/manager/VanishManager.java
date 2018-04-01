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
import org.dynmap.DynmapAPI;
import tokyo.nafu_at.cgmpx.CGMPX;
import tokyo.nafu_at.cgmpx.PlayerStatus;
import tokyo.nafu_at.cgmpx.PlayerStatusCache;

import static tokyo.nafu_at.cgmpx.CGMPX.sendMessage;

public class VanishManager implements CommandExecutor {
    private final DynmapAPI dynmap = CGMPX.getInstance().getDynmap();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (command.getName().equals("fullvanish")) {
            if (string.length == 0) {
                Player player = (Player) commandSender;
                PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
                if (status.isVanish())
                    unVanish(player);
                else
                    setfullVanish(player);
            } else {
                Player player = Bukkit.getPlayer(string[0]);
                PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
                if (player != null) {
                    if (status.isVanish())
                        unVanish(player);
                    else
                        setfullVanish(player);
                } else
                    sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
            }
        } else {
            if (string.length == 0) {
                Player player = (Player) commandSender;
                PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
                if (status.isVanish())
                    unVanish(player);
                else
                    setVanish(player);
            } else {
                Player player = Bukkit.getPlayer(string[0]);
                PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
                if (player != null) {
                    if (status.isVanish())
                        unVanish(player);
                    else
                        setVanish(player);
                } else
                    sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
            }
        }
        return true;
    }

    private void setVanish(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.hasPermission("cgmpx.vanish.bypass"))
                online.hidePlayer(player);
        }
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
        status.setVanish(true);
        sendMessage(player, "Vanish has been activated.");
    }

    private void setfullVanish(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.hidePlayer(online);
        }
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
        status.setVanish(true);
        dynmap.setPlayerVisiblity(player, false);
        sendMessage(player, "Vanish has been activated.");
    }

    private void unVanish(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.showPlayer(online);
        }
        PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
        status.setVanish(false);
        if (dynmap != null)
            dynmap.setPlayerVisiblity(player, true);
        sendMessage(player, "Vanish has been disabled.");
    }
}
