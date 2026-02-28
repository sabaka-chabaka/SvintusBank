package com.sabakachabaka.svintusbank.items;

import com.sabakachabaka.svintusbank.SvintusBank;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BillItem extends Item {
    private final int value;

    public BillItem(int value) {
        super(new Item.Properties().tab(SvintusBank.ITEM_GROUP));
        this.value = value;
    }

    public int getValue() { return value; }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent("Номинал: §a" + value + " свинтусов"));
    }
}
