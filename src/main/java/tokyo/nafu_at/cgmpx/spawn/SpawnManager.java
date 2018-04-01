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

package tokyo.nafu_at.cgmpx.spawn;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static tokyo.nafu_at.cgmpx.CGMPX.sendMessage;
import static tokyo.nafu_at.cgmpx.utils.Teleporter.safeTeleport;

public class SpawnManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (string.length == 2) {
                if (!string[0].equals("set")) {
                    return false;
                } else if (!commandSender.hasPermission("cgmpx.spawn.set")) {
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                } else switch (string[1]) {
                    case "spawn":
                        sendMessage(commandSender, "Register this location as a spawn location.");
                        SpawnDataManager.saveLocation(player.getLocation());
                        break;

                    case "first":
                        sendMessage(commandSender, "Register this location as a first spawn location.");
                        SpawnDataManager.saveFirstSpawn(player.getLocation());
                        break;

                    case "fixed":
                        sendMessage(commandSender, "Register this location as a fixed spawn location.");
                        SpawnDataManager.saveFixedSpawn(player.getLocation());
                        break;
                }
            } else {
                if (commandSender.hasPermission("cgmpx.spawn")) {
                    if (SpawnDataManager.getLocation(player.getWorld().getName()) == null) {
                        sendMessage(commandSender, ChatColor.RED + "Spawn point is not set for this world!");
                    } else
                        safeTeleport(player, SpawnDataManager.getLocation(player.getWorld().getName()));
                } else
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
            }
        }
        return true;
    }
}
