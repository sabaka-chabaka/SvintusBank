package com.sabakachabaka.svintusbank.blocks;

import com.sabakachabaka.svintusbank.items.BankCardItem;
import com.sabakachabaka.svintusbank.items.BillItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import static com.sabakachabaka.svintusbank.items.ModItems.BILL_50;

public class ATMBlock extends Block {
    public ATMBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).strength(5.0f).requiresCorrectToolForDrops());
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (world.isClientSide) return ActionResultType.SUCCESS;

        ItemStack mainHand = player.getItemInHand(Hand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(Hand.OFF_HAND);

        if (mainHand.getItem() instanceof BankCardItem && offHand.isEmpty()) {
            double balance = BankCardItem.getBalance(mainHand);
            if (balance >= 50) {
                BankCardItem.setBalance(mainHand, balance - 50);
                player.addItem(new ItemStack(BILL_50.get()));
                player.displayClientMessage(new StringTextComponent("§6ATM: §fСнято 50 свинтусов"), true);
            } else {
                player.displayClientMessage(new StringTextComponent("§cНедостаточно средств (минимум 50)"), true);
            }
            return ActionResultType.SUCCESS;
        }

        if (mainHand.getItem() instanceof BankCardItem && offHand.getItem() instanceof BillItem) {
            BillItem bill = (BillItem) offHand.getItem();
            int amount = bill.getValue();

            double currentBalance = BankCardItem.getBalance(mainHand);
            BankCardItem.setBalance(mainHand, currentBalance + amount);

            offHand.shrink(1);

            player.displayClientMessage(new StringTextComponent("§6ATM: §fЗачислено §a" + amount + " §fсвинтусов!"), true);
            return ActionResultType.SUCCESS;
        }

        player.displayClientMessage(new StringTextComponent("§cВозьмите карту в основную руку, а купюру в левую!"), true);
        return ActionResultType.CONSUME;
    }
}