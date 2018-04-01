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
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static tokyo.nafu_at.cgmpx.CGMPX.sendMessage;

public class GamemodeManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (commandSender instanceof Player) {
            if (string.length == 0) {
                if (((Player) commandSender).getGameMode().equals(GameMode.SURVIVAL)) {
                    ((Player) commandSender).setGameMode(GameMode.CREATIVE);
                    sendMessage(commandSender, "Successfully changed game mode.");
                } else {
                    ((Player) commandSender).setGameMode(GameMode.SURVIVAL);
                    sendMessage(commandSender, "Successfully changed game mode.");
                }
            } else if (string.length == 2) {
                Player target = Bukkit.getPlayer(string[1]);
                if (target != null) {
                    switch (string[0].toLowerCase()) {
                        case "creative":
                        case "c":
                        case "1":
                            target.setGameMode(GameMode.CREATIVE);
                            sendMessage(commandSender, "Successfully changed game mode.");
                            break;

                        case "survival":
                        case "s":
                        case "0":
                        default:
                            target.setGameMode(GameMode.SURVIVAL);
                            sendMessage(commandSender, "Successfully changed game mode.");
                            break;

                        case "spectator":
                        case "sp":
                        case "3":
                            target.setGameMode(GameMode.SPECTATOR);
                            sendMessage(commandSender, "Successfully changed game mode.");
                            break;

                        case "adventure":
                        case "a":
                        case "2":
                            target.setGameMode(GameMode.ADVENTURE);
                            sendMessage(commandSender, "Successfully changed game mode.");
                            break;
                    }
                } else {
                    sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                }
            } else switch (string[0].toLowerCase()) {
                case "creative":
                case "c":
                case "1":
                    ((Player) commandSender).setGameMode(GameMode.CREATIVE);
                    sendMessage(commandSender, "Successfully changed game mode.");
                    break;

                case "survival":
                case "s":
                case "0":
                default:
                    ((Player) commandSender).setGameMode(GameMode.SURVIVAL);
                    sendMessage(commandSender, "Successfully changed game mode.");
                    break;

                case "spectator":
                case "sp":
                case "3":
                    ((Player) commandSender).setGameMode(GameMode.SPECTATOR);
                    sendMessage(commandSender, "Successfully changed game mode.");
                    break;

                case "adventure":
                case "a":
                case "2":
                    ((Player) commandSender).setGameMode(GameMode.ADVENTURE);
                    sendMessage(commandSender, "Successfully changed game mode.");
                    break;
            }
        }
        return true;
    }
}
