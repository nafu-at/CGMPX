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

package tokyo.nafu_at.cgmpx.timer;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tokyo.nafu_at.cgmpx.CGMPX;
import tokyo.nafu_at.cgmpx.PlayerStatusCache;

public class TabUpdater implements Runnable {
    private final Chat vault = CGMPX.getInstance().getVault();
    private final String opNameColor = CGMPX.getInstance().getPluginConfig().getOPsNameColor();
    private final boolean displayName = CGMPX.getInstance().getPluginConfig().isUseDisplayName();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String prefix = "";
            String suffix = "";
            if (vault != null) {
                prefix = vault.getPlayerPrefix(player);
                suffix = vault.getPlayerSuffix(player);
            }

            if (PlayerStatusCache.getPlayerStatus(player).isAfk())
                suffix = "&7[&9AFK&7]&f";
            else if (player.getAllowFlight())
                suffix = "&7[&aFly&7]&f";

            String tabName;
            if (displayName) {
                if (player.isOp())
                    tabName = prefix + opNameColor + player.getDisplayName() + suffix;
                else
                    tabName = prefix + player.getDisplayName() + suffix;
            } else {
                if (player.isOp())
                    tabName = prefix + opNameColor + player.getName() + suffix;
                else
                    tabName = prefix + player.getName() + suffix;
            }
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', tabName));
        }
    }
}
