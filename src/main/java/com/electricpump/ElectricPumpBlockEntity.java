package com.electricpump;

import com.simibubi.create.content.fluids.pump.PumpBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

public class ElectricPumpBlockEntity extends PumpBlockEntity {
    public static final int CAPACITY = 10000;
    public static final int MAX_RECEIVE = 1000;
    public static final int CONSUMPTION = 64;
    public static final float OPERATING_SPEED = 128.0f;

    protected final CustomEnergyStorage energy = new CustomEnergyStorage(CAPACITY, MAX_RECEIVE, 0);

    public ElectricPumpBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ElectricPumpBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ELECTRIC_PUMP.get(), pos, state);
    }

    public IEnergyStorage getEnergyStorage() {
        return energy;
    }

    public Direction getEnergyFacing() {
        return ElectricPumpBlock.getEnergyFacing(getBlockState());
    }

    @Override
    public boolean needsSpeedUpdate() {
        return false;
    }

    @Override
    public void attachKinetics() {
    }

    @Override
    public void detachKinetics() {
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentSurplus, int networkSize) {
    }

    @Override
    public float calculateStressApplied() {
        return 0;
    }

    @Override
    public float calculateAddedStressCapacity() {
        return 0;
    }

    @Override
    public void tick() {
        if (level != null && !level.isClientSide) {
            boolean powered = energy.getEnergyStored() >= CONSUMPTION;
            if (powered) {
                energy.extractInternal(CONSUMPTION);
                if (this.speed != OPERATING_SPEED) {
                    float oldSpeed = this.speed;
                    this.speed = OPERATING_SPEED;
                    onSpeedChanged(oldSpeed);
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                }
            } else {
                if (this.speed != 0) {
                    float oldSpeed = this.speed;
                    this.speed = 0;
                    onSpeedChanged(oldSpeed);
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                }
            }
        }
        super.tick();
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.putInt("Energy", energy.getEnergyStored());
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        if (compound.contains("Energy")) {
            energy.setEnergy(compound.getInt("Energy"));
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putFloat("Speed", speed);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            if (tag.contains("Energy")) energy.setEnergy(tag.getInt("Energy"));
            if (tag.contains("Speed")) this.speed = tag.getFloat("Speed");
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        tooltip.add(Component.translatable("goggle.create_electric_pump.title").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("goggle.create_electric_pump.energy")
            .withStyle(ChatFormatting.GRAY)
            .append(Component.literal(energy.getEnergyStored() + " / " + energy.getMaxEnergyStored() + " FE")
            .withStyle(ChatFormatting.AQUA)));
        tooltip.add(Component.translatable("goggle.create_electric_pump.usage")
            .withStyle(ChatFormatting.GRAY)
            .append(Component.literal(CONSUMPTION + " FE/t")
            .withStyle(ChatFormatting.YELLOW)));
        tooltip.add(Component.translatable("goggle.create_electric_pump.status")
            .withStyle(ChatFormatting.GRAY)
            .append(energy.getEnergyStored() >= CONSUMPTION
                ? Component.translatable("goggle.create_electric_pump.active", (int)OPERATING_SPEED).withStyle(ChatFormatting.GREEN)
                : Component.translatable("goggle.create_electric_pump.no_power").withStyle(ChatFormatting.RED)));
        return true;
    }

    public static class CustomEnergyStorage extends EnergyStorage {
        public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {
            super(capacity, maxReceive, maxExtract);
        }

        public void setEnergy(int amount) {
            this.energy = Math.max(0, Math.min(this.capacity, amount));
        }

        public int extractInternal(int toExtract) {
            int extracted = Math.min(this.energy, toExtract);
            this.energy -= extracted;
            return extracted;
        }
    }
}
