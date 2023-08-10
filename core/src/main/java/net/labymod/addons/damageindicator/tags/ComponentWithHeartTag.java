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
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;

public abstract class ComponentWithHeartTag extends NameTag {

  private final RenderPipeline renderPipeline;
  private final ResourceRenderer resourceRenderer;
  private final DamageIndicatorConfiguration configuration;
  private final DisplayType displayType;
  private float startX;

  protected ComponentWithHeartTag(DamageIndicator damageIndicator, DisplayType displayType) {
    this.renderPipeline = damageIndicator.labyAPI().renderPipeline();
    this.resourceRenderer = this.renderPipeline.resourceRenderer();
    this.configuration = damageIndicator.configuration();
    this.displayType = displayType;
  }

  @Override
  public void render(Stack stack, Entity entity) {
    RenderPipeline renderPipeline = this.renderPipeline;
    renderPipeline.renderSeeThrough(entity, 0.0F, () -> {
          super.render(stack, entity);
          LivingEntity livingEntity = (LivingEntity) entity;
          this.resourceRenderer.entityHeartRenderer(livingEntity).renderHealthBar(
              stack,
              this.startX,
              this.getHeight() / 2 - 4,
              8,
              2,
              2
          );
        }
    );
  }

  @Override
  protected RenderableComponent getRenderableComponent() {
    if (!(this.entity instanceof LivingEntity) || this.entity.isCrouching()
        || !this.configuration.isVisible(this.displayType)) {
      return null;
    }

    RenderableComponent renderableComponent = RenderableComponent.of(
        this.component((LivingEntity) this.entity)
    );

    this.startX = renderableComponent.getWidth() + 2;
    return renderableComponent;
  }

  @Override
  public float getWidth() {
    return super.getWidth() + 9;
  }

  @Override
  public float getScale() {
    return .7F;
  }

  protected abstract Component component(LivingEntity livingEntity);
}
