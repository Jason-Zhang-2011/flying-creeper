package com.creeper;

import dev.architectury.event.events.common.LootEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

public final class ExampleMod {
    public static final String MOD_ID = "flying_creeper";
    public static final int WING_DURABILITY = 64;
    public static final float WING_DROP_CHANCE = 0.35F;
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM);
    public static final RegistrySupplier<Item> CREEPER_WINGS = ITEMS.register("creeper_wings", () -> new Item(
            new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, id("creeper_wings")))
                    .maxDamage(WING_DURABILITY)
                    .component(DataComponentTypes.GLIDER, Unit.INSTANCE)
                    .component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.CHEST)
                            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA)
                            .model(RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, id("creeper_wings")))
                            .damageOnHurt(false).build())));

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static void init() {
        ITEMS.register();
        CreativeTabRegistry.appendStack(CreativeTabRegistry.defer(ItemGroups.TOOLS),
                () -> new ItemStack(CREEPER_WINGS.get()));
        LootEvent.MODIFY_LOOT_TABLE.register((key, context, builtin) -> {
            if (key.getValue().equals(Identifier.ofVanilla("entities/creeper"))) {
                context.addPool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(WING_DROP_CHANCE))
                        .with(ItemEntry.builder(CREEPER_WINGS.get())));
            }
        });
    }
}
