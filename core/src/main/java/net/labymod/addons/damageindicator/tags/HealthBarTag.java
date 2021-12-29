package net.labymod.addons.damageindicator.tags;

import net.labymod.addons.damageindicator.DamageIndicatorConfiguration;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.client.entity.LivingEntity;
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
  public void render(Stack stack, LivingEntity entity) {
    this.resourceRenderer.getEntityHeartRenderer(entity).renderHealthBar(stack, 0, 0, 16);
  }

  @Override
  public boolean isVisible(LivingEntity entity) {
    return this.configuration.isVisible(DisplayType.HEALTH_BAR);
  }

  @Override
  public float getWidth(LivingEntity entity) {
    return this.resourceRenderer.getEntityHeartRenderer(entity).getWidth(16);
  }

  @Override
  public float getHeight(LivingEntity entity) {
    return 16F;
  }

  @Override
  public float getScale(LivingEntity entity) {
    return 0.6F;
  }
}
