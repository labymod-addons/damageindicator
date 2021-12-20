package net.labymod.addons.damageindicator.api;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;

/**
 * Heart renderer for simple rendering of the players hearts
 */
public class HealthRenderer { //TODO Move to Core

  private static final ResourceLocation ICONS_TEXTURE = Laby.getLabyAPI().getMinecraft()
      .getResources().getResourceLocationFactory()
      .createMinecraft("textures/gui/icons.png"); //TODO Move to Core
  private static final Icon EMPTY_HEART = Icon.sprite(ICONS_TEXTURE, 16, 0, 9, 9, 256, 256);
  private static final Icon FLASHING_HEART = Icon.sprite(ICONS_TEXTURE, 43, 0, 9, 9, 256, 256);
  private static final Icon HALF_HEART = Icon.sprite(ICONS_TEXTURE, 61, 0, 9, 9, 256, 256);
  private static final Icon HEART = Icon.sprite(ICONS_TEXTURE, 52, 0, 9, 9, 256, 256);
  private static final long FLASHING_DELAY = 100;
  private static final long FLASHING_TIME = 185;

  private long flashingStartTime;
  private int flashingTimes;

  protected HealthRenderer() {
  }

  /**
   * Factory method of the class
   *
   * @return the health renderer
   */
  public static HealthRenderer create() {
    return new HealthRenderer();
  }

  /**
   * Render a complete health bar
   *
   * @param stack     the stack
   * @param startX    the start x
   * @param y         the y
   * @param size      the size
   * @param health    the health
   * @param maxHealth the max health
   */
  public void renderHealthBar(Stack stack, float startX, float y, int size, int health,
      int maxHealth) { //TODO add absorption
    renderBackgroundHearts(stack, startX, y, size, maxHealth / 2);
    int actualSize = getActualSize(size);
    for (int i = 1; i <= maxHealth; i++) {
      if (i % 2 == 0) {
        if (i <= health) {
          renderSingleFullHeart(stack, startX + actualSize / 2F * (i - 2), y, size);
        }
      } else if (i == health) {
        renderSingleHalfHeart(stack, startX + actualSize / 2F * (i - 1), y, size);
      }
    }
  }

  /**
   * Render either a single empty or flashing heart
   *
   * @param stack the stack
   * @param x     the x
   * @param y     the y
   * @param size  the size
   */
  public void renderSingleBackgroundHeart(Stack stack, float x, float y, int size) {
    renderEmptyHearts(stack, x, y, size, 1);
  }

  /**
   * Render either empty or flashing hearts
   *
   * @param stack       the stack
   * @param startX      the start x
   * @param y           the y
   * @param size        the size
   * @param heartAmount the heart amount
   */
  public void renderBackgroundHearts(Stack stack, float startX, float y, int size,
      int heartAmount) {
    int actualSize = getActualSize(size);
    Icon backgroundHeart = isCurrentlyFlashing() ? FLASHING_HEART : EMPTY_HEART;
    for (int i = 0; i < heartAmount; i++) {
      backgroundHeart.render(stack, startX + i * actualSize, y, size);
    }
  }

  /**
   * Render a single empty heart.
   *
   * @param stack the stack
   * @param x     the x
   * @param y     the y
   * @param size  the size
   */
  public void renderSingleEmptyHeart(Stack stack, float x, float y, int size) {
    EMPTY_HEART.render(stack, x, y, size);
  }

  /**
   * Render empty hearts.
   *
   * @param stack       the stack
   * @param startX      the start x
   * @param y           the y
   * @param size        the size
   * @param heartAmount the heart amount
   */
  public void renderEmptyHearts(Stack stack, float startX, float y, int size, int heartAmount) {
    int actualSize = getActualSize(size);
    for (int i = 0; i < heartAmount; i++) {
      renderSingleEmptyHeart(stack, startX + i * actualSize, y, size);
    }
  }

  /**
   * Render a single flashing heart.
   *
   * @param stack the stack
   * @param x     the x
   * @param y     the y
   * @param size  the size
   */
  public void renderSingleFlashingHeart(Stack stack, float x, float y, int size) {
    FLASHING_HEART.render(stack, x, y, size);
  }

  /**
   * Render flashing hearts.
   *
   * @param stack       the stack
   * @param startX      the start x
   * @param y           the y
   * @param size        the size
   * @param heartAmount the heart amount
   */
  public void renderFlashingHearts(Stack stack, float startX, float y, int size, int heartAmount) {
    int actualSize = getActualSize(size);
    for (int i = 0; i < heartAmount; i++) {
      renderSingleFlashingHeart(stack, startX + i * actualSize, y, size);
    }
  }

  /**
   * Render a single half heart.
   *
   * @param stack the stack
   * @param x     the x
   * @param y     the y
   * @param size  the size
   */
  public void renderSingleHalfHeart(Stack stack, float x, float y, int size) {
    HALF_HEART.render(stack, x, y, size);
  }

  /**
   * Render half hearts.
   *
   * @param stack       the stack
   * @param startX      the start x
   * @param y           the y
   * @param size        the size
   * @param heartAmount the heart amount
   */
  public void renderHalfHearts(Stack stack, float startX, float y, int size, int heartAmount) {
    int actualSize = getActualSize(size);
    for (int i = 0; i < heartAmount; i++) {
      renderSingleHalfHeart(stack, startX + i * actualSize, y, size);
    }
  }

  /**
   * Render  asingle full heart.
   *
   * @param stack the stack
   * @param x     the x
   * @param y     the y
   * @param size  the size
   */
  public void renderSingleFullHeart(Stack stack, float x, float y, int size) {
    HEART.render(stack, x, y, size);
  }

  /**
   * Render full hearts.
   *
   * @param stack       the stack
   * @param startX      the start x
   * @param y           the y
   * @param size        the size
   * @param heartAmount the heart amount
   */
  public void renderFullHearts(Stack stack, float startX, float y, int size, int heartAmount) {
    int actualSize = getActualSize(size);
    for (int i = 0; i < heartAmount; i++) {
      renderSingleFullHeart(stack, startX + i * actualSize, y, size);
    }
  }

  /**
   * Start flashing
   *
   * @param times the amount of how many times the hearts will flash
   */
  public void startFlashing(int times) {
    flashingTimes = times;
    flashingStartTime = System.currentTimeMillis();
  }

  /**
   * Stop flashing
   */
  public void stopFlashing() {
    flashingTimes = 0;
  }

  /**
   * Check if there is currently a flashing period
   *
   * @return the boolean
   */
  public boolean isFlashing() {
    return flashingTimes != 0;
  }

  /**
   * Calculate if the hearts are currently flashing
   *
   * @return the boolean
   */
  public boolean isCurrentlyFlashing() {
    long currentTime = System.currentTimeMillis();
    if (!isFlashing() || flashingStartTime > currentTime) {
      return false;
    }

    if (flashingStartTime < currentTime) {
      long flashingEndTime = flashingStartTime + FLASHING_TIME;
      if (flashingEndTime > currentTime) {
        return true;
      } else {
        flashingTimes--;
        flashingStartTime = currentTime + FLASHING_DELAY;
      }
    }

    return false;
  }

  /**
   * Gets the width to be displayed
   *
   * @param hearts the hearts
   * @param size   the size
   * @return the width
   */
  public int getWidth(int hearts, int size) {
    return hearts * getActualSize(size) / 2;
  }

  private int getActualSize(int size) {
    return (int) (size * 0.875);
  }
}

