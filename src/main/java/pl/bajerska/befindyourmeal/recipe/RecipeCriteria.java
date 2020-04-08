package pl.bajerska.befindyourmeal.recipe;

import javax.persistence.*;
import java.util.ArrayList;


public class RecipeCriteria {

    ArrayList<String> ingredients = new ArrayList<String>();

    @Enumerated(EnumType.STRING)
    ArrayList<DietLabels> diet = new ArrayList<DietLabels>();

    @Enumerated(EnumType.STRING)
    ArrayList<HealthLabels> health = new ArrayList<HealthLabels>();

    ArrayList<String> excluded = new ArrayList<String>();

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<DietLabels> getDiet() {
        return diet;
    }

    public void setDiet(ArrayList<DietLabels> diet) {
        this.diet = diet;
    }

    public ArrayList<HealthLabels> getHealth() {
        return health;
    }

    public void setHealth(ArrayList<HealthLabels> health) {
        this.health = health;
    }

    public ArrayList<String> getExcluded() {
        return excluded;
    }

    public void setExcluded(ArrayList<String> excluded) {
        this.excluded = excluded;
    }
}

