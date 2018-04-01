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
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static tokyo.nafu_at.cgmpx.CGMPX.sendMessage;

/**
 * This command is not used.
 */
public class OPManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        switch (command.getName()) {
            case "op":
                if (string.length == 0) {
                    commandSender.setOp(true);
                    sendMessage(commandSender, "Opped you.");
                } else {
                    Player p = Bukkit.getPlayer(string[0]);
                    if (p != null) {
                        p.setOp(true);
                        sendMessage(p, "Opped you.");
                    } else {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(string[0]);
                        if (op.hasPlayedBefore())
                            op.setOp(true);
                        else
                            return true;
                    }
                    sendMessage(commandSender, "Opped " + string[0]);
                }
                break;

            case "deop":
                if (string.length == 0) {
                    commandSender.setOp(false);
                } else {
                    Player p = Bukkit.getPlayer(string[0]);
                    if (p != null) {
                        if (p.isOp())
                            p.setOp(false);
                    } else {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(string[0]);
                        if (op.hasPlayedBefore() && op.isOp())
                            op.setOp(false);
                        else
                            return true;
                    }
                    sendMessage(commandSender, "De-opped " + string[0]);
                }
                break;
        }
        return true;
    }
}
