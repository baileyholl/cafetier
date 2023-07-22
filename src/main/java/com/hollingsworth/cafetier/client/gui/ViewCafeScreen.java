package com.hollingsworth.cafetier.client.gui;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.api.Cafe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ViewCafeScreen extends ModScreen{
    public static ResourceLocation background = new ResourceLocation(Cafetier.MODID, "textures/gui/spell_book_template.png");

    List<ItemStack> menuItems;
    int numSeats;
    int numSpawners;
    Cafe cafe;

    public ViewCafeScreen(Cafe cafe, List<ItemStack> menuItems, int numSeats, int numSpawners) {
        super(290, 194);
        this.menuItems = menuItems;
        this.numSeats = numSeats;
        this.numSpawners = numSpawners;
        this.cafe = cafe;
    }

    @Override
    public void init() {
        super.init();

    }

    @Override
    public void drawBackgroundElements(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.drawBackgroundElements(graphics, mouseX, mouseY, partialTicks);
        int color = -8355712;
        graphics.drawString(font, "Cafe Name: " + cafe.name, bookLeft, bookTop + 100, color, false);
        graphics.drawString(font, "Menu Items: " + menuItems.size(), bookLeft, bookTop + 20, color, false);
        graphics.drawString(font, "Seats: " + numSeats, bookLeft, bookTop + 40, color, false);
        graphics.drawString(font, "Spawners: " + numSpawners, bookLeft, bookTop + 80, color, false);
    }

    @Override
    public ResourceLocation getBgTexture() {
        return background;
    }
}
