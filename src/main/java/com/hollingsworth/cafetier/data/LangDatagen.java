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
        add("cafetier.update_schematic", "Schematic created. Set the boundary and use on the desk to set the area.");
        add("cafetier.set_boundary","Cafe boundary set.");
        add("cafetier.create","Create Cafe");
        add("cafetier.cancel_game", "Cancel Game");
        add("cafetier.start_game", "Start Game");
        add("cafetier.get_schematic","Update Schematic");
        add("cafetier.no_spawn_positions", "No spawn positions found. Add one 10 blocks from the cafe boundary.");
        add("cafetier.no_bounds", "No boundary set. Update the boundary within the Management Desk menu.");
        add("cafetier.game_ended", "Game ended.");
        add("cafetier.already_ended", "Game already ended.");
        add("cafetier.game_already_running", "Game already running.");
        add("cafetier.game_starting", "Game starting.");
        add("cafetier.cafe_set", "Cafe set.");
        add("cafetier.menu", "Menu");
        add("cafetier.seats", "Seats: %s");
        add("cafetier.spawners", "Spawners: %s");
        add("cafetier.not_enough_seats", "You need at least 4 seats in the Cafe.");
        add("cafetier.not_enough_menu_items", "You need at least 3 food items in Display Cases.");
        add("cafetier.change_cafe", "Change Cafe");
        add("cafetier.cafe_name", "Cafe Name");
        add("cafetier.cafe_description","Cafe Description");

    }

    public void add(ItemLike itemLike, String name) {
        add(itemLike.asItem(), name);
    }
}
