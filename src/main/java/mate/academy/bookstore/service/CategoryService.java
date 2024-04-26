package mate.academy.bookstore.service;

import java.util.List;
import mate.academy.bookstore.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.bookstore.dto.category.CategoryDto;
import mate.academy.bookstore.dto.category.CreateCategoryRequestDto;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDto save(CreateCategoryRequestDto requestDto);

    List<CategoryDto> getAll(Pageable pageable);

    CategoryDto getById(Long id);

    void deleteById(Long id);

    CategoryDto updateCategoryById(Long id, CreateCategoryRequestDto requestDto);

    List<BookDtoWithoutCategoryIds> findBooksByCategoryId(Long categoryId);
}
