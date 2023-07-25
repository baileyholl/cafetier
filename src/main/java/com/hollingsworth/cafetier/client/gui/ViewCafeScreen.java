package com.hollingsworth.cafetier.client.gui;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.common.network.CancelGameServer;
import com.hollingsworth.cafetier.common.network.Networking;
import com.hollingsworth.cafetier.common.network.OpenCreateScreen;
import com.hollingsworth.cafetier.common.network.StartGameServer;
import com.hollingsworth.cafetier.common.util.ComponentUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ViewCafeScreen extends ModScreen{
    public static ResourceLocation background = new ResourceLocation(Cafetier.MODID, "textures/gui/spell_book_template.png");

    List<ItemStack> menuItems;
    int numSeats;
    int numSpawners;
    Cafe cafe;
    public Button startGameButton;
    public UUID runningGameID;
    public Button createCafeButton;

    public ViewCafeScreen(Cafe cafe, List<ItemStack> menuItems, int numSeats, int numSpawners, UUID runningGameID) {
        super(290, 194);
        this.menuItems = menuItems;
        this.numSeats = numSeats;
        this.numSpawners = numSpawners;
        this.cafe = cafe;
        this.runningGameID = runningGameID;
    }

    @Override
    public void init() {
        super.init();
        for(int i = 0; i < Math.min(18, menuItems.size()); i++){
            ItemStack stack = menuItems.get(i);
            HoverableItem item = new HoverableItem(bookLeft + 155 + 20 * (i % 6), bookTop + 30 + (20 * (i / 6)), stack);
            addRenderableWidget(item);
        }
        startGameButton = new ANButton(bookRight - 110, bookBottom - 40, 70, 20, Component.translatable(runningGameID != null ? "cafetier.cancel_game" : "cafetier.start_game"), this::startGame);
        startGameButton.active = this.canStartGame() || runningGameID != null;
        createCafeButton = new ANButton(bookLeft + 40, bookBottom - 40, 70, 20, Component.translatable("cafetier.change_cafe"), this::openCreate);
        addRenderableWidget(createCafeButton);
        addRenderableWidget(startGameButton);
    }

    public void openCreate(Button button){
        Networking.sendToServer(new OpenCreateScreen(this.cafe.deskPos));
    }

    public void startGame(Button button){
        if(runningGameID != null){
            Networking.sendToServer(new CancelGameServer(runningGameID));
        }else if(canStartGame()){
            Networking.sendToServer(new StartGameServer(cafe.deskPos));
        }
        minecraft.setScreen(null);
    }

    @Override
    public void drawTooltip(GuiGraphics stack, int mouseX, int mouseY) {
        super.drawTooltip(stack, mouseX, mouseY);
        for(Widget widget : this.renderables){
            if(widget instanceof HoverableItem hoverableItem){
                if(GuiUtils.isMouseInRelativeRange(mouseX, mouseY, hoverableItem)) {
                    renderTooltip(stack.poseStack, getTooltipFromItem(hoverableItem.stack), hoverableItem.stack.getTooltipImage(), mouseX, mouseY, hoverableItem.stack);
                }
            }
            if(widget == this.startGameButton){
                if(GuiUtils.isMouseInRelativeRange(mouseX, mouseY, startGameButton)){
                    List<Component> tooltips = new ArrayList<>();
                    if(numSpawners <= 0){
                        tooltips.add(Component.translatable("cafetier.no_spawn_positions").withStyle(ComponentUtil.TAKE_ACTION_STYLE));
                    }
                    if(numSeats < 4){
                        tooltips.add(Component.translatable("cafetier.not_enough_seats").withStyle(ComponentUtil.TAKE_ACTION_STYLE));
                    }
                    if(menuItems.size() < 3){
                        tooltips.add(Component.translatable("cafetier.not_enough_menu_items").withStyle(ComponentUtil.TAKE_ACTION_STYLE));
                    }
                    if(cafe.getBounds() == null){
                        tooltips.add(Component.translatable("cafetier.no_bounds").withStyle(ComponentUtil.TAKE_ACTION_STYLE));
                    }
                    if(!tooltips.isEmpty()) {
                        renderComponentTooltip(stack.poseStack, tooltips, mouseX, mouseY);
                    }
                }
            }
        }
    }

    public boolean canStartGame(){
        return numSpawners > 0 && numSeats > 3 && menuItems.size() >= 3 && cafe.getBounds() != null;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void drawBackgroundElements(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.drawBackgroundElements(graphics, mouseX, mouseY, partialTicks);
        int color = -8355712;
        graphics.drawString(font, cafe.name, 60, 20, color, false);
        graphics.drawString(font, Component.translatable("cafetier.menu").getString(), 200, 20, color, false);
        graphics.drawString(font, Component.translatable("cafetier.seats", numSeats).getString(), 155, bookTop + 75, color, false);
        graphics.drawString(font, Component.translatable("cafetier.spawners", numSpawners).getString(), 155, bookTop + 85, color, false);
    }

    @Override
    public ResourceLocation getBgTexture() {
        return background;
    }
}
