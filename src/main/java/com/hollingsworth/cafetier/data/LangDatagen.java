package com.hollingsworth.cafetier.data;

import com.hollingsworth.cafetier.common.block.CafeBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.LanguageProvider;

public class LangDatagen extends LanguageProvider {
    public LangDatagen(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add(CafeBlocks.DISPLAY_BLOCK, "Display Case");
        add(CafeBlocks.CASH_REGISTER, "Cash Register");
        add(CafeBlocks.PLATE_BLOCK, "Copper Plate");
        add(CafeBlocks.OAK_CHAIR, "Oak Chair");
        add(CafeBlocks.MANAGEMENT_DESK, "Management Desk");
    }

    public void add(ItemLike itemLike, String name) {
        add(itemLike.asItem(), name);
    }
}
