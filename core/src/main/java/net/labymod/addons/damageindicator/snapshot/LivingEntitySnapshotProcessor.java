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
package net.labymod.addons.damageindicator.snapshot;

import net.labymod.addons.damageindicator.DamageIndicator;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.state.entity.EntitySnapshotProcessor;
import net.labymod.api.client.render.state.entity.EntitySnapshotRegistry;
import net.labymod.api.laby3d.renderer.snapshot.ExtrasWriter;
import net.labymod.api.service.annotation.AutoService;

@AutoService(EntitySnapshotProcessor.class)
public class LivingEntitySnapshotProcessor extends EntitySnapshotProcessor<LivingEntity> {

  public LivingEntitySnapshotProcessor(EntitySnapshotRegistry registry) {
    super(registry);
  }

  @Override
  public boolean supports(Entity entity) {
    return entity instanceof LivingEntity livingEntity && !livingEntity.entityId().equals(
        DamageIndicator.ARMOR_STAND
    );
  }

  @Override
  public void process(LivingEntity player, float partialTicks, ExtrasWriter entityWriter) {
    this.registry().captureSnapshot(entityWriter, DamageIndicatorExtraKeys.HEALTH_STATUS, player);
  }
}
