package net.labymod.addons.damageindicator.tags;

import net.kyori.adventure.text.Component;
import net.labymod.addons.damageindicator.DamageIndicator;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator amount tag renderer.
 */
public class HealthAmountTag extends NameTag {

  private final DamageIndicator addon;

  private HealthAmountTag(DamageIndicator addon) {
    this.addon = addon;
  }

  /**
   * Factory method of the class
   *
   * @param addon the addon
   * @return the damage indicator tag
   */
  public static HealthAmountTag create(DamageIndicator addon) {
    return new HealthAmountTag(addon);
  }

  @Override
  public void render(Stack stack, LivingEntity entity) {
    super.render(stack, entity);
    RenderPipeline renderPipeline = this.addon.labyAPI().renderPipeline();

    int startX = renderPipeline.componentRenderer().width(this.getComponent(entity)) + 2;
    this.addon.fixDepth(entity, resourceRenderer -> resourceRenderer.entityHeartRenderer(entity)
        .renderHealthBar(stack, startX, this.getHeight(entity) / 2 - 4, 8, 2, 2));
  }

  @Override
  protected RenderableComponent renderableComponent(LivingEntity entity) {
    return RenderableComponent.of(this.getComponent(entity));
  }

  @Override
  public boolean isVisible(LivingEntity entity) {
    return this.addon.configuration().isVisible(DisplayType.AMOUNT);
  }

  @Override
  public float getWidth(LivingEntity entity) {
    return super.getWidth(entity) + 9;
  }

  @Override
  public float getScale(LivingEntity livingEntity) {
    return .7F;
  }

  private Component getComponent(LivingEntity entity) {
    double health = Math.ceil(entity.getHealth()) / 2;
    double maxHealth = Math.ceil(entity.getMaximalHealth()) / 2;
    return Component.text(this.formatDouble(health) + "/" + this.formatDouble(maxHealth));
  }

  private String formatDouble(double value) {
    if (value == (int) value) {
      return String.format("%d", (int) value);
    } else {
      return String.format("%s", value);
    }
  }
}