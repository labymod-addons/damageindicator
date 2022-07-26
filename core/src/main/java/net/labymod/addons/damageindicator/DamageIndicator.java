package net.labymod.addons.damageindicator;

import com.google.inject.Singleton;
import net.labymod.addons.damageindicator.tags.HealthAmountTag;
import net.labymod.addons.damageindicator.tags.HealthBarTag;
import net.labymod.addons.damageindicator.tags.HealthPercentageTag;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.models.addon.annotation.AddonListener;

/**
 * The damage indicator addon main class
 */
@AddonListener
@Singleton
public final class DamageIndicator extends LabyAddon<DamageIndicatorConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();
    this.registerTags();
  }

  @Override
  protected Class<DamageIndicatorConfiguration> configurationClass() {
    return DamageIndicatorConfiguration.class;
  }

  private void registerTags() {
    TagRegistry tagRegistry = this.labyAPI().tagRegistry();
    tagRegistry.register("di_healthbar", PositionType.ABOVE_NAME, HealthBarTag.create(this));
    tagRegistry.register("di_percentage", PositionType.ABOVE_NAME,
        HealthPercentageTag.create(this));
    tagRegistry.register("di_amount", PositionType.ABOVE_NAME, HealthAmountTag.create(this));
  }
}
