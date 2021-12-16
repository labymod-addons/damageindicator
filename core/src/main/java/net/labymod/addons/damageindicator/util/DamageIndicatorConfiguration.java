package net.labymod.addons.damageindicator.util;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.impl.AddonConfig;

/**
 * The damage indicator configuration.
 */
@ConfigName("settings")
public class DamageIndicatorConfiguration extends AddonConfig { //TODO Finish Configuration

  @SwitchSetting
  private final boolean enabled = true;

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
