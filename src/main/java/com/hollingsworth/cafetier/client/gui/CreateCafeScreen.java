package com.hollingsworth.cafetier.client.gui;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.common.network.CreateCafeServer;
import com.hollingsworth.cafetier.common.network.Networking;
import com.hollingsworth.cafetier.common.network.RequestSchematicServer;
import com.hollingsworth.cafetier.common.network.StartGameServer;
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
    public Button startGame;
    public BlockPos deskPos;

    public CreateCafeScreen(BlockPos deskPos) {
        super(290, 194);
        this.deskPos = deskPos;
    }

    @Override
    public void init() {
        super.init();
        cafeName = new EditBox(font, bookLeft, bookTop, 60, 12, Component.empty());
        confirm = new Button(bookLeft, bookTop + 20, 60, 16, Component.translatable("cafetier.create"), this::onCreate);
        startGame = new Button(bookLeft, bookTop + 40, 60, 16, Component.translatable("cafetier.start_game"), this::startGame);
        Button schematicButton = new Button(bookLeft, bookTop + 60, 60, 16, Component.translatable("cafetier.get_schematic"), this::onSchematic);
        addRenderableWidget(cafeName);
        addRenderableWidget(confirm);
        addRenderableWidget(startGame);
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

    public void startGame(Button button){
        Networking.sendToServer(new StartGameServer(deskPos));
        minecraft.setScreen(null);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public ResourceLocation getBgTexture() {
        return background;
    }
}
