import com.diffplug.spotless.LineEnding
import net.labymod.labygradle.common.extension.model.labymod.ReleaseChannels

plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
    id("com.diffplug.spotless") version ("8.0.0")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "net.labymod.addons"
version = providers.environmentVariable("VERSION").getOrElse("1.0.0")

labyMod {
    defaultPackageName = "net.labymod.addons.damageindicator" //change this to your main package name (used by all modules)

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    // When the property is set to true, you can log in with a Minecraft account
                    devLogin = true
                }
            }
        }
    }

    addonInfo {
        namespace = "damageindicator"
        displayName = "DamageIndicator"
        author = "LabyMedia GmbH"
        minecraftVersion = "*"
        version = rootProject.version.toString()
        releaseChannel = ReleaseChannels.SNAPSHOT
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")
    plugins.apply("com.diffplug.spotless")

    repositories {
        mavenLocal()
    }

    group = rootProject.group
    version = rootProject.version

    spotless {
        lineEndings = LineEnding.UNIX

        java {
            licenseHeaderFile(rootProject.file("gradle/LICENSE-HEADER.txt"))
        }
    }
}