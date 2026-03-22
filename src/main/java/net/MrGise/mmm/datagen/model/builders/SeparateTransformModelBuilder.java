package net.MrGise.mmm.datagen.model.builders;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.HashMap;
import java.util.Map;

public class SeparateTransformModelBuilder extends ModelBuilder<SeparateTransformModelBuilder> {

    private ResourceLocation model;
    private final Map<String, String> modelTextures = new HashMap<>();

    private final Map<String, Perspective> perspectives = new HashMap<>();

    private ResourceLocation flatTexture;

    public SeparateTransformModelBuilder(ResourceLocation outLoc, ExistingFileHelper existingFileHelper) {
        super(outLoc, existingFileHelper);
    }

    //| Custom model

    public SeparateTransformModelBuilder base(ResourceLocation parent, ResourceLocation flatTexture) {
        this.model = parent;
        this.flatTexture = flatTexture;
        return this;
    }

    public SeparateTransformModelBuilder baseTexture(String key, String texture) {
        modelTextures.put(key, texture);
        return this;
    }

    public SeparateTransformModelBuilder addTextures(Texture... textures) {
        for (Texture texture : textures) {
            modelTextures.put(texture.key, texture.texture);
        }
        return this;
    }

    public SeparateTransformModelBuilder addTexturesWithPrefix(String prefix, Texture... textures) {
        for (Texture texture : textures) {
            modelTextures.put(texture.key, prefix + texture.texture);
        }
        return this;
    }

    // Perspectives

    public SeparateTransformModelBuilder perspective(String name, ResourceLocation parent, ResourceLocation texture) {
        perspectives.put(name, new Perspective(parent, texture));
        return this;
    }

    public SeparateTransformModelBuilder guiGenerated() {
        return perspective("gui", new ResourceLocation("item/generated"), flatTexture);
    }

    public SeparateTransformModelBuilder groundGenerated() {
        return perspective("ground", new ResourceLocation("item/generated"), flatTexture);
    }

    public SeparateTransformModelBuilder fixedGenerated() {
        return perspective("fixed", new ResourceLocation("item/generated"), flatTexture);
    }

    public SeparateTransformModelBuilder modelInHandBasic() {
        return guiGenerated().groundGenerated().fixedGenerated();
    }

    //| JSON
    @Override
    public JsonObject toJson() {
        if (model == null) {
            throw new IllegalStateException("Base model not set for " + getLocation());
        }
        if (flatTexture == null) {
            throw new IllegalStateException("Flat texture not set for " + getLocation());
        }

        if (!existingFileHelper.exists(model, PackType.CLIENT_RESOURCES, ".json", "models")) {
            throw new IllegalStateException("Base model " + model + " does not exist, required by " + getLocation());
        }

        for (Map.Entry<String, String> entry : modelTextures.entrySet()) {
            ResourceLocation texLoc = new ResourceLocation(entry.getValue());
            if (!existingFileHelper.exists(texLoc, PackType.CLIENT_RESOURCES, ".png", "textures")) {
                throw new IllegalStateException("Texture " + texLoc + " does not exist, required by " + getLocation() + " for key " + entry.getKey());
            }
        }

        if (!existingFileHelper.exists(flatTexture, PackType.CLIENT_RESOURCES, ".png", "textures")) {
            throw new IllegalStateException("Flat texture " + flatTexture + " does not exist, required by " + getLocation());
        }

        for (Map.Entry<String, Perspective> entry : perspectives.entrySet()) {
            ResourceLocation parent = entry.getValue().parent;
            if (!existingFileHelper.exists(parent, PackType.CLIENT_RESOURCES, ".json", "models")) {
                throw new IllegalStateException("Perspective model " + parent + " does not exist, required by " + getLocation());
            }
        }

        JsonObject json = new JsonObject();

        json.addProperty("loader", "forge:separate_transforms");
        json.addProperty("gui_light", "front");

        // base
        JsonObject base = new JsonObject();
        base.addProperty("parent", model.toString());

        if (!modelTextures.isEmpty()) {
            JsonObject textures = new JsonObject();
            modelTextures.forEach(textures::addProperty);
            base.add("textures", textures);
        }

        json.add("base", base);

        // perspectives
        JsonObject perspectivesJson = new JsonObject();

        for (Map.Entry<String, Perspective> entry : perspectives.entrySet()) {
            JsonObject p = new JsonObject();
            p.addProperty("parent", entry.getValue().parent.toString());

            JsonObject textures = new JsonObject();
            textures.addProperty("layer0", entry.getValue().texture.toString());
            p.add("textures", textures);

            perspectivesJson.add(entry.getKey(), p);
        }

        json.add("perspectives", perspectivesJson);

        return json;
    }


    private static class Perspective {
        ResourceLocation parent;
        ResourceLocation texture;

        Perspective(ResourceLocation parent, ResourceLocation texture) {
            this.parent = parent;
            this.texture = texture;
        }
    }

    public record Texture(String key, String texture) {}
}
