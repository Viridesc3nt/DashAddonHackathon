package me.justinjaques.sidedash;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DashListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
            Dash dash = CoreAbility.getAbility(player, Dash.class);

            if(bPlayer.canBend(CoreAbility.getAbility(Dash.class))) {
                if(dash == null) {
                    System.out.println("Dash created");
                    new Dash(player);
                } else {
                    dash.onClick();
                }
            }
        }
    }
}
