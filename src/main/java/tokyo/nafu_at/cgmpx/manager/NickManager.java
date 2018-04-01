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

import static tokyo.nafu_at.cgmpx.CGMPX.sendMessage;

public class NickManager implements CommandExecutor {
    private final String prefix = CGMPX.getInstance().getPluginConfig().getNickCommandPrefix();
    private final int maxLength = CGMPX.getInstance().getPluginConfig().getNickMaxLength();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (command.getName().equals("nick")) {
            if (string.length == 1) {
                if (commandSender.hasPermission("cgmpx.nick")) {
                    Player player = (Player) commandSender;
                    setNickName(commandSender, player, string[0]);
                } else {
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                }
            } else if (string.length == 2) {
                if (commandSender.hasPermission("cgmpx.admin.nick")) {
                    Player player = Bukkit.getPlayer(string[0]);
                    if (player != null) {
                        setNickName(commandSender, player, string[1]);
                    } else {
                        sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                    }
                } else {
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                }
            }
        } else if (command.getName().equals("forcenick")) {
            if (string.length == 1) {
                if (commandSender.hasPermission("cgmpx.nick.force")) {
                    Player player = (Player) commandSender;
                    setForceNick(commandSender, player, string[0]);
                } else {
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                }
            } else if (string.length == 2) {
                if (commandSender.hasPermission("cgmpx.admin.nick")) {
                    Player player = Bukkit.getPlayer(string[0]);
                    if (player != null) {
                        setForceNick(commandSender, player, string[1]);
                    } else {
                        sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                    }
                } else {
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                }
            }
        } else {
            if (string.length == 1) {
                if (commandSender.hasPermission("cgmpx.admin.nick")) {
                    Player player = Bukkit.getPlayer(string[0]);
                    if (player != null) {
                        player.setDisplayName(player.getName());
                        sendMessage(commandSender, "Successfully Reset Nickname.");
                    } else {
                        sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                    }
                } else {
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                }
            } else {
                if (commandSender.hasPermission("cgmpx.nick")) {
                    Player player = (Player) commandSender;
                    player.setDisplayName(player.getName());
                    sendMessage(commandSender, "Successfully Reset Nickname.");
                } else {
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
                }
            }
        }
        return true;
    }

    private void setNickName(CommandSender commandSender, Player player, String name) {
        if (ChatColor.stripColor(name).length() <= maxLength) {
            String nick = prefix + ChatColor.translateAlternateColorCodes('&', name);
            player.setDisplayName(nick);
            sendMessage(commandSender, "Successfully set Nickname.");
        } else {
            sendMessage(commandSender, "It can not be set" +
                    "because it exceeds the number of characters that can be set!");
        }
    }

    private void setForceNick(CommandSender commandSender, Player player, String name) {
        if (ChatColor.stripColor(name).length() <= maxLength) {
            String nick = ChatColor.translateAlternateColorCodes('&', name);
            player.setDisplayName(nick);
            sendMessage(commandSender, "Successfully set Nickname.");
        } else {
            sendMessage(commandSender, "It can not be set" +
                    "because it exceeds the number of characters that can be set!");
        }
    }
}
