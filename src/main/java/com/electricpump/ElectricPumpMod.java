package com.electricpump;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(ElectricPumpMod.MOD_ID)
public class ElectricPumpMod {
    public static final String MOD_ID = "create_electric_pump";

    public ElectricPumpMod(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::addCreative);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
            Capabilities.EnergyStorage.BLOCK,
            ModBlockEntities.ELECTRIC_PUMP.get(),
            (be, side) -> {
                if (side == null || side == be.getEnergyFacing()) {
                    return be.getEnergyStorage();
                }
                return null;
            }
        );
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if ("create".equals(event.getTabKey().location().getNamespace()) &&
            "base".equals(event.getTabKey().location().getPath())) {
            event.accept(new ItemStack(ModItems.ELECTRIC_PUMP.get()));
        }
    }
}
