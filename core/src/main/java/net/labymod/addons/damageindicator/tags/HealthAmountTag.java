package net.labymod.addons.damageindicator.tags;

import net.kyori.adventure.text.Component;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.Player;
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
  public void render(Stack stack, Player player) {
    super.render(stack, player);
    int startX = this.labyAPI.getComponentRenderer().width(this.getComponent(player)) + 2;
    this.resourceRenderer.getEntityHeartRenderer(player).renderHealthBar(stack, startX, 1, 8, 2, 2);
  }

  @Override
  protected RenderableComponent getRenderableComponent(Player player) {
    return RenderableComponent.of(this.getComponent(player));
  }

  @Override
  public boolean isVisible(Player player) {
    return this.configuration.isVisible(DisplayType.AMOUNT);
  }

  @Override
  public float getWidth(Player player) {
    return super.getWidth(player) + 9;
  }

  private Component getComponent(Player player) {
    double health = Math.ceil(player.getHealth()) / 2;
    double maxHealth = Math.ceil(player.getMaximalHealth()) / 2;
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