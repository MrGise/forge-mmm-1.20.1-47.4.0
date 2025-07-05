import json
import os

#Name of armor ("Iron" chestplate, "Cobalt" chestplate, etc)
var_ = str(input("Enter specific string name: "))
var_.lower()

#MOD ID
mod_id = str(input("Enter mod id: "))
mod_id.lower()

armor = {
    1: "helmet",
    2: "chestplate",
    3: "leggings",
    4: "boots",
}

trims_json = {
    1: "quartz",
    2: "iron",
    3: "netherite",
    4: "redstone",
    5: "copper",
    6: "gold",
    7: "emerald",
    8: "diamond",
    9: "lapis",
    10: "amethyst"
}

for index in armor:
  file_name = os.path.join("output_folder", f"{var_}_{armor[index]}.json")

  overrides_list = []

  for trim_type in trims_json:
    model_dict = {
        "model":
        f"{mod_id}:item/{var_}_{armor[index]}_{trims_json[trim_type]}_trim",
        "predicate": {
            "minecraft:trim_type": trim_type / 10
        }
    }
    overrides_list.append(model_dict)

  data_armor_type = {
      "parent": "minecraft:item/generated",
      "overrides": overrides_list,
      "textures": {
          "layer0": f"{mod_id}:item/{var_}_{armor[index]}"
      }
  }

  with open(file_name, "w") as file:
    json.dump(data_armor_type, file, indent=2)

  for index2 in trims_json:
    sub_name = os.path.join(
        "output_folder",
        f"{var_}_{armor[index]}_{trims_json[index2]}_trim.json")

    individual_trim = {
        "parent": "minecraft:item/generated",
        "textures": {
            "layer0":
            f"{mod_id}:item/{var_}_{armor[index]}",
            "layer1":
            f"minecraft:trims/items/{armor[index]}_trim_{trims_json[index2]}"
        }
    }

    with open(sub_name, "w") as file2:
      json.dump(individual_trim, file2, indent=2)

  index += 1
