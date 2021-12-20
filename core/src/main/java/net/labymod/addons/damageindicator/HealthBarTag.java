package net.labymod.addons.damageindicator;

import net.labymod.addons.damageindicator.api.HealthRendererProvider;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator tag renderer.
 */
final class HealthBarTag implements TagRenderer {

  private final DamageIndicatorConfiguration configuration;

  private HealthBarTag(DamageIndicatorConfiguration configuration) {
    this.configuration = configuration;
  }

  /**
   * Factory method of the class
   *
   * @param configuration the configuration
   * @return the damage indicator tag
   */
  public static HealthBarTag create(DamageIndicatorConfiguration configuration) {
    return new HealthBarTag(configuration);
  }

  @Override
  public void render(Stack stack, Player player) { //TODO Implement Configuration
    HealthRendererProvider.of(player).renderHealthBar(stack, 0, 0, 16);
  }

  @Override
  public boolean isVisible(Player player) {
    return true;
  }

  @Override
  public float getWidth(Player player) {
    return HealthRendererProvider.of(player).getWidth(16);
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
