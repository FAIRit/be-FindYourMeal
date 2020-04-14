package pl.bajerska.befindyourmeal.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bajerska.befindyourmeal.model.Recipe;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService{

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe save(Recipe recipe, String q) {
        Recipe found = recipeRepository.findByLabel(recipe.getLabel());
        if (found != null) {
            return found;
        }
        String[] labels = q.split(",");
        Arrays.stream(labels).forEach(x -> recipe.addIngredientTag(new IngredientTag(x)));
        return recipeRepository.save(recipe);
    }

}
