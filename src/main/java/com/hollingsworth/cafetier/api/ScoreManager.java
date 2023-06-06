package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.game_events.CustomerPaidEvent;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import net.minecraft.world.item.ItemStack;

public class ScoreManager {
    public int wavesCompleted;
    public int customersServed;
    public int customersLost;
    public int penalty;
    public int score;


    public ScoreManager(){
        this(1);
    }

    public ScoreManager(float difficultyModifier) {

    }

    public int getScore() {
        return score;
    }

    public void onEvent(IStateEvent event){
        if(event instanceof CustomerPaidEvent customerPaidEvent){
            var customer = customerPaidEvent.customer;
            customersServed++;
            var happinessScore = customer.getHappiness() * customer.scoreModifier();
            score += happinessScore;
            ItemStack orderedItem = customer.getDesiredItem();
            if(orderedItem.isEdible() && orderedItem.getFoodProperties(customer) != null){
                var foodProperties = orderedItem.getFoodProperties(customer);
                var hungerScore = foodProperties.getNutrition() * 10f;
                var saturationScore = foodProperties.getSaturationModifier() * 50f;
                var menuModifier = customer.game.menuStacks.size();
                var baseFoodScore = hungerScore + saturationScore;
                var finalFoodScore = baseFoodScore + baseFoodScore * (0.1f * menuModifier);
                score += finalFoodScore;
            }
        }
    }
}
