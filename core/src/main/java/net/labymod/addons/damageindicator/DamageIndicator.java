package net.labymod.addons.damageindicator;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.addons.damageindicator.api.HealthRendererProvider;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.configuration.loader.ConfigurationLoader;
import net.labymod.api.configuration.settings.SettingsRegistry;
import net.labymod.api.configuration.settings.gui.SettingCategoryRegistry;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Priority;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameInitializeEvent;
import net.labymod.api.event.client.lifecycle.GameInitializeEvent.Lifecycle;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.api.models.addon.annotation.AddonMain;

/**
 * The damage indicator addon main class
 */
@AddonMain
@Singleton
public final class DamageIndicator {

  private final LabyAPI labyAPI;
  private final SettingCategoryRegistry categoryRegistry;

  private DamageIndicatorConfiguration configuration;

  @Inject
  private DamageIndicator(LabyAPI labyAPI, EventBus eventBus,
      SettingCategoryRegistry categoryRegistry) {
    this.labyAPI = labyAPI;
    this.categoryRegistry = categoryRegistry;

    eventBus.registerListener(this, HealthRendererProvider.class); //TODO Move to Core
  }

  /**
   * On game initialize.
   *
   * @param event the event
   */
  @Subscribe(Priority.LATEST)
  public void onGameInitialize(GameInitializeEvent event) {
    if (event.getLifecycle() != Lifecycle.POST_STARTUP) {
      return;
    }

    ConfigurationLoader configurationLoader = labyAPI.getConfigurationLoader();
    try {
      configuration = configurationLoader.load(DamageIndicatorConfiguration.class);
      SettingsRegistry registry = configuration.initializeRegistry();
      categoryRegistry.register(registry.getId(), registry);
    } catch (Exception e) {
      e.printStackTrace();
    }

    TagRegistry tagRegistry = labyAPI.getNameTagService();
    tagRegistry.register("damageindicator", PositionType.ABOVE_NAME,
        HealthBarTag.create(configuration));
  }

  /**
   * On configuration save.
   *
   * @param event the event
   */
  @Subscribe
  public void onConfigurationSave(ConfigurationSaveEvent event) {
    try {
      event.getLoader().save(this.configuration);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
