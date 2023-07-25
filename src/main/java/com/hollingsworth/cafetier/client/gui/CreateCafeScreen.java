package com.hollingsworth.cafetier.client.gui;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.common.network.CreateCafeServer;
import com.hollingsworth.cafetier.common.network.Networking;
import com.hollingsworth.cafetier.common.network.RequestSchematicServer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CreateCafeScreen extends ModScreen {

    public static ResourceLocation background = new ResourceLocation(Cafetier.MODID, "textures/gui/spell_book_template.png");

    public EditBox cafeName;
    public Button confirm;
    public BlockPos deskPos;

    public CreateCafeScreen(BlockPos deskPos) {
        super(290, 194);
        this.deskPos = deskPos;
    }

    @Override
    public void init() {
        super.init();
        cafeName = new EditBox(font, bookLeft + 80, bookTop + 40, 60, 12, Component.empty());
        confirm = new Button(bookLeft + 40, bookTop + 150, 80, 20, Component.translatable("cafetier.create"), this::onCreate);

        Button schematicButton = new Button(bookLeft + 25, bookTop + 100, 100, 20, Component.translatable("cafetier.get_schematic"), this::onSchematic);
        addRenderableWidget(cafeName);
        addRenderableWidget(confirm);
        addRenderableWidget(schematicButton);
    }

    public void onSchematic(Button button){
        Networking.sendToServer(new RequestSchematicServer(deskPos));
        minecraft.setScreen(null);
    }

    public void onCreate(Button button){
        Networking.sendToServer(new CreateCafeServer(deskPos, cafeName.getValue()));
        minecraft.setScreen(null);
    }


    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void drawBackgroundElements(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.drawBackgroundElements(graphics, mouseX, mouseY, partialTicks);
        int color = -8355712;
        graphics.drawString(font, Component.translatable("cafetier.create").getString(), 50, 20, color, false);
        graphics.drawString(font, Component.translatable("cafetier.cafe_name").getString(), 20, 40, color, false);
        graphics.drawString(font, Component.translatable("cafetier.cafe_description").getString(), 20, 60, color, false);
//        graphics.drawString(font, Component.translatable("cafetier.select_cafe"));

//        graphics.drawString(font, Component.translatable("cafetier.menu").getString(), 200, 20, color, false);
//        graphics.drawString(font, Component.translatable("cafetier.seats", numSeats).getString(), 155, bookTop + 75, color, false);
//        graphics.drawString(font, Component.translatable("cafetier.spawners", numSpawners).getString(), 155, bookTop + 85, color, false);
    }

    @Override
    public ResourceLocation getBgTexture() {
        return background;
    }
}
