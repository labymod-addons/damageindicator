package net.labymod.addons.damageindicator;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ConfigName;

/**
 * The damage indicator configuration.
 */
@ConfigName("settings")
@SuppressWarnings("FieldMayBeFinal")
public final class DamageIndicatorConfiguration extends Config {

  @SwitchSetting
  private boolean enabled = true;

  @DropdownSetting
  private DisplayType displayType = DisplayType.HEALTH_BAR;

  public boolean isEnabled() {
    return this.enabled;
  }

  public DisplayType getDisplayType() {
    return this.displayType;
  }

  public boolean isVisible(DisplayType value) {
    return this.isEnabled() && this.displayType == value;
  }

  public enum DisplayType {
    HEALTH_BAR, AMOUNT, PERCENT
  }
}
