package net.MrGise.mmm.event;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.MrGise.mmm.MMM;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockTouchTrigger extends SimpleCriterionTrigger<BlockTouchTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(MMM.MOD_ID, "block_touch");

    private enum TouchType {
        IN, ON,
        NORTH, SOUTH, EAST, WEST;

        public String getJsonKey() {
            return getName() + "_block";
        }

        public String getName() {
            return this.name().toLowerCase();
        }

        private static TouchType parseType(JsonObject object) {
            for (TouchType type : TouchType.values()) {
                if (object.has(type.getJsonKey())) {
                    return type;
                }
            }
            return TouchType.IN; // default
        }
    }

    public ResourceLocation getId() {
        return ID;
    }

    public TriggerInstance createInstance(JsonObject object, ContextAwarePredicate predicate, DeserializationContext context) {
        Block block = deserializeBlock(object);
        StatePropertiesPredicate properties = StatePropertiesPredicate.fromJson(object.get("state"));
        if (block != null) {
            properties.checkState(block.getStateDefinition(), (string) -> {
                throw new JsonSyntaxException("Block " + block + " has no property " + string);
            });
        }
        TouchType type = TouchType.parseType(object);
        return new TriggerInstance(predicate, block, properties, type);
    }

    @Nullable
    private static Block deserializeBlock(JsonObject object) {
        if (object.has("block")) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(object, "block"));
            return BuiltInRegistries.BLOCK.getOptional(resourcelocation).orElseThrow(() -> {
                return new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
            });
        } else {
            return null;
        }
    }

    public void trigger(ServerPlayer player, BlockPos pos) {
        this.trigger(player, (instance) -> {
            return instance.matches(pos, player);
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        @Nullable
        private final Block block;
        private final StatePropertiesPredicate state;
        private final TouchType type;

        public TriggerInstance(ContextAwarePredicate context, @Nullable Block block, StatePropertiesPredicate properties, TouchType type) {
            super(BlockTouchTrigger.ID, context);
            this.block = block;
            this.state = properties;
            this.type = type;
        }

        public static TriggerInstance inBlock(Block block) {
            return new TriggerInstance(ContextAwarePredicate.ANY, block, StatePropertiesPredicate.ANY, TouchType.IN);
        }

        public static TriggerInstance onBlock(Block block) {
            return new TriggerInstance(ContextAwarePredicate.ANY, block, StatePropertiesPredicate.ANY, TouchType.ON);
        }

        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonobject = super.serializeToJson(context);
            if (this.block != null) {
                jsonobject.addProperty("block", BuiltInRegistries.BLOCK.getKey(this.block).toString());
            }

            jsonobject.add("state", this.state.serializeToJson());

            jsonobject.addProperty(this.type.getJsonKey(), true);

            return jsonobject;
        }

        public boolean matches(BlockPos pos, Player player) {
            Level level = player.level();
            BlockState state = switch (this.type) {
                case IN -> level.getBlockState(pos);
                case ON -> level.getBlockState(pos.below());
                case NORTH -> level.getBlockState(pos.north());
                case SOUTH -> level.getBlockState(pos.south());
                case EAST -> level.getBlockState(pos.east());
                case WEST -> level.getBlockState(pos.west());
            };
            if (this.block != null && !state.is(this.block)) {
                return false;
            } else {
                return this.state.matches(state);
            }
        }
    }
}
