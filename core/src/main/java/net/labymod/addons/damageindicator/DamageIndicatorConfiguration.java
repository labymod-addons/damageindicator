package net.labymod.addons.damageindicator;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * The damage indicator configuration.
 */
@ConfigName("settings")
public final class DamageIndicatorConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @DropdownSetting
  private final ConfigProperty<DisplayType> displayType = new ConfigProperty<>(
      DisplayType.HEALTH_BAR);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<DisplayType> displayType() {
    return this.displayType;
  }

  public boolean isVisible(DisplayType value) {
    return this.enabled().get() && this.displayType.get() == value;
  }

  public enum DisplayType {
    HEALTH_BAR, AMOUNT, PERCENT
  }
}
