/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.labymod.addons.damageindicator.tags;

import net.kyori.adventure.text.Component;
import net.labymod.addons.damageindicator.DamageIndicator;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator amount tag renderer.
 */
public class HealthAmountTag extends NameTag {

  private final DamageIndicator addon;

  private HealthAmountTag(DamageIndicator addon) {
    this.addon = addon;
  }

  /**
   * Factory method of the class
   *
   * @param addon the addon
   * @return the damage indicator tag
   */
  public static HealthAmountTag create(DamageIndicator addon) {
    return new HealthAmountTag(addon);
  }

  @Override
  public void render(Stack stack, Entity entity) {
    super.render(stack, entity);
    RenderPipeline renderPipeline = this.addon.labyAPI().renderPipeline();
    LivingEntity livingEntity = (LivingEntity) entity;
    float startX = renderPipeline.componentRenderer().width(this.getComponent(livingEntity)) + 2;
    renderPipeline.renderSeeThrough(entity,
        () -> renderPipeline.resourceRenderer()
            .entityHeartRenderer(livingEntity).renderHealthBar(
                stack,
                startX,
                this.getHeight(entity) / 2 - 4,
                8,
                2,
                2
            )
    );
  }

  @Override
  protected RenderableComponent renderableComponent(Entity entity) {
    return RenderableComponent.of(this.getComponent((LivingEntity) entity));
  }

  @Override
  public boolean isVisible(Entity entity) {
    return entity instanceof LivingEntity && this.addon.configuration()
        .isVisible(DisplayType.AMOUNT);
  }

  @Override
  public float getWidth(Entity entity) {
    return super.getWidth(entity) + 9;
  }

  @Override
  public float getScale(Entity livingEntity) {
    return .7F;
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