package com.electricpump;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.pump.PumpBlock;
import com.simibubi.create.content.fluids.pump.PumpBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import java.util.List;

public class ElectricPumpBlock extends PumpBlock {
    public static final DirectionProperty CONNECTOR = DirectionProperty.create("connector", Direction.Plane.HORIZONTAL);

    public ElectricPumpBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
            .setValue(BlockStateProperties.WATERLOGGED, false)
            .setValue(FACING, Direction.NORTH)
            .setValue(CONNECTOR, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CONNECTOR);
    }

    public static Direction getEnergyFacing(BlockState state) {
        Direction facing = state.getValue(FACING);
        if (facing.getAxis() != Direction.Axis.Y) {
            return Direction.UP;
        }
        return state.getValue(CONNECTOR);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state == null) return null;
        Direction facing = state.getValue(FACING);
        Direction connector = Direction.NORTH;
        if (facing.getAxis() == Direction.Axis.Y) {
            connector = context.getHorizontalDirection().getOpposite();
        }
        return state.setValue(CONNECTOR, connector);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        BlockState newState = getRotatedBlockState(state, clickedFace);
        if (!newState.canSurvive(level, pos)) {
            return InteractionResult.PASS;
        }
        KineticBlockEntity.switchToBlockState(level, pos, updateAfterWrenched(newState, context));
        if (level.getBlockState(pos) != state) {
            IWrenchable.playRotateSound(level, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getRotatedBlockState(BlockState state, Direction forcedDirection) {
        Direction facing = state.getValue(FACING);
        if (facing.getAxis() == Direction.Axis.Y) {
            if (forcedDirection == null || forcedDirection.getAxis() == Direction.Axis.Y) {
                return state.setValue(CONNECTOR, state.getValue(CONNECTOR).getClockWise());
            }
            return state.setValue(FACING, facing.getOpposite());
        }
        return state.setValue(FACING, facing.getOpposite());
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return false;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return null;
    }

    @Override
    public boolean isSmallCog() {
        return false;
    }

    @Override
    public boolean isLargeCog() {
        return false;
    }

    @Override
    public boolean isDedicatedCogWheel() {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<PumpBlockEntity> getBlockEntityClass() {
        return (Class<PumpBlockEntity>) (Class<?>) ElectricPumpBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PumpBlockEntity> getBlockEntityType() {
        return ModBlockEntities.ELECTRIC_PUMP.get();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ElectricPumpBlockEntity(pos, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.create_electric_pump.desc")
            .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.create_electric_pump.connector")
            .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.create_electric_pump.wrench_rotate")
            .withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable("tooltip.create_electric_pump.wrench_flip")
            .withStyle(ChatFormatting.DARK_GRAY));
    }
}
