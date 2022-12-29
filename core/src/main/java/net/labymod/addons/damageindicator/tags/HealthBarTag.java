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

import net.labymod.addons.damageindicator.DamageIndicator;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator health bar tag renderer.
 */
public final class HealthBarTag extends AbstractTagRenderer {

  private final DamageIndicator addon;

  private HealthBarTag(DamageIndicator addon) {
    this.addon = addon;
  }

  /**
   * Factory method of the class
   *
   * @param addon the addon
   * @return the damage indicator tag
   */
  public static HealthBarTag create(DamageIndicator addon) {
    return new HealthBarTag(addon);
  }

  @Override
  public void render(Stack stack, Entity entity) {
    RenderPipeline renderPipeline = this.addon.labyAPI().renderPipeline();
    renderPipeline.renderSeeThrough(entity, () -> renderPipeline.resourceRenderer()
        .entityHeartRenderer((LivingEntity) entity).renderHealthBar(stack, 0, 0, 16));
  }

  @Override
  public boolean isVisible() {
    return this.entity instanceof LivingEntity && this.addon.configuration()
        .isVisible(DisplayType.HEALTH_BAR);
  }

  @Override
  public float getWidth() {
    return this.addon.labyAPI().renderPipeline().resourceRenderer()
        .entityHeartRenderer((LivingEntity) this.entity).getWidth(16);
  }

  @Override
  public float getHeight() {
    return 16F;
  }

  @Override
  public float getScale() {
    return 0.4F;
  }
}
