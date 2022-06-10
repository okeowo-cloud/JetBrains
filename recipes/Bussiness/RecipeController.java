package recipes.Bussiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.SortByDate;
import recipes.dto.LogInDto;
import recipes.dto.RecipeDto;
import recipes.dto.RecipeResponseDto;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;


    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody LogInDto login) {
        recipeService.register(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("recipe/{id}")
    public ResponseEntity<RecipeResponseDto> getARecipe(@Valid @PathVariable Long id) {
        if (recipeService.getRecipeById(id) != null) {
            return new ResponseEntity<>(recipeService.getRecipeById(id), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("recipe/new")
    public ResponseEntity<Map<String, Long>> createARecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long id = recipeService.createRecipe(recipeDto, username);
        return new ResponseEntity<>(Map.of("id", id), HttpStatus.OK);
    }

    @DeleteMapping("recipe/{id}")
    public ResponseEntity<?> deleteARecipe(@Valid @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        RecipeResponseDto responseDto = recipeService.getRecipeById(id);
        if (responseDto != null) {
            if (username.equals(responseDto.getOwner().getEmail())) {
                recipeService.deleteRecipeById(id);
                return ResponseEntity.noContent().build();
            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("recipe/{id}")
    public ResponseEntity<String> updateARecipe(@Valid @PathVariable Long id, @Valid @RequestBody RecipeDto recipeDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        RecipeResponseDto existingRecipeDto = recipeService.getRecipeById(id);
        if (existingRecipeDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!username.equals(existingRecipeDto.getOwner().getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipeService.updateRecipe(id, recipeDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("recipe/search")
    public ResponseEntity<List<RecipeResponseDto>> searchRecipe(@Valid @RequestParam(required = false) String name,
                                                                @Valid @RequestParam(required = false) String category)
            throws NullPointerException {
        List<RecipeResponseDto> recipeList = recipeService.searchRecipe();
        List<RecipeResponseDto> sortedRecipes = new ArrayList<>();
        if (name == null && category == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        try {
            if (category != null && name == null) {
                sortedRecipes = recipeList
                        .stream()
                        .filter(recipe -> {
                            if (recipe.getCategory() != null) {
                                return category.equalsIgnoreCase(recipe.getCategory());
                            }
                            return false;
                        })
                        .sorted(new SortByDate())
                        .collect(Collectors.toList());
            } else if (category == null) {
                sortedRecipes = recipeList
                        .stream()
                        .filter(recipe -> {
                            if (recipe.getName() != null) {
                                return recipe.getName().toLowerCase().contains(name.toLowerCase());
                            }
                            return false;
                        })
                        .sorted(new SortByDate())
                        .collect(Collectors.toList());
            }
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        return new ResponseEntity<>(sortedRecipes, HttpStatus.OK);
    }
}