package net.labymod.addons.damageindicator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.labymod.addons.damageindicator.api.HeartRenderer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator tag renderer.
 */
final class DamageIndicatorTag implements TagRenderer {

  private final LabyAPI labyAPI;
  private final ComponentRenderer componentRenderer;
  private final DamageIndicatorConfiguration configuration;
  private final HeartRenderer heartRenderer;

  private DamageIndicatorTag(LabyAPI labyAPI, DamageIndicatorConfiguration configuration) {
    this.labyAPI = labyAPI;
    this.configuration = configuration;
    componentRenderer = labyAPI.getComponentRenderer();
    heartRenderer = labyAPI.getInjected(HeartRenderer.class);
  }

  /**
   * Factory method of the class
   *
   * @param labyAPI       the laby api
   * @param configuration the configuration
   * @return the damage indicator tag
   */
  public static DamageIndicatorTag create(LabyAPI labyAPI,
      DamageIndicatorConfiguration configuration) {
    return new DamageIndicatorTag(labyAPI, configuration);
  }

  @Override
  public void render(Stack stack, Player player) { //TODO Implement Configuration
    int health = (int) Math.ceil(player.getHealth());
    //componentRenderer.renderComponent(stack, getRenderableComponent(player, health), 0, 0, 0, true);
    //heartRenderer.renderSingleHeart(stack, 25, 0, player, health, true);
    heartRenderer.render(stack, 0, 0, player, health);
  }

  @Override
  public boolean isVisible(Player player) {
    return true;
  }

  @Override
  public float getWidth(Player player) {
    return 30F;
  }

  @Override
  public float getHeight(Player player) {
    return 16.0F;
  }

  @Override
  public float getScale(Player player) {
    return 0.8F;
  }

  private RenderableComponent getRenderableComponent(Player player, int health) {
    String text = "";

    //AMOUNT
//    double health = player.getHealth() / 2D;
    //   text = String.valueOf(health);

    //PERCENTAGE
    int percentage = health * 5;
    text = percentage + "%";

    return RenderableComponent.of(Component.text(text, NamedTextColor.WHITE));
  }
}
