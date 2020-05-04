package pl.bajerska.befindyourmeal.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bajerska.befindyourmeal.exception.EmptyIngredientsStringException;
import pl.bajerska.befindyourmeal.ingredient.IngredientTag;
import pl.bajerska.befindyourmeal.ingredient.IngredientTagRepository;
import pl.bajerska.befindyourmeal.model.Recipe;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final IngredientTagRepository tagRepository;

    @Autowired
    public RecipeServiceImpl(final RecipeRepository recipeRepository, final IngredientTagRepository tagRepository) {
        this.recipeRepository = recipeRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Recipe save(Recipe recipe, String query) {

        if (query.isEmpty()) {
            throw new EmptyIngredientsStringException();
        }
        String[] labels = query.split(",");

        Recipe found = recipeRepository.findByLabel(recipe.getLabel());
        if (found != null) {
            return found;
        }
        recipeRepository.save(recipe);

        Arrays.stream(labels).forEach(ingredient -> {
            IngredientTag tag = tagRepository.findByTag(ingredient);
            if (isNull(tag)) {
                tag = new IngredientTag(ingredient);
            }
            recipe.addIngredientTag(tag);
            tag.addRecipe(recipe);
            tagRepository.save(tag);
        } );
        return recipe;
    }

    @Override
    public List<Recipe> find(RecipeCriteria recipeCriteria) {
        List<Recipe> foundRecipes = new LinkedList<>();

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
