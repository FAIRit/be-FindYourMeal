package pl.bajerska.befindyourmeal.recipe;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bajerska.befindyourmeal.edamam.ApiService;
import pl.bajerska.befindyourmeal.model.EdamamOutput;
import pl.bajerska.befindyourmeal.model.Recipe;


@Controller
public class RecipeController {

    private ApiService apiService;

    public RecipeController(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping(value = {"describerecipe"}, method = RequestMethod.GET)
    public String getRecipe(Model model) {
        RecipeCriteria recipeCriteria = new RecipeCriteria();
        model.addAttribute("recipeCriteria", recipeCriteria);
        return "describerecipe";
    }

    @PostMapping(value = {"/findrecipe"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findRecipe(RecipeCriteria recipeCriteria, Model model) {

        model.addAttribute("edamamOutput", apiService.findRecipe(recipeCriteria));
        return "recipe";
    }

}
