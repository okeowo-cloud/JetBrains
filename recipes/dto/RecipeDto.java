package recipes.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    @NonNull
    @NotBlank
    String name;

    @NonNull
    @NotBlank
    String category;

    @NonNull
    @NotBlank
    String description;

    @NotEmpty
    @Size(min=1)
    List<String> ingredients;

    @NotEmpty
    @Size(min=1)
    List<String> directions;
}
