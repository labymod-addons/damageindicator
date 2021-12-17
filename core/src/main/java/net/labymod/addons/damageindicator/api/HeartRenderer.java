package net.labymod.addons.damageindicator.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;

/**
 * Heart renderer for simple rendering of the players hearts
 */
public class HeartRenderer { //TODO Add to Core

  private static final ResourceLocation ICONS_TEXTURE;
  private static final Icon EMPTY_HEART;
  private static final Icon FLASHING_HEART;
  private static final Icon HALF_HEART;
  private static final Icon HEART;
  private static final int HEART_SIZE;
  private static final int X_OFFSET;

  static {
    ICONS_TEXTURE = Laby.getLabyAPI().getMinecraft()
        .getResources().getResourceLocationFactory()
        .createMinecraft("textures/gui/icons.png"); //TODO Move to Core
    EMPTY_HEART = Icon.sprite(ICONS_TEXTURE, 16, 0, 9, 9, 256, 256);
    FLASHING_HEART = Icon.sprite(ICONS_TEXTURE, 43, 0, 9, 9, 256, 256);
    HALF_HEART = Icon.sprite(ICONS_TEXTURE, 61, 0, 9, 9, 256, 256);
    HEART = Icon.sprite(ICONS_TEXTURE, 52, 0, 9, 9, 256, 256);
    HEART_SIZE = 16;
    X_OFFSET = 7;
  }

  private final Map<Player, Integer> previousHealth;
  private final Map<Player, Long[][]> flashingHearts;

  @Inject
  private HeartRenderer() {
    previousHealth = new HashMap<>();
    flashingHearts = new HashMap<>();
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
      Player player, boolean renderEmpty) { //TODO Include Absorption Hearts
    int health = (int) Math.ceil(player.getHealth());
    int maxHealth = (int) player.getMaximalHealth();
    Icon backgroundHeart = calculateFlashingHeart(player, health);

    for (int i = 1; i <= maxHealth; i++) {
      if (i % 2 == 0) {
        renderHeart(backgroundHeart, stack, i * X_OFFSET, y);
        if (i <= health) {
          renderHeart(HEART, stack, i * X_OFFSET, y);
        }
      } else if (i == health) {
        renderHeart(backgroundHeart, stack, i * X_OFFSET + X_OFFSET, y);
        renderHeart(HALF_HEART, stack, i * X_OFFSET + X_OFFSET, y);

        i++;
      }
    }
  }

  /**
   * Render a single heart
   *
   * @param stack    the stack
   * @param x        the x coordinate
   * @param y        the y coordinate
   * @param player   the player
   * @param flashing flashing enabled
   */
  public void renderSingleHeart(Stack stack, int x, int y, Player player, boolean flashing) {
    Icon backgroundHeart = EMPTY_HEART;
    if (flashing) {
      int health = (int) Math.ceil(player.getHealth());
      backgroundHeart = calculateFlashingHeart(player, health);
    }

    renderHeart(backgroundHeart, stack, x, y);
    renderHeart(HEART, stack, x, y);
  }

  private void renderHeart(Icon heart, Stack stack, int x, int y) {
    heart.render(stack, x, y, HEART_SIZE);
  }

  private Icon calculateFlashingHeart(Player player,
      int health) {
    int prevHealth = previousHealth.computeIfAbsent(player, absent -> health);
    previousHealth.put(player, health);
    Long[][] flashing = flashingHearts.get(player);
    if (health == prevHealth && Objects.isNull(flashing)) {
      return EMPTY_HEART;
    }

    if (health != prevHealth) {
      int flashingTimes = health < prevHealth ? 3 : 2;
      flashingHearts.put(player, calculateFlashing(flashingTimes));
      return FLASHING_HEART;
    }

    long currentTime = System.currentTimeMillis();
    for (Long[] flashingValues : flashing) {
      //     if (flashingValues == flashing[flashing.length - 1]) {
      //      flashingHearts.remove(player);
      //     System.out.println("flashing complete @ " + System.currentTimeMillis());
      //   }

      if (flashingValues[0] < currentTime && flashingValues[1] > currentTime) {
        return FLASHING_HEART;
      }
    }

    return EMPTY_HEART;
  }

  private Long[][] calculateFlashing(int times) { //TODO time the flashing right
    Long[][] flashing = new Long[times][];
    for (int i = 0; i < times; i++) {
      if (i == 0) {
        long currentTime = System.currentTimeMillis();
        flashing[i] = new Long[]{currentTime, currentTime + 200};
      } else {
        long lastValue = flashing[i - 1][1];
        flashing[i] = new Long[]{lastValue + 100, lastValue + 300};
      }
    }

    return flashing;
  }
}

