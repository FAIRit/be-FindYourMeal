package pl.bajerska.befindyourmeal.recipe;

import pl.bajerska.befindyourmeal.model.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe save(Recipe recipe, String q);

    List<Recipe> find(RecipeCriteria recipeCriteria);
}
