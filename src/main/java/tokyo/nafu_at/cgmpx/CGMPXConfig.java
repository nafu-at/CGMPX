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

import org.bukkit.configuration.file.FileConfiguration;

public class CGMPXConfig {
    private FileConfiguration config;

    private String OPsNameColor;
    private String NickCommandPrefix;
    private int NickMaxLength;
    private boolean useSafeTeleport;

    private boolean clearAllEffect;
    private boolean toBeFull;

    private int afkTimeInterval;
    private boolean useGodMode;
    private boolean useAntiPush;

    private boolean enableTabPrefix;
    private boolean useDisplayName;

    private boolean multiWorldSpawn;
    private boolean enableFirstJoinSpawn;
    private boolean joinFixedSpawn;
    private boolean respawnToWorldSpawn;
    private boolean forcedRespawnToWorldSpawn;

    private int maxHomePoint;

    private String playerAFKMessage;
    private String playerAFKBackMessage;

    public CGMPXConfig() {
        CGMPX.getInstance().getLogger();
        config = CGMPX.getInstance().getConfig();
    }

    public void reloadConfig() {
        CGMPX.getInstance().reloadConfig();

        OPsNameColor = config.getString("OPsNameColor", "none");
        NickCommandPrefix = config.getString("NickCommandPrefix", "~");
        NickMaxLength = config.getInt("NickMaxLength", 0);
        useSafeTeleport = config.getBoolean("useSafeTeleport", true);

        clearAllEffect = config.getBoolean("clearAllEffect", false);
        toBeFull = config.getBoolean("toBeFull", true);

        afkTimeInterval = config.getInt("afkTimeInterval", 0);
        useGodMode = config.getBoolean("useGodMode", true);
        useAntiPush = config.getBoolean("useAntiPush", true);

        enableTabPrefix = config.getBoolean("enableTabPrefix", false);
        useDisplayName = config.getBoolean("useDisplayName", true);

        multiWorldSpawn = config.getBoolean("multiWorldSpawn", true);
        enableFirstJoinSpawn = config.getBoolean("enableFirstJoinSpawn", false);
        joinFixedSpawn = config.getBoolean("joinFixedSpawn", false);
        respawnToWorldSpawn = config.getBoolean("respawnToWorldSpawn", true);
        forcedRespawnToWorldSpawn = config.getBoolean("forcedRespawnToWorldSpawn", false);

        maxHomePoint = config.getInt("maxHomePoint", 0);

        playerAFKMessage = config.getString("playerAFKMessage");
        playerAFKBackMessage = config.getString("playerAFKBackMessage");
    }

    public String getOPsNameColor() {
        return OPsNameColor;
    }

    public String getNickCommandPrefix() {
        return NickCommandPrefix;
    }

    public int getNickMaxLength() {
        return NickMaxLength;
    }

    public boolean isUseSafeTeleport() {
        return useSafeTeleport;
    }

    public boolean isClearAllEffect() {
        return clearAllEffect;
    }

    public boolean isToBeFull() {
        return toBeFull;
    }

    public int getAfkTimeInterval() {
        return afkTimeInterval;
    }

    public boolean isUseGodMode() {
        return useGodMode;
    }

    public boolean isUseAntiPush() {
        return useAntiPush;
    }

    public boolean isEnableTabPrefix() {
        return enableTabPrefix;
    }

    public boolean isUseDisplayName() {
        return useDisplayName;
    }

    public boolean isMultiWorldSpanwn() {
        return multiWorldSpawn;
    }

    public boolean isEnableFirstJoinSpawn() {
        return enableFirstJoinSpawn;
    }

    public boolean isJoinFixedSpawn() {
        return joinFixedSpawn;
    }

    public boolean isRespawnToWorldSpawn() {
        return respawnToWorldSpawn;
    }

    public boolean isForcedRespawnToWorldSpawn() {
        return forcedRespawnToWorldSpawn;
    }

    public int getMaxHomePoint() {
        return maxHomePoint;
    }

    public String getPlayerAFKMessage() {
        return playerAFKMessage;
    }

    public String getPlayerAFKBackMessage() {
        return playerAFKBackMessage;
    }
}
