package net.MrGise.mmm.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.MrGise.mmm.block.entity.BowlBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.MrGise.floating.helper.Methods.mmm;

public class BowlRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack result;
    private final ResourceLocation id;

    private final FluidIngredient requiredFluid;
    private final int craftLength;

    public BowlRecipe(NonNullList<Ingredient> inputItems, ItemStack result, ResourceLocation id, @Nullable FluidIngredient requiredFluid, int craftLength) {
        this.inputItems = inputItems;
        this.result = result;
        this.id = id;
        this.requiredFluid = requiredFluid;
        this.craftLength = craftLength;
    }


    public boolean matches(List<ItemStack> bowlItems, FluidStack bowlFluid) {
        List<ItemStack> remaining = new ArrayList<>();

        for (ItemStack stack : bowlItems) {
            remaining.add(stack.copy());
        }

        for (Ingredient ingredient : inputItems) {
            boolean found = false;

            for (ItemStack stack : remaining) {
                if (!stack.isEmpty() && ingredient.test(stack)) {
                    stack.shrink(1);
                    found = true;
                    break;
                }
            }

            if (!found) return false;
        }

        // Check fluid requirement
        if (this.requiredFluid != null && !this.requiredFluid.equals(FluidIngredient.EMPTY)) {
            if (bowlFluid.isEmpty()) return false;

            return this.requiredFluid.test(bowlFluid);
        }
        return true;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        // Convert SimpleContainer to a list and pass an empty fluid so this
        // path works if something in vanilla/Forge ever calls it directly.
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) items.add(stack);
        }
        return matches(items, FluidStack.EMPTY);
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }


    public FluidIngredient getRequiredFluid() {
        return this.requiredFluid == null ? FluidIngredient.EMPTY : this.requiredFluid;
    }

    public boolean requiresFluid() {
        return this.requiredFluid != null && !this.requiredFluid.equals(FluidIngredient.EMPTY);
    }


    public int getCraftLength() {
        return this.craftLength;
    }

    
    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BowlRecipe> {
        private Type() {}
        public static final BowlRecipe.Type INSTANCE = new BowlRecipe.Type();
        public static final String ID = "bowl";
    }

    public static class Serializer implements RecipeSerializer<BowlRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                mmm("bowl");

        @Override
        public BowlRecipe fromJson(ResourceLocation id, JsonObject json) {
            FluidIngredient fluidIngredient = json.has("fluid") ? FluidIngredient.deserialize(json.get("fluid"))
                    : FluidIngredient.EMPTY;

            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.create();

            for (JsonElement el : ingredients) {
                inputs.add(Ingredient.fromJson(el));
            }

            int ingredientWeight = inputs.stream().mapToInt(ingredient -> {
                int max = 0;
                for (ItemStack stack : ingredient.getItems()) {
                    max = Math.max(max, BowlBlockEntity.getSingleWeight(stack));
                }
                return max;
            }).sum();
            if (ingredientWeight > BowlBlockEntity.MAX_WEIGHT) {
                throw new JsonSyntaxException("Invalid ingredient maximum weight of " + ingredientWeight + " for " + ingredients);
            }

            int craftLength = GsonHelper.getAsInt(json, "craft_length");

            return new BowlRecipe(inputs, result, id, fluidIngredient, craftLength);
        }

        @Override
        public BowlRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            FluidIngredient fluidIngredient = FluidIngredient.read(buf);

            NonNullList<Ingredient> inputs = NonNullList.create();

            int size = buf.readVarInt();
            for (int i = 0; i < size; i++) {
                inputs.add(Ingredient.fromNetwork(buf));
            }

            ItemStack result = buf.readItem();

            int craftLength = buf.readVarInt();

            return new BowlRecipe(inputs, result, id, fluidIngredient, craftLength);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, BowlRecipe recipe) {
            recipe.getRequiredFluid().write(buf);

            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }

            buf.writeItem(recipe.result.copy());

            buf.writeVarInt(recipe.getCraftLength());
        }
    }
}
