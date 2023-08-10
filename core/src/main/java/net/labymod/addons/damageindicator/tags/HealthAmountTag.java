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
import net.labymod.api.client.entity.LivingEntity;

/**
 * The damage indicator amount tag renderer.
 */
public class HealthAmountTag extends ComponentWithHeartTag {

  public HealthAmountTag(DamageIndicator damageIndicator) {
    super(damageIndicator, DisplayType.AMOUNT);
  }

  @Override
  protected Component component(LivingEntity entity) {
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