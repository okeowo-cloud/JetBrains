package recipes;

import recipes.dto.RecipeResponseDto;

import java.util.Comparator;

public class SortByDate implements Comparator<RecipeResponseDto> {
    @Override
    public int compare(RecipeResponseDto r1, RecipeResponseDto r2) {
        if (r1.getDate().isAfter(r2.getDate())) {
            return -1;
        } else if (r1.getDate().isBefore(r2.getDate())) {
            return 1;
        }
        return 0;
    }
}
