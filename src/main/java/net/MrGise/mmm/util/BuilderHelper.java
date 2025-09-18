package net.MrGise.mmm.util;

import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

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
}
