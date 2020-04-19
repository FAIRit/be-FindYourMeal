package pl.bajerska.befindyourmeal.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bajerska.befindyourmeal.exception.EmptyIngredientsStringException;
import pl.bajerska.befindyourmeal.model.Recipe;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

        if (q.isEmpty()) {
            throw new EmptyIngredientsStringException();
        }

        String[] labels = q.split(",");

        Recipe found = recipeRepository.findByLabel(recipe.getLabel());
        if (found != null) {
            return found;
        }
        recipeRepository.save(recipe);

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

    @Override
    public List<Recipe> find(RecipeCriteria recipeCriteria) {
        List<Recipe> foundRecipes = new LinkedList<Recipe>();

        recipeRepository.findAll().forEach(r -> {

            if (r.getIngredientTags().stream()
                    .map(IngredientTag::getTag).collect(Collectors.toList())
                    .containsAll(recipeCriteria.getIngredients())) {
                foundRecipes.add(r);
            }
        });

        return foundRecipes;
    }

}
