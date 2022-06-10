package recipes.Bussiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.RecipeMapper;
import recipes.auth.User;
import recipes.auth.UserRepository;
import recipes.dto.LogInDto;
import recipes.dto.RecipeDto;
import recipes.dto.RecipeResponseDto;
import recipes.persistence.Owner;
import recipes.persistence.OwnerRepository;
import recipes.persistence.Recipes;
import recipes.persistence.RecipesRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class RecipeService {

    @Autowired
    private final RecipesRepository recipesRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final OwnerRepository ownerRepository;

    private final RecipeMapper recipeMapper = new RecipeMapper();

    public RecipeService(RecipesRepository recipesRepository, UserRepository userRepository, OwnerRepository ownerRepository) {
        this.recipesRepository = recipesRepository;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
    }

    public void register(LogInDto logInDto) {
        if (userRepository.findUserByUsername(logInDto.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User newUser = recipeMapper.mapToUser(logInDto);
        User createdUser = userRepository.save(newUser);
        System.out.println(":::: new User Created: [" + createdUser.getUsername() + " " + createdUser.getPassword() +"]::::");
    }

    public Long createRecipe (RecipeDto recipeDto, String username) {
        Owner recipeOwner = Owner.builder().email(username).build();
        Owner createdOwner = ownerRepository.save(recipeOwner);
        System.out.println("::: new owner created with email " + createdOwner.getEmail() + " :::" );
        Recipes savedRecipe = recipesRepository.save(recipeMapper.mapToRecipes(recipeDto, createdOwner));
        return savedRecipe.getId();
    }

    public RecipeResponseDto getRecipeById (Long id) {
        if (recipesRepository.findById(id).isPresent()) {
            return recipeMapper.mapToResponseDto(recipesRepository.findById(id).get());
        }
        return null;
    }

    public void deleteRecipeById(Long id) {recipesRepository.deleteById(id);}

    public long updateRecipe(Long id, RecipeDto recipeDto) {
        if (recipesRepository.findById(id).isPresent()) {
            Recipes recipes = recipesRepository.findById(id).get();
            recipes.setName(recipeDto.getName());
            recipes.setCategory(recipeDto.getCategory());
            recipes.setDescription(recipeDto.getDescription());
            recipes.setDate(LocalDateTime.now());
            recipes.setDirections(recipeDto.getDirections());
            recipes.setIngredients(recipeDto.getIngredients());
            recipesRepository.save(recipes);
            return recipes.getId();
        }
        return -1;
    }

    public List<RecipeResponseDto> searchRecipe() {
        List<RecipeResponseDto> recipeList = new ArrayList<>();
        recipesRepository.findAll().forEach(x -> {
            RecipeResponseDto recipes = new RecipeResponseDto();
            recipes.setName(x.getName());
            recipes.setCategory(x.getCategory());
            recipes.setDirections(x.getDirections());
            recipes.setDescription(x.getDescription());
            recipes.setIngredients(x.getIngredients());
            recipes.setDate(x.getDate());
            recipeList.add(recipes);
        });
        return recipeList;
    }
}