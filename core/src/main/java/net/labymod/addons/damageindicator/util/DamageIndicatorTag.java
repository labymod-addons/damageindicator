package net.labymod.addons.damageindicator.util;

import java.util.HashMap;
import java.util.Map;
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
public class DamageIndicatorTag implements TagRenderer {

  private final LabyAPI labyAPI;
  private final ComponentRenderer componentRenderer;
  private final DamageIndicatorConfiguration configuration;
  private final HeartRenderer heartRenderer;

  private final Map<Player, Float> previousHearts;
  private final Map<Player, Long> flashingHearts;

  private DamageIndicatorTag(LabyAPI labyAPI, DamageIndicatorConfiguration configuration) {
    this.labyAPI = labyAPI;
    this.configuration = configuration;

    componentRenderer = labyAPI.getComponentRenderer();
    heartRenderer = labyAPI.getInjected(HeartRenderer.class);
    previousHearts = new HashMap<>();
    flashingHearts = new HashMap<>();
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
    //componentRenderer.renderComponent(stack, getRenderableComponent(player), 0, 0, 0, true);
    heartRenderer.render(stack, 0, 0, 10, player, true);

  }

  @Override
  public boolean isVisible(Player player) {
    return true;
  }

  @Override
  public float getWidth(Player player) {
    return 24.0F;
  }

  @Override
  public float getHeight(Player player) {
    return 8.0F;
  }

  @Override
  public float getScale(Player player) {
    return 0.8F;
  }

  private RenderableComponent getRenderableComponent(Player player) {
    String text = "";

    //AMOUNT
//    double health = player.getHealth() / 2D;
    //   text = String.valueOf(health);

    //PERCENTAGE
    double percentage = player.getHealth() * 5D;
    text = percentage + "%";

    return RenderableComponent.of(Component.text(text, NamedTextColor.WHITE));
  }
}
