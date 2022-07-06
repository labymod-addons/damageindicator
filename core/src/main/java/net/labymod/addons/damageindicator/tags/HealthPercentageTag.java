package net.labymod.addons.damageindicator.tags;

import net.kyori.adventure.text.Component;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.EntityHeartRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator percentage tag renderer.
 */
public class HealthPercentageTag extends NameTag {

  private final LabyAddon<DamageIndicatorConfiguration> labyAddon;

  private HealthPercentageTag(LabyAddon<DamageIndicatorConfiguration> labyAddon) {
    this.labyAddon = labyAddon;
  }

  /**
   * Factory method of the class
   *
   * @param labyAddon the addon
   * @return the damage indicator tag
   */
  public static HealthPercentageTag create(LabyAddon<DamageIndicatorConfiguration> labyAddon) {
    return new HealthPercentageTag(labyAddon);
  }

  @Override
  public void render(Stack stack, LivingEntity entity) {
    super.render(stack, entity);
    RenderPipeline renderPipeline = this.labyAddon.labyAPI().renderPipeline();

    int startX = renderPipeline.componentRenderer().width(this.getComponent(entity)) + 2;
    EntityHeartRenderer heartRenderer = renderPipeline.resourceRenderer()
        .getEntityHeartRenderer(entity);
    heartRenderer.renderHealthBar(stack, startX, 1, 8, 2, 2);
  }

  @Override
  protected RenderableComponent getRenderableComponent(LivingEntity entity) {
    return RenderableComponent.of(this.getComponent(entity));
  }

  @Override
  public boolean isVisible(LivingEntity entity) {
    return this.labyAddon.configuration().isVisible(DisplayType.PERCENT);
  }

  @Override
  public float getWidth(LivingEntity entity) {
    return super.getWidth(entity) + 9;
  }

  private Component getComponent(LivingEntity entity) {
    int health = (int) Math.ceil(entity.getHealth());
    int maxHealth = (int) Math.ceil(entity.getMaximalHealth());
    return Component.text(health * 100 / maxHealth + "%");
  }
}
