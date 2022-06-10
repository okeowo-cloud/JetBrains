package recipes;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import recipes.auth.User;
import recipes.dto.LogInDto;
import recipes.dto.RecipeDto;
import recipes.dto.RecipeResponseDto;
import recipes.persistence.Owner;
import recipes.persistence.Recipes;

import java.time.LocalDateTime;

public class RecipeMapper {

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    public Recipes mapToRecipes (RecipeDto recipeDto, Owner recipeOwner) {
        return Recipes.builder()
                .name(recipeDto.getName())
                .category(recipeDto.getCategory())
                .description(recipeDto.getDescription())
                .directions(recipeDto.getDirections())
                .ingredients(recipeDto.getIngredients())
                .owner(recipeOwner)
                .date(LocalDateTime.now())
                .build();
    }

    public RecipeResponseDto mapToResponseDto (Recipes recipes) {
        return RecipeResponseDto.builder()
                .name(recipes.getName())
                .category(recipes.getCategory())
                .date(recipes.getDate())
                .description(recipes.getDescription())
                .directions(recipes.getDirections())
                .ingredients(recipes.getIngredients())
                .owner(recipes.getOwner())
                .build();
    }

    public User mapToUser(LogInDto loginDto) {
        User newUser = new User();
        newUser.setUsername(loginDto.getEmail());
        newUser.setPassword(encoder.encode(loginDto.getPassword()));
        newUser.setRole("ROLE_USER");
        return newUser;
    }
}
