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
import net.labymod.addons.damageindicator.snapshot.DamageIndicatorExtraKeys;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.tags.ComponentNameTag;
import net.labymod.api.client.render.draw.HeartRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.client.render.state.entity.LivingEntitySnapshot;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.render.queue.CustomGeometryRenderer;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission;
import net.labymod.api.util.HealthStatus;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public abstract class ComponentWithHeartTag extends ComponentNameTag {

  private static final HealthStatus DEFAULT_STATUS = HealthStatus.immutable(2, 2);
  private static final float DEFAULT_BACKGROUND_DEPTH = -0.003F;
  private final HeartRenderer heartRenderer;
  private final DamageIndicatorConfiguration configuration;
  private final DisplayType displayType;

  protected ComponentWithHeartTag(DamageIndicator damageIndicator, DisplayType displayType) {
    this.displayType = displayType;
    this.heartRenderer = Laby.references().heartRenderer();
    this.configuration = damageIndicator.configuration();
  }

  @Override
  public void render(
      Stack stack,
      SubmissionCollector submissionCollector,
      EntitySnapshot snapshot
  ) {
    submissionCollector.submitCustomGeometry(
        stack,
        RenderStates.GUI,
        new ColoredRectangle(
            0, 0, this.getWidth() + 2.0F, this.getHeight(),
            DEFAULT_BACKGROUND_DEPTH,
            super.getBackgroundColor(snapshot)
        )
    );
    super.render(stack, submissionCollector, snapshot);
    this.heartRenderer.submitHealthBar(
        stack,
        submissionCollector,
        IconSubmission.DisplayMode.NORMAL,
        this.getWidth() - 9.0F + 2.0F, this.getHeight() / 2 - 4,
        8,
        DEFAULT_STATUS
    );
  }

  @Override
  protected @NotNull List<Component> buildComponents(EntitySnapshot snapshot) {
    if (!(snapshot instanceof LivingEntitySnapshot livingEntitySnapshot)
        || snapshot.isDiscrete()
        || !this.configuration.isVisible(this.displayType)) {
      return super.buildComponents(snapshot);
    }

    if (!snapshot.has(DamageIndicatorExtraKeys.HEALTH_STATUS)) {
      return super.buildComponents(snapshot);
    }

    return Collections.singletonList(this.createComponent(livingEntitySnapshot));
  }

  @Override
  public float getWidth() {
    return super.getWidth() + 9;
  }

  @Override
  public float getScale() {
    return 0.7F;
  }

  @Override
  protected int getBackgroundColor(EntitySnapshot snapshot) {
    return 0;
  }

  protected abstract Component createComponent(LivingEntitySnapshot snapshot);

  static class ColoredRectangle implements CustomGeometryRenderer {

    private final float left;
    private final float top;
    private final float right;
    private final float bottom;
    private final float depth;
    private final int argb;

    public ColoredRectangle(
        float left, float top, float right, float bottom,
        float depth,
        int argb
    ) {
      this.left = left;
      this.top = top;
      this.right = right;
      this.bottom = bottom;
      this.depth = depth;
      this.argb = argb;
    }

    @Override
    public void render(Matrix4f pose, VertexConsumer consumer) {
      consumer.addVertex(pose, this.left, this.top, this.depth).setBlankUv().setColor(this.argb);
      consumer.addVertex(pose, this.left, this.bottom, this.depth).setBlankUv().setColor(this.argb);
      consumer.addVertex(pose, this.right, this.bottom, this.depth).setBlankUv()
          .setColor(this.argb);
      consumer.addVertex(pose, this.right, this.top, this.depth).setBlankUv().setColor(this.argb);
    }
  }
}
