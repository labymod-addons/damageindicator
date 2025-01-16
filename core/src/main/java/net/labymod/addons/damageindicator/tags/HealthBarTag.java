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
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration;
import net.labymod.addons.damageindicator.DamageIndicatorConfiguration.DisplayType;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.matrix.Stack;

/**
 * The damage indicator health bar tag renderer.
 */
public final class HealthBarTag extends AbstractTagRenderer {

  private final RenderPipeline renderPipeline;
  private final ResourceRenderer resourceRenderer;
  private final DamageIndicatorConfiguration configuration;

  public HealthBarTag(DamageIndicator damageIndicator) {
    this.renderPipeline = damageIndicator.labyAPI().renderPipeline();
    this.resourceRenderer = this.renderPipeline.resourceRenderer();
    this.configuration = damageIndicator.configuration();
  }

  @Override
  public void render(Stack stack, Entity entity) {
    RenderPipeline renderPipeline = this.renderPipeline;
    renderPipeline.renderNoneStandardNameTag(entity, () ->
        this.resourceRenderer.entityHeartRenderer((LivingEntity) entity)
            .renderHealthBar(stack, 0, 0, 16)
    );
  }

  @Override
  public boolean isVisible() {
    return this.entity instanceof LivingEntity &&
        !this.entity.isCrouching() &&
        this.configuration.isVisible(DisplayType.HEALTH_BAR) &&
        !this.entity.entityId().equals(DamageIndicator.ARMOR_STAND);
  }

  @Override
  public float getWidth() {
    return this.resourceRenderer.entityHeartRenderer((LivingEntity) this.entity).getWidth(16);
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
