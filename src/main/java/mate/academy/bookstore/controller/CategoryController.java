package mate.academy.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.bookstore.dto.category.CategoryDto;
import mate.academy.bookstore.dto.category.CreateCategoryRequestDto;
import mate.academy.bookstore.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Categories management", description = "Endpoints for managing categories")
@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get categories", description = "Get all categories")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryService.getAll(pageable);
    }

    @Operation(summary = "Get category", description = "Get category by id")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @Operation(summary = "Create category", description = "Save category to DB")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid CreateCategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @Operation(summary = "Delete category", description = "Delete category by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @Operation(summary = "Update detail category", description = "Update category by id")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryDto updateBookById(@PathVariable Long id,
                                  @RequestBody CreateCategoryRequestDto requestDto) {
        return categoryService.updateCategoryById(id, requestDto);
    }

    @Operation(summary = "Get a book by category id",
            description = "Get a book by category id")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable Long id) {
        return categoryService.findBooksByCategoryId(id);
    }

}
