package pl.bajerska.befindyourmeal.recipe;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import pl.bajerska.befindyourmeal.exception.EmptyIngredientsStringException;
import pl.bajerska.befindyourmeal.ingredient.IngredientTag;
import pl.bajerska.befindyourmeal.ingredient.IngredientTagRepository;
import pl.bajerska.befindyourmeal.model.Recipe;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@RunWith(JUnit4.class)
public class RecipeServiceImplTest {

    private RecipeRepository recipeRepository;
    private IngredientTagRepository tagRepository;
    private RecipeService recipeService;

    @Before
    public void setUp(){
        recipeRepository = mock(RecipeRepository.class);
        tagRepository = mock(IngredientTagRepository.class);
        recipeService = new RecipeServiceImpl(recipeRepository, tagRepository);
    }

    @Test
    public void shouldNotFindRecipeWhenCriteriaIsNullAndRecipeListIsNull() {

        RecipeCriteria recipeCriteria = null;
        Mockito.when(recipeRepository.findAll()).thenReturn(null);

        Throwable exception = assertThrows(NullPointerException.class, () -> recipeService.find(recipeCriteria));


    }

    @Test
    public void shouldNotFindRecipeWhenCriteriaIsNull() {

        RecipeCriteria recipeCriteria = null;
        List<Recipe> list = new LinkedList<>();
        list.add(new Recipe());
        Mockito.when(recipeRepository.findAll()).thenReturn(list);

        Throwable exception = assertThrows(NullPointerException.class, () -> recipeService.find(recipeCriteria));

    }

    @Test
    public void shouldNotFindRecipeWhenRecipeDoesNotHaveAnyTag() {

        RecipeCriteria recipeCriteria = new RecipeCriteria();
        recipeCriteria.addIngredient("chicken");
        List<Recipe> list = new LinkedList<>();
        list.add(new Recipe());
        Mockito.when(recipeRepository.findAll()).thenReturn(list);

        List<Recipe> found  = recipeService.find(recipeCriteria);

        assertEquals(found.size(), 0);


    }

    @Test
    public void shouldFindRecipeWhenRecipeFulfilsCriteria() {

        String tagA = new String("chicken");
        String tagB = new String("ham");
        String tagC = new String("onion");
        String tagD = new String("beef");

        List<Recipe> list = new LinkedList<>();
        Recipe recipe = new Recipe();
        recipe.addIngredientTag(new IngredientTag(tagA));
        recipe.addIngredientTag(new IngredientTag(tagB));
        recipe.addIngredientTag(new IngredientTag(tagC));
        recipe.setLabel("ABC");
        list.add(recipe);
        recipe =new Recipe();
        recipe.addIngredientTag(new IngredientTag(tagB));
        recipe.addIngredientTag(new IngredientTag(tagC));
        recipe.addIngredientTag(new IngredientTag(tagD));
        recipe.setLabel("BCD");
        list.add(recipe);
        recipe =new Recipe();
        recipe.addIngredientTag(new IngredientTag(tagB));
        recipe.addIngredientTag(new IngredientTag(tagC));
        recipe.setLabel("BC");
        list.add(recipe);

        Mockito.when(recipeRepository.findAll()).thenReturn(list);

        RecipeCriteria recipeCriteria = new RecipeCriteria();
        recipeCriteria.addIngredient(tagA);
        recipeCriteria.addIngredient(tagB);
        recipeCriteria.addIngredient(tagC);
        List<Recipe> found  = recipeService.find(recipeCriteria);

        assertEquals(1, found.size());
        assertTrue(found.stream().map(Recipe::getLabel).collect(Collectors.joining()).contains("ABC"));

    }

    @Test
    public void shouldFindRecipeWhenRecipeFulfilsCriteriaCaseDisjunctive() {

        String tagA = new String("chicken");
        String tagB = new String("ham");
        String tagC = new String("onion");
        String tagD = new String("beef");

        List<Recipe> list = new LinkedList<>();
        Recipe recipe = new Recipe();
        recipe.addIngredientTag(new IngredientTag(tagA));
        recipe.addIngredientTag(new IngredientTag(tagB));
        recipe.addIngredientTag(new IngredientTag(tagC));
        recipe.setLabel("ABC");
        list.add(recipe);
        recipe =new Recipe();
        recipe.addIngredientTag(new IngredientTag(tagB));
        recipe.addIngredientTag(new IngredientTag(tagC));
        recipe.addIngredientTag(new IngredientTag(tagD));
        recipe.setLabel("BCD");
        list.add(recipe);
        recipe =new Recipe();
        recipe.addIngredientTag(new IngredientTag(tagC));
        recipe.addIngredientTag(new IngredientTag(tagD));
        recipe.setLabel("BC");
        list.add(recipe);

        Mockito.when(recipeRepository.findAll()).thenReturn(list);

        RecipeCriteria recipeCriteria = new RecipeCriteria();
        recipeCriteria.addIngredient(tagA);
        recipeCriteria.addIngredient(tagB);
        List<Recipe> found  = recipeService.find(recipeCriteria);

        assertEquals(1, found.size());
        assertTrue(found.stream().map(Recipe::getLabel).collect(Collectors.joining()).contains("ABC"));

    }


