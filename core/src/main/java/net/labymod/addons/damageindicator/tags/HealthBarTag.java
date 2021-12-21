package net.labymod.addons.damageindicator.tags;

import net.labymod.addons.damageindicator.DamageIndicatorConfiguration;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator health bar tag renderer.
 */
public final class HealthBarTag implements TagRenderer {

  private final ResourceRenderer resourceRenderer;
  private final DamageIndicatorConfiguration configuration;

  private HealthBarTag(ResourceRenderer resourceRenderer,
      DamageIndicatorConfiguration configuration) {
    this.resourceRenderer = resourceRenderer;
    this.configuration = configuration;
  }

  /**
   * Factory method of the class
   *
   * @param configuration the configuration
   * @return the damage indicator tag
   */
  public static HealthBarTag create(ResourceRenderer resourceRenderer,
      DamageIndicatorConfiguration configuration) {
    return new HealthBarTag(resourceRenderer, configuration);
  }

  @Override
  public void render(Stack stack, Player player) {
    resourceRenderer.getEntityHeartRenderer(player).renderHealthBar(stack, 0, 0, 16);
  }

  @Override
  public boolean isVisible(Player player) {
    return configuration.isVisible(DisplayType.HEALTH_BAR);
  }

  @Override
  public float getWidth(Player player) {
    return resourceRenderer.getEntityHeartRenderer(player).getWidth(16);
  }

  @Override
  public float getHeight(Player player) {
    return 16F;
  }

  @Override
  public float getScale(Player player) {
    return 0.6F;
  }
}
