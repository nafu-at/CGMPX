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

package tokyo.nafu_at.cgmpx;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

import static tokyo.nafu_at.cgmpx.PlayerStatusCache.setPlayerStatus;

public class PlayerStatus {
    private Player player;
    private boolean afk;
    private boolean god;
    private boolean freeze;
    private boolean vanish;
    private Location actionLocation;
    private Map<String, Location> homes;

    protected PlayerStatus(Player player) {
        this.player = player;
        homes = new HashMap<>();
    }

    public boolean isAfk() {
        return afk;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
        setPlayerStatus(player, this);
    }

    public boolean isGod() {
        return god;
    }

    public void setGod(boolean god) {
        this.god = god;
        setPlayerStatus(player, this);
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
        setPlayerStatus(player, this);
    }

    public boolean isVanish() {
        return vanish;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
        setPlayerStatus(player, this);
    }

    public Location getActionLocation() {
        return actionLocation;
    }

    public void setActionLocation(Location actionLocation) {
        this.actionLocation = actionLocation;
        setPlayerStatus(player, this);
    }

    public Map<String, Location> getHomes() {
        return homes;
    }

    public void setHomes(Map<String, Location> homes) {
        this.homes = homes;
        setPlayerStatus(player, this);
    }
}
