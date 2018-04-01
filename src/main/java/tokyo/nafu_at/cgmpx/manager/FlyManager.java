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

import static tokyo.nafu_at.cgmpx.CGMPX.sendMessage;

public class FlyManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (string.length == 0) {
                if (p.getAllowFlight()) {
                    p.setAllowFlight(false);
                    sendMessage(p, ChatColor.GOLD + "Disabled Fly");
                } else {
                    p.setAllowFlight(true);
                    sendMessage(p, ChatColor.GOLD + "Enabled Fly");
                }
            } else switch (string[0]) {
                case "on":
                    if (string.length >= 2) {
                        if (commandSender.hasPermission("cgmpx.admin.fly.toggle")) {
                            if (Bukkit.getPlayerExact(string[1]) != null)
                                setAllowFly(commandSender, Bukkit.getPlayer(string[1]));
                            else
                                sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                        } else
                            sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                    } else {
                        if (commandSender.hasPermission("cgmpx.fly.toggle"))
                            setAllowFly(p, p);
                        else
                            sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                    }
                    break;

                case "off":
                    if (string.length >= 2) {
                        if (commandSender.hasPermission("cgmpx.admin.fly.toggle")) {
                            if (Bukkit.getPlayerExact(string[1]) != null)
                                setDenyFly(commandSender, Bukkit.getPlayer(string[1]));
                            else
                                sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                        } else
                            sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                    } else {
                        if (commandSender.hasPermission("cgmpx.fly.toggle"))
                            setDenyFly(commandSender, p);
                        else
                            sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                    }
                    break;

                case "speed":
                    if (string.length >= 2) {
                        if (string.length >= 3) {
                            if (commandSender.hasPermission("cgmpx.admin.fly.speed")) {
                                if (Bukkit.getPlayerExact(string[2]) != null)
                                    setFlySpeed(commandSender, Bukkit.getPlayer(string[2]), string[1]);
                                else
                                    sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                            } else
                                sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                        } else if (string.length == 2) {
                            if (commandSender.hasPermission("cgmpx.fly.speed"))
                                setFlySpeed(commandSender, p, string[1]);
                            else
                                sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                        } else
                            sendMessage(commandSender, ChatColor.RED + "Please enter the correct value.");
                    } else
                        sendMessage(commandSender, ChatColor.RED + "Please enter the correct value.");
                    break;

                case "help":
                default:
                    commandSender.sendMessage(ChatColor.GOLD + "====== CGMPX FlyManager Help ======");
                    commandSender.sendMessage("/fly on: Turn on Fly");
                    commandSender.sendMessage("/fly off: Turn off Fly");
                    commandSender.sendMessage("/fly speed [value]: Change flight speed");
                    break;
            }
        }
        return true;
    }

    private void setAllowFly(CommandSender commandSender, Player target) {
        if (Bukkit.getPlayerExact(target.getName()) != null) {
            target.setAllowFlight(true);
            sendMessage(target, ChatColor.GOLD + "Enabled Fly");
        } else
            sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
    }

    private void setDenyFly(CommandSender commandSender, Player target) {
        if (Bukkit.getPlayerExact(target.getName()) != null) {
            target.setAllowFlight(false);
            sendMessage(target, ChatColor.GOLD + "Disabled Fly");
        } else
            sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
    }

    private void setFlySpeed(CommandSender commandSender, Player target, String speed) {
        if (Bukkit.getPlayerExact(target.getName()) != null) {
            float realspeed = (float) 0.1;
            try {
                realspeed = Float.parseFloat(speed) / 10;
                if (!(realspeed >= 0) || realspeed > 1) {
                    sendMessage(commandSender, ChatColor.RED + "The value must be between 0 and 10.");
                    return;
                }
            } catch (NumberFormatException e) {
                sendMessage(commandSender, ChatColor.RED + "Please enter the correct value.");
            }
            target.setFlySpeed(realspeed);
            sendMessage(target, "Succeeded in changing the flight speed. Current speed: " + target.getFlySpeed() * 10);
        } else
            sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
    }
}
