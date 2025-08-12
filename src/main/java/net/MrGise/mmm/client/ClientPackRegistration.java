package net.MrGise.mmm.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import net.MrGise.mmm.MMM;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientPackRegistration {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;

        Path modFilePath = ModList.get().getModFileById(MMM.MOD_ID).getFile().getFilePath();
        Path resourcePacksDir = modFilePath.resolve("resourcepacks");

        if (!Files.exists(resourcePacksDir) || !Files.isDirectory(resourcePacksDir)) {
            LOGGER.debug("No built-in resourcepacks folder found for mod {}", MMM.MOD_ID);
            return;
        }

        try (Stream<Path> packFolders = Files.list(resourcePacksDir)) {
            packFolders.filter(Files::isDirectory).forEach(packFolder -> {
                String folderName = packFolder.getFileName().toString();
                Component displayName = getPackDisplayName(packFolder, folderName);

                event.addRepositorySource(packConsumer -> {
                    try {
                        packConsumer.accept(Pack.readMetaAndCreate(
                                new ResourceLocation(MMM.MOD_ID, folderName).toString(),    // ID
                                displayName,    // Display name from pack.mcmeta or folder name
                                false,  // Default enabled
                                id -> new PathPackResources(id, packFolder, false), // Loader
                                PackType.CLIENT_RESOURCES,
                                Pack.Position.TOP,  // Default TOP so it overrides
                                PackSource.BUILT_IN
                        ));
                        LOGGER.info("Registered built-in resource pack: {}", folderName);
                    } catch (Exception e) {
                        LOGGER.error("Failed to register built-in resource pack: {}", folderName, e);
                    }
                });
            });
        } catch (IOException e) {
            LOGGER.error("Error reading built-in resource packs directory for {}", MMM.MOD_ID, e);
        }
    }

    private static Component getPackDisplayName(Path packFolder, String fallbackName) {
        Path mcmetaPath = packFolder.resolve("pack.mcmeta");
        if (Files.exists(mcmetaPath)) {
            try {
                String json = Files.readString(mcmetaPath);
                JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                if (obj.has("pack")) {
                    JsonObject packObj = obj.getAsJsonObject("pack");

                    // Read your custom field here instead of description
                    if (packObj.has("displayname")) {
                        return Component.literal(packObj.get("displayname").getAsString());
                    }
                }
            } catch (IOException e) {
                LOGGER.warn("Could not read pack.mcmeta for built-in pack: {}", packFolder, e);
            }
        }
        return Component.literal(fallbackName);
    }

}
