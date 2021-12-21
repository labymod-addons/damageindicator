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
 * The damage indicator percentage tag renderer.
 */
public class HealthPercentageTag extends NameTag {

  private final LabyAPI labyAPI;
  private final ResourceRenderer resourceRenderer;
  private final DamageIndicatorConfiguration configuration;

  private HealthPercentageTag(LabyAPI labyAPI, DamageIndicatorConfiguration configuration) {
    this.labyAPI = labyAPI;
    this.configuration = configuration;

    resourceRenderer = labyAPI.getResourceRenderer();
  }

  /**
   * Factory method of the class
   *
   * @param configuration the configuration
   * @return the damage indicator tag
   */
  public static HealthPercentageTag create(LabyAPI labyAPI,
      DamageIndicatorConfiguration configuration) {
    return new HealthPercentageTag(labyAPI, configuration);
  }

  @Override
  public void render(Stack stack, Player player) {
    super.render(stack, player);
    int startX = labyAPI.getComponentRenderer().width(getComponent(player)) + 2;
    resourceRenderer.getEntityHeartRenderer(player).renderHealthBar(stack, startX, 1, 8, 2, 2);
  }

  @Override
  protected RenderableComponent getRenderableComponent(Player player) {
    return RenderableComponent.of(getComponent(player));
  }

  @Override
  public boolean isVisible(Player player) {
    return configuration.isVisible(DisplayType.PERCENT);
  }

  @Override
  public float getWidth(Player player) {
    return super.getWidth(player) + 9;
  }

  private Component getComponent(Player player) {
    int health = (int) Math.ceil(player.getHealth());
    return Component.text(health * 5 + "%");
  }
}
