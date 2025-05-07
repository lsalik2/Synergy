package net.slk.synergy.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.slk.synergy.Synergy;
import net.slk.synergy.item.SynergyItems;

import java.util.function.Function;

public class SynergyBlocks {
    // Method to create a block with the provided identifier and register it with the game's block registry.
    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem){
        // Create a registry key for the block and the block instance
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = keyOfItem(name);
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name){
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Synergy.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name){
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Synergy.MOD_ID, name));
    }

    // Methods to register the individual custom items
    public static final Block CONCENTRATED_ENERGY_JEWEL_BLOCK = registerBlock( // TODO add mapColor
            "concentrated_energy_jewel_block",
            Block::new,
            AbstractBlock.Settings.create().requiresTool().strength(15.0F, 15.0F).sounds(BlockSoundGroup.METAL).mapColor(MapColor.BLACK),
            true
    );
    public static final Block CONCENTRATED_ENERGY_JEWEL_ORE = registerBlock( // TODO add world generation (gotta figure out how!)
            "concentrated_energy_jewel_ore",
            Block::new,
            AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(10.0F, 10.0F),
            true
    );

    public static final Block DEEPSLATE_CONCENTRATED_ENERGY_JEWEL_ORE = registerBlock( // TODO add world generation (gotta figure out how!)
            "deepslate_concentrated_energy_jewel_ore",
            Block::new,
            AbstractBlock.Settings.create().mapColor(MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE).requiresTool().strength(12.0F, 12.0F),
            true
    );
    // ...

    /** Once a block above has been added, make sure to :
     *  Add the item to the custom item group in the initialize method
     *  Add the translation in src/main/resources/assets/synergy/lang/en_us.json
     *  Add the texture in assets/synergy/textures/item (needs to be a .png with the same item identifier as the file name)
     *  Add the model data in assets/synergy/models/item (needs to be a .json with the same item identifier as the file name)
     *  Add the model description in assets/synergy/items (needs to be a .json with the same item identifier as the file name)
     *
     *  OPTIONAL: Add block drops if this block is supposed to drop in survival at data/synergy/loot_table/blocks (needs to be a .json with the same item identifier as the file name) (https://docs.fabricmc.net/develop/blocks/first-block#adding-block-drops)
     *  OPTIONAL: Add a harvesting tool if this block is supposed to only be received using a tool at data/minecraft/tags/block/mineable/<tool_type>.json </> (https://docs.fabricmc.net/develop/blocks/first-block#recommending-a-harvesting-tool)
     *  OPTIONAL: Add a mining level so this item can only be mined with a specific tool level at data/minecraft/tags/block/needs_<tool_material>_tool.json (https://docs.fabricmc.net/develop/blocks/first-block#mining-levels)
     *  OPTIONAL: Check all the different block settings you can add to each block (https://maven.fabricmc.net/docs/fabric-api-0.34.8+1.17/net/fabricmc/fabric/api/object/builder/v1/block/FabricBlockSettings.html)
     *  OPTIONAL: Add a crafting recipe for item in data/mod-id/recipe (JSON generator: https://crafting.thedestruc7i0n.ca/)
     *  OPTIONAL: Add a custom tooltip (https://docs.fabricmc.net/develop/items/first-item#custom-tooltips)
     */

    // Statically initialize the SynergyBlocks class
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(SynergyItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
            itemGroup.add(SynergyBlocks.CONCENTRATED_ENERGY_JEWEL_BLOCK.asItem());
            itemGroup.add(SynergyBlocks.CONCENTRATED_ENERGY_JEWEL_ORE.asItem());
            itemGroup.add(SynergyBlocks.DEEPSLATE_CONCENTRATED_ENERGY_JEWEL_ORE.asItem());
            // ...
        });
    }
}
