package net.labymod.addons.damageindicator.api;

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

  private static final ResourceLocation ICONS_TEXTURE;
  private static final Icon EMPTY_HEART;
  private static final Icon FLASHING_HEART;
  private static final Icon HALF_HEART;
  private static final Icon HEART;

  static {
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
      Player player, boolean renderEmpty) { //TODO Finish
    float health = player.getHealth();
    float maxHealth = player.getMaximalHealth();

    for (double i = 0; i < maxHealth; i++) {
      EMPTY_HEART.render(stack, (float) (i * 16), y, 16);
      if (health >= i) {
        HEART.render(stack, (float) (i * 16), y, 16);
      }

      // HALF_HEART.render(stack, (float) (i*16), y, 16);
    }
  }
}
