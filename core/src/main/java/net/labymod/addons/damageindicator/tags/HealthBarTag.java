package net.labymod.addons.damageindicator.tags;

import net.labymod.addons.damageindicator.DamageIndicatorConfiguration;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator health bar tag renderer.
 */
public final class HealthBarTag implements TagRenderer {

  private final LabyAddon<DamageIndicatorConfiguration> labyAddon;

  private HealthBarTag(LabyAddon<DamageIndicatorConfiguration> labyAddon) {
    this.labyAddon = labyAddon;
  }

  /**
   * Factory method of the class
   *
   * @param labyAddon the addon
   * @return the damage indicator tag
   */
  public static HealthBarTag create(LabyAddon<DamageIndicatorConfiguration> labyAddon) {
    return new HealthBarTag(labyAddon);
  }

  @Override
  public void render(Stack stack, LivingEntity entity) {
    this.labyAddon.labyAPI().renderPipeline().resourceRenderer().getEntityHeartRenderer(entity)
        .renderHealthBar(stack, 0, 0, 16);
  }

  @Override
  public boolean isVisible(LivingEntity entity) {
    return this.labyAddon.configuration().isVisible(DisplayType.HEALTH_BAR);
  }

  @Override
  public float getWidth(LivingEntity entity) {
    return this.labyAddon.labyAPI().renderPipeline().resourceRenderer()
        .getEntityHeartRenderer(entity).getWidth(16);
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
