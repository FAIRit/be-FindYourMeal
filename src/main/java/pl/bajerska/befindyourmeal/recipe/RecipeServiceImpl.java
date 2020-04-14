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
    private IngredientTagRepository tagRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, IngredientTagRepository tagRepository) {
        this.recipeRepository = recipeRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Recipe save(Recipe recipe, String q) {
        Recipe found = recipeRepository.findByLabel(recipe.getLabel());
        if (found != null) {
            return found;
        }
        recipeRepository.save(recipe);

        String[] labels = q.split(",");

        Arrays.stream(labels).forEach(x -> {
            IngredientTag tag = tagRepository.findByTag(x);
            if (tag == null) {
                tag = new IngredientTag(x);
            }
            recipe.addIngredientTag(tag);
            tag.addRecipe(recipe);
            tagRepository.save(tag);
        } );
        return recipe;
    }

}
