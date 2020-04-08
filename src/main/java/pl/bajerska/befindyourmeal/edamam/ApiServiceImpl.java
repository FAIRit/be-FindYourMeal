package pl.bajerska.befindyourmeal.edamam;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.bajerska.befindyourmeal.recipe.RecipeCriteria;

@Service
public class ApiServiceImpl implements ApiService {


    @Override
    public String test() {
        RestTemplate rest = new RestTemplate();

        ResponseEntity<String> exchange = rest.exchange(
                "https://api.edamam.com/search?app_id=2f71b0fa&app_key=c0ed4fc9f52cd3c0d3d1f74b8f6e220d&q=haddock",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class);
        return exchange.getBody();
    }

    private String queryBuilder(RecipeCriteria recipeCriteria) {
        String query = new String();
        for (String ing : recipeCriteria.getIngredients()) {
            if ((query.length() > 0) && (ing.length() > 0))
                query += ",";
            query += ing;
        }
        return "&q=" + query;
    }

    @Override
    public String findRecipe(RecipeCriteria recipeCriteria) {
        RestTemplate rest = new RestTemplate();

        ResponseEntity<String> exchange = rest.exchange(
                "https://api.edamam.com/search?app_id=2f71b0fa&app_key=c0ed4fc9f52cd3c0d3d1f74b8f6e220d" + queryBuilder(recipeCriteria),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class);

        return exchange.getBody();
    }

}
