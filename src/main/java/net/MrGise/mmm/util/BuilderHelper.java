package net.MrGise.mmm.util;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.MrGise.mmm.MMM;
import net.MrGise.mmm.block.PortalBlock;
import net.MrGise.mmm.item.description.DescriptionPortalBlockItem;
import net.MrGise.mmm.registry.create.ModCTBehaviour;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

public class BuilderHelper {
    public static <B extends Block> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> emptyBlock(
            Supplier<CTSpriteShiftEntry> ct,
            BlockBehaviour.Properties properties) {
        return b -> b.initialProperties(SharedProperties::stone)
                .properties(p -> properties)
                .blockstate((c, p) -> p.simpleBlock(c.get()))
                .onRegister(connectedTextures(() -> new SimpleCTBehaviour(ct.get())))
                .item()
                .build();
    }

    public static <B extends Block> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> emptyBlockNoodle(
            Supplier<CTSpriteShiftEntry> ct,
            BlockBehaviour.Properties properties) {
        return b -> b.initialProperties(SharedProperties::stone)
                .properties(p -> properties)
                .blockstate((c, p) -> p.simpleBlock(c.get()))
                .onRegister(connectedTextures(() -> new ModCTBehaviour.NoodleCTBehaviour(ct.get())))
                .item()
                .build();
    }

    public static <B extends PortalBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> portalBlock(
            Supplier<CTSpriteShiftEntry> ctOff,
            Supplier<CTSpriteShiftEntry> ctOn,
            BlockBehaviour.Properties properties,
            String eyeName) {
        MMM.LOGGER.warn("Reminder to erase automatic lang files");
        return b -> b.initialProperties(SharedProperties::stone)
                .properties(p -> properties)
                .blockstate((c, p) -> p.getVariantBuilder(c.get()).forAllStates(state -> {
                    if(state.getValue(PortalBlock.EYE)) {
                        return new ConfiguredModel[]{new ConfiguredModel(p.models().cubeAll(c.getName() + "_on",
                                new ResourceLocation(MMM.MOD_ID, "block/" + c.getName() + "_on")))};
                    } else {
                        return new ConfiguredModel[]{new ConfiguredModel(p.models().cubeAll(c.getName() + "_off",
                                new ResourceLocation(MMM.MOD_ID, "block/" + c.getName() + "_off")))};
                    }
                }))
                .onRegister(connectedTextures(() -> new ModCTBehaviour.PortalCTBehaviour(ctOff.get(), ctOn.get())))
                .item((b1, properties1) -> new DescriptionPortalBlockItem(b1, properties1, eyeName))
                .model((context, provider) ->
                        provider.withExistingParent("connecting_portal_block", new ResourceLocation(MMM.MOD_ID, "block/connecting_portal_block_on")))
                .build();
    }

    public static <B extends PortalBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> portalBlockNoodle(
            Supplier<CTSpriteShiftEntry> ctOff,
            Supplier<CTSpriteShiftEntry> ctOn,
            BlockBehaviour.Properties properties,
            String eyeName) {
        MMM.LOGGER.warn("Reminder to erase automatic lang files");
        return b -> b.initialProperties(SharedProperties::stone)
                .properties(p -> properties)
                .blockstate((c, p) -> p.getVariantBuilder(c.get()).forAllStates(state -> {
                    if(state.getValue(PortalBlock.EYE)) {
                        return new ConfiguredModel[]{new ConfiguredModel(p.models().cubeAll(c.getName() + "_on",
                                new ResourceLocation(MMM.MOD_ID, "block/" + c.getName() + "_on")))};
                    } else {
                        return new ConfiguredModel[]{new ConfiguredModel(p.models().cubeAll(c.getName() + "_off",
                                new ResourceLocation(MMM.MOD_ID, "block/" + c.getName() + "_off")))};
                    }
                }))
                .onRegister(connectedTextures(() -> new ModCTBehaviour.NoodlePortalCTBehaviour(ctOff.get(), ctOn.get())))
                .item((b1, properties1) -> new DescriptionPortalBlockItem(b1, properties1, eyeName))
                .model((context, provider) ->
                        provider.withExistingParent("connecting_portal_block", new ResourceLocation(MMM.MOD_ID, "block/connecting_portal_block_on")))
                .build();
    }
}
