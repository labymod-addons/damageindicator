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
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator percentage tag renderer.
 */
public class HealthPercentageTag extends NameTag {

  private final DamageIndicator addon;

  private HealthPercentageTag(DamageIndicator addon) {
    this.addon = addon;
  }

  /**
   * Factory method of the class
   *
   * @param labyAddon the addon
   * @return the damage indicator tag
   */
  public static HealthPercentageTag create(DamageIndicator labyAddon) {
    return new HealthPercentageTag(labyAddon);
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
                this.getHeight() / 2 - 4,
                8,
                2,
                2
            )
    );
  }

  @Override
  protected RenderableComponent getRenderableComponent() {
    if (!(this.entity instanceof LivingEntity) || !this.addon.configuration()
        .isVisible(DisplayType.PERCENT)) {
      return null;
    }

    return RenderableComponent.of(this.getComponent((LivingEntity) this.entity));
  }

  @Override
  public float getWidth() {
    return super.getWidth() + 9;
  }

  @Override
  public float getScale() {
    return .7F;
  }

  private Component getComponent(LivingEntity entity) {
    int health = (int) Math.ceil(entity.getHealth());
    int maxHealth = (int) Math.ceil(entity.getMaximalHealth());
    return Component.text(health * 100 / maxHealth + "%");
  }
}
