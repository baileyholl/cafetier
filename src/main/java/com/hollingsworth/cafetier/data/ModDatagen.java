package com.hollingsworth.cafetier.data;


import com.hollingsworth.cafetier.Cafetier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagen {

    @SubscribeEvent
    public static void datagen(GatherDataEvent event) {

        event.getGenerator().addProvider(event.includeClient(), new ItemModelGenerator(event.getGenerator(), Cafetier.MODID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeClient(), new LangDatagen(event.getGenerator(), Cafetier.MODID, "en_us"));
        event.getGenerator().addProvider(event.includeServer(), new RecipeDatagen(event.getGenerator()));
        event.getGenerator().addProvider(event.includeServer(), new BlockStatesDatagen(event.getGenerator(), Cafetier.MODID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeServer(), new PatchouliProvider(event.getGenerator()));
//        event.getGenerator().addProvider(event.includeServer(), new LootTableProvider(event.getGenerator()));
        event.getGenerator().addProvider(event.includeServer(), new DefaultTableProvider(event.getGenerator()));
        event.getGenerator().addProvider(event.includeServer(), new Advancements(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeServer(), new BlockTagProvider(event.getGenerator(), event.getExistingFileHelper()));
    }

}
