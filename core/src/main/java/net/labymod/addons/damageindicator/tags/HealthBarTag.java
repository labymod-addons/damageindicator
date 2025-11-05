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
import net.labymod.addons.damageindicator.snapshot.HealthStatusSnapshot;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer;
import net.labymod.api.client.render.draw.HeartRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.client.render.state.entity.LivingEntitySnapshot;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission.DisplayMode;

/**
 * The damage indicator health bar tag renderer.
 */
public final class HealthBarTag extends AbstractTagRenderer {

  private static final int DEFAULT_SIZE = 16;
  private final HeartRenderer heartRenderer;
  private final DamageIndicatorConfiguration configuration;

  public HealthBarTag(DamageIndicator damageIndicator) {
    this.heartRenderer = Laby.references().heartRenderer();
    this.configuration = damageIndicator.configuration();
  }

  @Override
  public void render(
      Stack stack,
      SubmissionCollector submissionCollector,
      EntitySnapshot snapshot
  ) {
    this.heartRenderer.submitHealthBar(
        stack,
        submissionCollector,
        DisplayMode.NORMAL,
        0, 0,
        DEFAULT_SIZE,
        snapshot.get(DamageIndicatorExtraKeys.HEALTH_STATUS).healthStatus()
    );
  }

  @Override
  public boolean isVisible() {
    return this.snapshot instanceof LivingEntitySnapshot livingSnapshot
        && livingSnapshot.has(DamageIndicatorExtraKeys.HEALTH_STATUS)
        && !livingSnapshot.isDiscrete()
        && this.configuration.isVisible(DisplayType.HEALTH_BAR);
  }

  @Override
  public float getWidth() {
    HealthStatusSnapshot healthStatusSnapshot = this.snapshot.get(
        DamageIndicatorExtraKeys.HEALTH_STATUS
    );
    return this.heartRenderer.getWidth(healthStatusSnapshot.healthStatus(), DEFAULT_SIZE);
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
