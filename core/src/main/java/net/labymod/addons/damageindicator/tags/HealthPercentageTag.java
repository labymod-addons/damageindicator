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
import net.labymod.addons.damageindicator.snapshot.DamageIndicatorExtraKeys;
import net.labymod.addons.damageindicator.snapshot.HealthStatusSnapshot;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.state.entity.LivingEntitySnapshot;
import net.labymod.api.util.HealthStatus;

/**
 * The damage indicator percentage tag renderer.
 */
public class HealthPercentageTag extends ComponentWithHeartTag {

  public HealthPercentageTag(DamageIndicator damageIndicator) {
    super(damageIndicator, DisplayType.PERCENT);
  }

  @Override
  protected Component createComponent(LivingEntitySnapshot snapshot) {
    HealthStatusSnapshot healthStatusSnapshot = snapshot.get(
        DamageIndicatorExtraKeys.HEALTH_STATUS
    );

    HealthStatus healthStatus = healthStatusSnapshot.healthStatus();
    int health = (int) Math.ceil(healthStatus.getHealth());
    int maxHealth = (int) Math.ceil(healthStatus.getMaxHealth());
    return Component.text(health * 100 / maxHealth + "%");
  }

}
