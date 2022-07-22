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
 * The damage indicator percentage tag renderer.
 */
public class HealthPercentageTag extends NameTag {

  private final DamageIndicator addon;

  private HealthPercentageTag(DamageIndicator addon) {
    this.addon = addon;
  }

  /**
   * Factory method of the class
   *
   * @param labyAddon the addon
   * @return the damage indicator tag
   */
  public static HealthPercentageTag create(DamageIndicator labyAddon) {
    return new HealthPercentageTag(labyAddon);
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
    return this.addon.configuration().isVisible(DisplayType.PERCENT);
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
    int health = (int) Math.ceil(entity.getHealth());
    int maxHealth = (int) Math.ceil(entity.getMaximalHealth());
    return Component.text(health * 100 / maxHealth + "%");
  }
}
