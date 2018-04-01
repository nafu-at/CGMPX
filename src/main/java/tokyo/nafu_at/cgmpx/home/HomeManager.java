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

package tokyo.nafu_at.cgmpx.home;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tokyo.nafu_at.cgmpx.PlayerStatus;
import tokyo.nafu_at.cgmpx.PlayerStatusCache;

import java.util.Map;

import static org.bukkit.ChatColor.*;
import static tokyo.nafu_at.cgmpx.CGMPX.sendMessage;
import static tokyo.nafu_at.cgmpx.utils.Teleporter.safeTeleport;

public class HomeManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            PlayerStatus status = PlayerStatusCache.getPlayerStatus(player);
            if (string.length == 0) {
                player.sendMessage("/home <HomePoint>: Teleport to home point");
                player.sendMessage("/home add <HomePoint>: Register home point.");
                player.sendMessage("/home remove <HomePoint>: Delete the home point.");
                player.sendMessage("/home list: Display home point list");
            } else switch (string[0]) {
                case "list":
                    if (player.hasPermission("cgmpx.home.list")) {
                        Map<String, Location> homes = status.getHomes();
                        if (!homes.isEmpty()) {
                            player.sendMessage(AQUA + "======== " + RESET + "Registered home point" + AQUA + " ========");
                            for (String home : homes.keySet()) {
                                player.sendMessage(home);
                            }
                        } else {
                            sendMessage(player, RED + "There is no registered home points!");
                        }
                    } else {
                        sendMessage(player, RED + "You don't have Permission.");
                    }
                    break;

                case "add":
                    if (player.hasPermission("cgmpx.home.add")) {
                        if (string.length >= 2) {
                            if (HomeDataManager.setHome(player, string[1], player.getLocation())) {
                                sendMessage(player, GREEN + "It succeeded to register the home points!");
                            } else {
                                sendMessage(player, RED + "Failed to register the home point.");
                            }
                        } else {
                            if (HomeDataManager.setHome(player, null, player.getLocation())) {
                                sendMessage(player, GREEN + "It succeeded to register the home points!");
                            } else {
                                sendMessage(player, RED + "Failed to register the home point.");
                            }
                        }
                    } else {
                        sendMessage(player, RED + "You don't have Permission.");
                    }
                    break;

                case "remove":
                    if (player.hasPermission("cgmpx.home.remove")) {
                        if (string.length >= 2) {
                            if (HomeDataManager.removeHome(player, string[1])) {
                                sendMessage(player, GREEN + "Successfully deleted home point!");
                            } else {
                                sendMessage(player, RED + "Failed to delete home point.");
                            }
                        } else {
                            sendMessage(player, RED + "Please specify your home point!");
                        }
                    } else {
                        sendMessage(player, RED + "You don't have Permission.");
                    }
                    break;

                default:
                    if (player.hasPermission("cgmpx.home.tp")) {
                        Map<String, Location> homes = status.getHomes();
                        if (!homes.containsKey(string[0])) {
                            sendMessage(player, RED + "Home point was not found.");
                            return true;
                        }
                        safeTeleport(player, homes.get(string[0]));
                        sendMessage(player, GOLD + "You moved to the home point!");
                    } else {
                        sendMessage(player, RED + "You don't have Permission.");
                    }
                    break;
            }
        }
        return true;
    }
}
