package net.labymod.addons.damageindicator.tags;

import net.kyori.adventure.text.Component;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator amount tag renderer.
 */
public class HealthAmountTag extends NameTag {

  private final LabyAPI labyAPI;
  private final ResourceRenderer resourceRenderer;
  private final DamageIndicatorConfiguration configuration;

  private HealthAmountTag(LabyAPI labyAPI, DamageIndicatorConfiguration configuration) {
    this.labyAPI = labyAPI;
    this.configuration = configuration;

    this.resourceRenderer = labyAPI.getResourceRenderer();
  }

  /**
   * Factory method of the class
   *
   * @param configuration the configuration
   * @return the damage indicator tag
   */
  public static HealthAmountTag create(LabyAPI labyAPI,
      DamageIndicatorConfiguration configuration) {
    return new HealthAmountTag(labyAPI, configuration);
  }

  @Override
  public void render(Stack stack, LivingEntity entity) {
    super.render(stack, entity);
    int startX = this.labyAPI.getComponentRenderer().width(this.getComponent(entity)) + 2;
    this.resourceRenderer.getEntityHeartRenderer(entity).renderHealthBar(stack, startX, 1, 8, 2, 2);
  }

  @Override
  protected RenderableComponent getRenderableComponent(LivingEntity entity) {
    return RenderableComponent.of(this.getComponent(entity));
  }

  @Override
  public boolean isVisible(LivingEntity entity) {
    return this.configuration.isVisible(DisplayType.AMOUNT);
  }

  @Override
  public float getWidth(LivingEntity entity) {
    return super.getWidth(entity) + 9;
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