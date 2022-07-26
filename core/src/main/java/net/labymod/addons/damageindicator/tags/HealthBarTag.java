package net.labymod.addons.damageindicator.tags;

import net.labymod.addons.damageindicator.DamageIndicator;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator health bar tag renderer.
 */
public final class HealthBarTag implements TagRenderer {

  private final DamageIndicator addon;

  private HealthBarTag(DamageIndicator addon) {
    this.addon = addon;
  }

  /**
   * Factory method of the class
   *
   * @param addon the addon
   * @return the damage indicator tag
   */
  public static HealthBarTag create(DamageIndicator addon) {
    return new HealthBarTag(addon);
  }

  @Override
  public void render(Stack stack, LivingEntity entity) {
    RenderPipeline renderPipeline = this.addon.labyAPI().renderPipeline();
    renderPipeline.renderSeeThrough(entity,
        () -> renderPipeline.resourceRenderer().entityHeartRenderer(entity)
            .renderHealthBar(stack, 0, 0, 16));
  }

  @Override
  public boolean isVisible(LivingEntity entity) {
    return this.addon.configuration().isVisible(DisplayType.HEALTH_BAR);
  }

  @Override
  public float getWidth(LivingEntity entity) {
    return this.addon.labyAPI().renderPipeline().resourceRenderer()
        .entityHeartRenderer(entity).getWidth(16);
  }

  @Override
  public float getHeight(LivingEntity entity) {
    return 16F;
  }

  @Override
  public float getScale(LivingEntity entity) {
    return 0.4F;
  }
}
