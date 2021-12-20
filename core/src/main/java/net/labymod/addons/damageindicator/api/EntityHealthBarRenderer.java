package net.labymod.addons.damageindicator.api;


import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The type Health bar renderer.
 */
public class EntityHealthBarRenderer extends HealthRenderer { //TODO Move to Core

  private int currentHealth;

  private EntityHealthBarRenderer(LivingEntity livingEntity) {
    currentHealth = getHealthAsInt(livingEntity.getHealth());
  }

  /**
   * Factory method of the class
   *
   * @param livingEntity the living entity
   * @return the health bar renderer
   */
  public static EntityHealthBarRenderer of(LivingEntity livingEntity) {
    return new EntityHealthBarRenderer(livingEntity);
  }

  /**
   * Render a LivingEntity's health bar with the saved health
   *
   * @param stack the stack
   * @param x     the x
   * @param y     the y
   * @param size  the size
   */
  public void renderHealthBar(Stack stack, float x, float y, int size) { //TODO add absorption
    renderHealthBar(stack, x, y, size, currentHealth, 20);
  }

  /**
   * Update the health with an int. If the new value is different from the old, flashing will be
   * started. Either 3x for health loss or 2x for health gain
   *
   * @param health the health
   */
  public void updateHealth(int health) {
    if (currentHealth != health) {
      startFlashing(currentHealth > health ? 3 : 2);
      currentHealth = health;
    }
  }

  /**
   * Update the health with a float which will then be converted to an integer
   *
   * @param health the health
   */
  public void updateHealth(float health) {
    updateHealth(getHealthAsInt(health));
  }

  /**
   * Update the health with a LivingEntity
   *
   * @param livingEntity the living entity
   */
  public void updateHealth(LivingEntity livingEntity) {
    updateHealth(livingEntity.getHealth());
  }

  /**
   * Gets the width to be displayed without needing to set the max health
   *
   * @param size the size
   * @return the width
   */
  public int getWidth(int size) {
    return getWidth(20, size);
  }

  private int getHealthAsInt(float health) {
    return (int) Math.ceil(health);
  }
}
