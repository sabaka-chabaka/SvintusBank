package com.sabakachabaka.svintusbank.items;

import com.sabakachabaka.svintusbank.SvintusBank;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BankCardItem extends Item {
    public BankCardItem(Properties properties) {
        super(properties.tab(SvintusBank.ITEM_GROUP).stacksTo(1));
    }

    public static double getBalance(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getDouble("balance");
        }
        return 0.0;
    }

    public static void setBalance(ItemStack stack, double amount) {
        stack.getOrCreateTag().putDouble("balance", amount);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand){
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            double currentBalance = getBalance(stack);
            player.sendMessage(new StringTextComponent("Текущий баланс: §a" + currentBalance + " свинтусов"), player.getUUID());
        }
        return ActionResult.sidedSuccess(stack, world.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent("Баланс: §6" + getBalance(stack) + " свинтусов"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
