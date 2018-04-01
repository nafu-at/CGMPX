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
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static tokyo.nafu_at.cgmpx.CGMPX.sendMessage;
import static tokyo.nafu_at.cgmpx.utils.Teleporter.safeTeleport;

public class TeleportManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (commandSender instanceof Player) {
            switch (command.getName()) {
                case "tp":
                    if (commandSender.hasPermission("cgmpx.tp")) {
                        if (string.length == 4) {
                            Player target = Bukkit.getPlayer(string[0]);
                            if (target != null) {
                                safeTeleport(target, locationCreator(target, string[1], string[2], string[3]));
                            } else {
                                sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                            }
                        } else if (string.length == 3) {
                            Player p = (Player) commandSender;
                            safeTeleport(p, locationCreator(p, string[0], string[1], string[2]));
                        } else if (string.length == 2) {
                            Player p = (Player) commandSender;
                            Player target = Bukkit.getPlayer(string[0]);
                            Player to = Bukkit.getPlayer(string[1]);
                            if (to.equals(p)) {
                                if (!commandSender.hasPermission("cgmpx.tp.here")) {
                                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                                    return true;
                                }
                            }
                            if (target != null && to != null) {
                                safeTeleport(target, to.getLocation());
                            } else {
                                sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                            }
                        } else if (string.length == 1) {
                            Player to = Bukkit.getPlayer(string[0]);
                            if (to != null) {
                                Player p = (Player) commandSender;
                                safeTeleport(p, to.getLocation());
                            } else {
                                sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                            }
                        }
                    } else {
                        sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                    }
                    break;

                case "tpa":
                    if (commandSender.hasPermission("cgmpx.tp.all")) {
                        for (Player target : Bukkit.getOnlinePlayers()) {
                            if (string.length == 3) {
                                safeTeleport(target, locationCreator(target, string[0], string[1], string[2]));
                            } else if (string.length == 1) {
                                Player to = Bukkit.getPlayer(string[0]);
                                if (to != null) {
                                    safeTeleport(target, to.getLocation());
                                } else {
                                    sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                                }
                            }
                        }
                    } else {
                        sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                    }
                    break;

                case "tphere":
                    if (commandSender.hasPermission("cgmpx.tp.here")) {
                        Player Sender = (Player) commandSender;
                        Player target = Bukkit.getPlayer(string[0]);

                        if (target != null) {
                            safeTeleport(target, Sender.getLocation());
                        } else {
                            sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                        }
                    } else {
                        sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                    }
                    break;
            }
        }
        return true;
    }

    private Location locationCreator(Player target, String x, String y, String z) {
        Location loc = target.getLocation();
        double X;
        double Y;
        double Z;

        if (x.indexOf("~") != -1) {
            X = loc.getX() + Double.parseDouble(x.substring(1));
        } else {
            X = Double.parseDouble(x);
        }
        if (y.indexOf("~") != -1) {
            Y = loc.getY() + Double.parseDouble(y.substring(1));
        } else {
            Y = Double.parseDouble(y);
        }
        if (z.indexOf("~") != -1) {
            Z = Double.parseDouble(z.substring(1));
        } else {
            Z = Double.parseDouble(z);
        }
        return new Location(target.getWorld(), X, Y, Z);
    }
}
