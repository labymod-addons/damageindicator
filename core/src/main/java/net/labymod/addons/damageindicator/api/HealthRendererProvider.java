package net.labymod.addons.damageindicator.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent.Phase;
import net.labymod.api.event.client.world.WorldLeaveEvent;

/**
 * The type Health renderer provider.
 */
@Singleton
public class HealthRendererProvider { //TODO Move to Core

  private static final Map<UUID, EntityHealthBarRenderer> HEALTH_RENDERERS = new HashMap<>();

  @Inject
  private HealthRendererProvider() {
  }

  /**
   * Factory method to create and/or get a EntityHealthBarRenderer linked to a LivingEntity
   *
   * @param livingEntity the living entity
   * @return the living entity health bar renderer
   */
  public static EntityHealthBarRenderer of(LivingEntity livingEntity) {
    return HEALTH_RENDERERS.computeIfAbsent(livingEntity.getUniqueId(),
        absent -> EntityHealthBarRenderer.of(livingEntity));
  }

  /**
   * Factory method to create a new HealthRenderer object
   *
   * @return the health renderer
   */
  public static HealthRenderer create() {
    return HealthRenderer.create();
  }

  /**
   * Update EntityHealthBarRenderer entities health on tick
   *
   * @param event the event
   */
  @Subscribe
  public void onGameTick(GameTickEvent event) {
    if (event.getPhase() != Phase.PRE) {
      return;
    }

    for (Player player : Laby.getLabyAPI().getMinecraft().getClientWorld()
        .getPlayers()) { //TODO change to entity
      EntityHealthBarRenderer healthBarRenderer = HEALTH_RENDERERS.get(player.getUniqueId());
      if (Objects.nonNull(healthBarRenderer)) {
        healthBarRenderer.updateHealth(player);
      }
    }
  }

  /**
   * Clear the EntityHealthBarRenderer map on world leave
   *
   * @param event the event
   */
  @Subscribe
  public void onWorldLeave(WorldLeaveEvent event) {
    HEALTH_RENDERERS.clear();
  }
}
