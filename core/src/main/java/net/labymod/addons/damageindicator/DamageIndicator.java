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

package net.labymod.addons.damageindicator;

import net.labymod.addons.damageindicator.tags.HealthAmountTag;
import net.labymod.addons.damageindicator.tags.HealthBarTag;
import net.labymod.addons.damageindicator.tags.HealthPercentageTag;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.models.addon.annotation.AddonMain;

/**
 * The damage indicator addon main class
 */
@AddonMain
public final class DamageIndicator extends LabyAddon<DamageIndicatorConfiguration> {

  @Override
  public void enable() {
    this.registerSettingCategory();
    this.registerTags();
  }

  @Override
  protected Class<DamageIndicatorConfiguration> configurationClass() {
    return DamageIndicatorConfiguration.class;
  }

  private void registerTags() {
    TagRegistry tagRegistry = this.labyAPI().tagRegistry();
    tagRegistry.register(
        "di_healthbar",
        PositionType.ABOVE_NAME,
        new HealthBarTag(this)
    );

    tagRegistry.register(
        "di_percentage",
        PositionType.ABOVE_NAME,
        new HealthPercentageTag(this)
    );

    tagRegistry.register(
        "di_amount",
        PositionType.ABOVE_NAME,
        new HealthAmountTag(this)
    );
  }
}
