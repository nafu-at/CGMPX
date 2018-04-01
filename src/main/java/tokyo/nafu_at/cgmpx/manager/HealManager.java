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
import org.bukkit.potion.PotionEffect;
import tokyo.nafu_at.cgmpx.CGMPX;

import static tokyo.nafu_at.cgmpx.CGMPX.sendMessage;

public class HealManager implements CommandExecutor {
    private final boolean clearAllEffect = CGMPX.getInstance().getPluginConfig().isClearAllEffect();
    private final boolean toBeFull = CGMPX.getInstance().getPluginConfig().isToBeFull();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] string) {
        if (string.length == 0) {
            if (commandSender.hasPermission("cgmpx.heal")) {
                Player target = (Player) commandSender;
                healPlayer(target);
                sendMessage(commandSender, "Heal succeeded.");
            } else {
                sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
            }
        } else {
            if (commandSender.hasPermission("cgmpx.admin.heal")) {
                Player target = Bukkit.getPlayer(string[0]);
                if (target != null) {
                    healPlayer(target);
                    sendMessage(commandSender, "Heal succeeded.");
                } else {
                    sendMessage(commandSender, ChatColor.RED + "Player Not Found.");
                }
            } else {
                sendMessage(commandSender, ChatColor.RED + "You don't have Permission.");
            }
        }
        return true;
    }

    private void healPlayer(Player player) {
        player.setHealth(20.0);
        player.setFireTicks(0);
        if (clearAllEffect) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
        if (toBeFull) {
            player.setFoodLevel(20);
            player.setSaturation((float) 20.0);
        }
    }
}
