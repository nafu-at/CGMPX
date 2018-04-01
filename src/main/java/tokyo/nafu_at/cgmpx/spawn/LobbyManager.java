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

public class LobbyManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (string.length == 1) {
                if (commandSender.hasPermission("cgmpx.lobby.set")) {
                    SpawnDataManager.saveLobby(player.getLocation());
                    sendMessage(commandSender, "Register this location as a lobby location.");
                } else
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
            } else {
                if (commandSender.hasPermission("cgmpx.lobby"))
                    safeTeleport(player, SpawnDataManager.getLobby());
                else
                    sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
            }
        }
        return true;
    }
}
