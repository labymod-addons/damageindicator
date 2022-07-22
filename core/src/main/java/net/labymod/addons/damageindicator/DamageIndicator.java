package net.labymod.addons.damageindicator;

import com.google.inject.Singleton;
import java.util.function.Consumer;
import net.labymod.addons.damageindicator.tags.HealthAmountTag;
import net.labymod.addons.damageindicator.tags.HealthBarTag;
import net.labymod.addons.damageindicator.tags.HealthPercentageTag;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.vertex.shard.RenderShards;
import net.labymod.api.loader.platform.PlatformEnvironment;
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

  public void fixDepth(LivingEntity livingEntity, Consumer<ResourceRenderer> consumer) {
    if (!livingEntity.isCrouching()) {
      RenderShards.LEQUAL_DEPTH_TEST.setupShared();
      consumer.accept(this.labyAPI().renderPipeline().resourceRenderer());
      RenderShards.LEQUAL_DEPTH_TEST.finishShared();
    }

    Laby.labyAPI().renderPipeline().multiplyAlpha(0.5F,
        () -> consumer.accept(this.labyAPI().renderPipeline().resourceRenderer()));

    if (PlatformEnvironment.isAncientOpenGL()) {
      RenderShards.LEQUAL_DEPTH_TEST.setupShared();
    }
  }

  private void registerTags() {
    TagRegistry tagRegistry = this.labyAPI().tagRegistry();
    tagRegistry.register("di_healthbar", PositionType.ABOVE_NAME, HealthBarTag.create(this));
    tagRegistry.register("di_percentage", PositionType.ABOVE_NAME,
        HealthPercentageTag.create(this));
    tagRegistry.register("di_amount", PositionType.ABOVE_NAME, HealthAmountTag.create(this));
  }
}
