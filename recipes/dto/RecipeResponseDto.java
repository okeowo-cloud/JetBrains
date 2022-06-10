package recipes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.persistence.Owner;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDto {
    String name;
    String category;
    LocalDateTime date;
    String description;
    List<String> ingredients;
    List<String> directions;
    @JsonIgnore
    Owner owner;
}
