plugins {
    id("java")
    id("com.github.johnrengelman.shadow") apply false
    id("io.papermc.paperweight.userdev") version "1.3.8"
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")

    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:23.0.0")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.1")
    compileOnly("com.ticxo.modelengine:api:R3.0.1")
    compileOnly("com.github.oraxen:oraxen:-SNAPSHOT")
    compileOnly("com.github.LoneDev6:API-ItemsAdder:3.2.5") // TODO Work on this

    //compileOnly("com.github.Fisher2911:FisherLib:master-SNAPSHOT")
    implementation("net.kyori:adventure-api:4.11.0")
    implementation ("net.kyori:adventure-text-minimessage:4.11.0")
    implementation("net.kyori:adventure-platform-bukkit:4.1.2")
    implementation("dev.triumphteam:triumph-gui:3.1.3")
    implementation("org.spongepowered:configurate-yaml:4.1.2")
    implementation("org.bstats:bstats-bukkit:3.0.0")
}

tasks {
    build {
        dependsOn(reobfJar)
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17
    ))
}