package net.labymod.addons.damageindicator.api;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;

/**
 * Heart renderer for simple rendering of the players hearts
 */
@Singleton
public class HeartRenderer {

  private static final Map<Player, Integer> PREVIOUS_HEALTH;
  private static final Map<Player, Long> FLASHING_HEARTS;

  private static final ResourceLocation ICONS_TEXTURE;
  private static final Icon EMPTY_HEART;
  private static final Icon FLASHING_HEART;
  private static final Icon HALF_HEART;
  private static final Icon HEART;

  static {
    PREVIOUS_HEALTH = new HashMap<>();
    FLASHING_HEARTS = new HashMap<>();

    ICONS_TEXTURE = Laby.getLabyAPI().getMinecraft()
        .getResources().getResourceLocationFactory().createMinecraft("textures/gui/icons.png");
    EMPTY_HEART = Icon.sprite(ICONS_TEXTURE, 16, 0, 9, 9, 256, 256);
    FLASHING_HEART = Icon.sprite(ICONS_TEXTURE, 43, 0, 9, 9, 256, 256);
    HALF_HEART = Icon.sprite(ICONS_TEXTURE, 61, 0, 9, 9, 256, 256);
    HEART = Icon.sprite(ICONS_TEXTURE, 52, 0, 9, 9, 256, 256);
  }

  @Inject
  private HeartRenderer() {
  }

  /**
   * Render a players hearts
   *
   * @param stack       the stack
   * @param x           the x coordinate
   * @param y           the y coordinate
   * @param player      the player
   * @param renderEmpty rendering of empty hearts
   */
  public void render(Stack stack, int x, int y, Player player, boolean renderEmpty) {
    render(stack, x, y, 10, player, renderEmpty);
  }

  /**
   * Render a players hearts
   *
   * @param stack       the stack
   * @param x           the x coordinate
   * @param y           the y coordinate
   * @param maxHearts   the max hearts
   * @param player      the player
   * @param renderEmpty rendering of empty hearts
   */
  public void render(Stack stack, int x, int y, int maxHearts,
      Player player, boolean renderEmpty) { //TODO Finish flashing
    int health = (int) Math.ceil(player.getHealth());
    int maxHealth = (int) player.getMaximalHealth();
    int previousHealth = PREVIOUS_HEALTH.computeIfAbsent(player, absent -> health);

    Icon background = EMPTY_HEART;
    if (previousHealth != health) {
      background = FLASHING_HEART;
    }

    for (int i = 1; i <= maxHealth; i++) {
      if (i % 2 == 0) {
        background.render(stack, i * 7, y, 16);
        if (i <= health) {
          HEART.render(stack, i * 7, y, 16);
        }
      } else if (i == health) {
        background.render(stack, i * 7 + 7, y, 16);
        HALF_HEART.render(stack, i * 7 + 7, y, 16);

        i++;
      }
    }

    PREVIOUS_HEALTH.put(player, health);
  }
}

