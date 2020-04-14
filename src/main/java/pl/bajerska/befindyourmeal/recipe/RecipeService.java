package pl.bajerska.befindyourmeal.recipe;

import pl.bajerska.befindyourmeal.model.Recipe;

public interface RecipeService {

    Recipe save(Recipe recipe, String q);

}
