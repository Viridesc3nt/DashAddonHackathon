package me.justinjaques.sidedash;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.util.Vector;

public final class Dash extends ChiAbility implements AddonAbility {
    static ChatColor color = ChatColor.YELLOW;
    private static final String AUTHOR = color + "Viridescent";
    private static final String VERSION = color + "1.0.0";
    private static final String NAME = "Dash";
    private long COOLDOWN;
    static String path = "ExtraAbilities.Viridescent_.Chi.Dash.";
    private Vector direction;
    private double speedX;
    private double speedY;
    private double speedZ;
    private Location location;
    private Permission perm;
    private Listener listener;
    private Location locationOfParticle;
    private int numOfDashes = 4;
    private void setFields() {
        COOLDOWN = ConfigManager.defaultConfig.get().getLong(path+"COOLDOWN");
        speedX = ConfigManager.defaultConfig.get().getDouble(path+"speedX");
        speedY = ConfigManager.defaultConfig.get().getDouble(path+"speedY");
        speedZ = ConfigManager.defaultConfig.get().getDouble(path+"speedZ");
    }

    public Dash(Player player) {
        super(player);
        setFields();
        if(!bPlayer.isOnCooldown(this)) {
            start();
        }
    }
    public void removeWithCooldown() {
        remove();
        bPlayer.addCooldown(this);
    }

    public void onClick() {
        location = player.getEyeLocation();
        locationOfParticle = player.getLocation();
        direction = player.getLocation().getDirection();
        ParticleEffect.CRIT_MAGIC.display(locationOfParticle, 50, direction.getX(), direction.getY(), direction.getZ(), 4);
        player.setVelocity(new Vector(direction.getX() * speedX, direction.getY() * speedY, direction.getZ() * speedZ));
        numOfDashes -= 1;
        return;
    }

    @Override
    public void progress() {
    if(numOfDashes == 4 && player.isOnGround()) {
        onClick();
    }

    if(numOfDashes > 1 && !player.isOnGround()) {
        onClick();
    }

    if(numOfDashes == 0) {
        removeWithCooldown();
    }

}
    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return true;
    }

    @Override
    public long getCooldown() {
        return COOLDOWN;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void load() {
        listener = new DashListener();
        perm = new Permission("bending.ability.Dash");
        ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(listener, ProjectKorra.plugin);
        ConfigManager.defaultConfig.get().addDefault(path+"COOLDOWN", 3000);
        ConfigManager.defaultConfig.get().addDefault(path+"speedX", 2);
        ConfigManager.defaultConfig.get().addDefault(path+"speedY", 2);
        ConfigManager.defaultConfig.get().addDefault(path+"speedZ", 2);


    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(listener);
        ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
    }

    @Override
    public String getAuthor() {
        return AUTHOR;
    }

    @Override
    public String getInstructions() {
        return color + "LEFT-CLICK at a target location, and LEFT-CLICK again while in mid-air";
    }

    @Override
    public String getDescription() {
        return color + "Dash is a ChiBlocking technique that allows it's user to Dash in a certain direction with great speed, and then once more while in the air. This ability provides a great means of engaging and escaping combat, and can be used unpredictably to throw off your opponents";
    }

    @Override
    public String getVersion() {
        return VERSION;
    }
}