    @Test
    public void shouldNotSaveRecipeWhenExist() {

        Recipe recipe = new Recipe();
        recipe.setLabel("BC");
        Mockito.when(recipeRepository.findByLabel(recipe.getLabel())).thenReturn(recipe);

        Recipe found  = recipeService.save(recipe, "q");

        assertNotNull(found);
        assertEquals(recipe.getLabel(), found.getLabel());


    }

    @Test
    public void shouldNotSaveRecipeWithTagsNull() {

        Recipe recipe = new Recipe();
        recipe.setLabel("BC");
        Mockito.when(recipeRepository.findByLabel(recipe.getLabel())).thenReturn(null);
        Mockito.when(recipeRepository.save(recipe)).thenReturn(recipe);

        Throwable exception = assertThrows(NullPointerException.class, () -> recipeService.save(recipe, null));

    }

    @Test
    public void shouldNotSaveRecipeWithEmptyTags() {

        Recipe recipe = new Recipe();
        recipe.setLabel("BC");
        Mockito.when(recipeRepository.findByLabel(recipe.getLabel())).thenReturn(null);
        Mockito.when(recipeRepository.save(recipe)).thenReturn(recipe);

        Throwable exception = assertThrows(EmptyIngredientsStringException.class, () -> recipeService.save(recipe, ""));

    }

    @Test
    public void shouldSaveRecipeWithOneExistingTag() {

        Recipe recipe = new Recipe();
        recipe.setLabel("BC");
        Mockito.when(recipeRepository.findByLabel(recipe.getLabel())).thenReturn(null);
        Mockito.when(recipeRepository.save(recipe)).thenReturn(recipe);
        IngredientTag tag = new IngredientTag("chicken");
        Mockito.when(tagRepository.findByTag(tag.getTag())).thenReturn(tag);
        Mockito.when(tagRepository.save(tag)).thenReturn(tag);

        Recipe saved = recipeService.save(recipe, tag.getTag());

        assertNotNull(saved);
        assertEquals("BC", saved.getLabel());
        assertEquals(1, saved.getIngredientTags().size());
        assertTrue(saved.getIngredientTags().stream().map(IngredientTag::getTag).collect(Collectors.joining()).contains(tag.getTag()));

    }

    @Test
    public void shouldSaveRecipeWithFewExistingTags() {

        Recipe recipe = new Recipe();
        recipe.setLabel("BC");
        Mockito.when(recipeRepository.findByLabel(recipe.getLabel())).thenReturn(null);
        Mockito.when(recipeRepository.save(recipe)).thenReturn(recipe);
        IngredientTag tag1 = new IngredientTag("chicken");
        IngredientTag tag2 = new IngredientTag("onion");
        IngredientTag tag3 = new IngredientTag("tomato");
        Mockito.when(tagRepository.findByTag(tag1.getTag())).thenReturn(tag1);
        Mockito.when(tagRepository.save(tag1)).thenReturn(tag1);
        Mockito.when(tagRepository.findByTag(tag2.getTag())).thenReturn(tag2);
        Mockito.when(tagRepository.save(tag2)).thenReturn(tag2);
        Mockito.when(tagRepository.findByTag(tag3.getTag())).thenReturn(tag3);
        Mockito.when(tagRepository.save(tag3)).thenReturn(tag3);

        Recipe saved = recipeService.save(recipe, tag1.getTag() + "," + tag2.getTag() + "," + tag3.getTag());

        assertNotNull(saved);
        assertEquals("BC", saved.getLabel());
        assertEquals(3, saved.getIngredientTags().size());
        assertTrue(saved.getIngredientTags().stream().map(IngredientTag::getTag).collect(Collectors.joining()).contains(tag1.getTag()));
        assertTrue(saved.getIngredientTags().stream().map(IngredientTag::getTag).collect(Collectors.joining()).contains(tag2.getTag()));
        assertTrue(saved.getIngredientTags().stream().map(IngredientTag::getTag).collect(Collectors.joining()).contains(tag3.getTag()));

    }


    @Test
    public void shouldSaveRecipeWithOneNonExistingTag() {

        Recipe recipe = new Recipe();
        recipe.setLabel("BC");
        Mockito.when(recipeRepository.findByLabel(recipe.getLabel())).thenReturn(null);
        Mockito.when(recipeRepository.save(recipe)).thenReturn(recipe);
        IngredientTag tag = new IngredientTag("chicken");
        Mockito.when(tagRepository.findByTag(tag.getTag())).thenReturn(null);
        Mockito.when(tagRepository.save(tag)).thenReturn(tag);

        Recipe saved = recipeService.save(recipe, tag.getTag());

        assertNotNull(saved);
        assertEquals("BC", saved.getLabel());
        assertEquals(1, saved.getIngredientTags().size());
        assertTrue(saved.getIngredientTags().stream().map(IngredientTag::getTag).collect(Collectors.joining()).contains(tag.getTag()));

    }

}
