package net.slk.synergy.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.slk.synergy.Synergy;

import java.util.function.Function;

public class SynergyItems {
    // Method to create an item with the provided identifier and register it with the game's item registry.
    public static Item registerItem(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings){
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Synergy.MOD_ID, name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    // Methods to store the item group and a registry key for it, used for a mod-unique creative tab
    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Synergy.MOD_ID, "item_group"));
    public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SynergyItems.CONCENTRATED_ENERGY_JEWEL))
            .displayName(Text.translatable("itemGroup.synergy"))
            .build();

    // Methods to register the individual custom items
    public static final Item CONCENTRATED_ENERGY_JEWEL = registerItem("concentrated_energy_jewel", Item::new, new Item.Settings());
    public static final Item ATTUNEMENT_STONE = registerItem("attunement_stone", Item::new, new Item.Settings());
    public static final Item INSIGNIA_OF_POWER = registerItem("insignia_of_power", Item::new, new Item.Settings());

    // ...

    /** Once an item above have been added, make sure to :
     *  Add the item to the custom item group in the initialize method
     *  Add the translation in src/main/resources/assets/synergy/lang/en_us.json
     *  Add the texture in assets/synergy/textures/item (needs to be a .png with the same item identifier as the file name)
     *  Add the model data in assets/synergy/models/item (needs to be a .json with the same item identifier as the file name)
     *  Add the model description in assets/synergy/items (needs to be a .json with the same item identifier as the file name)
     */

    // Statically initialize the SynergyItems class
    public static void initialize() {
        // Register the group.
        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

        // Register items to the custom item group.
        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(SynergyItems.CONCENTRATED_ENERGY_JEWEL);
            itemGroup.add(SynergyItems.ATTUNEMENT_STONE);
            itemGroup.add(SynergyItems.INSIGNIA_OF_POWER);
            // ...
        });
    }
}