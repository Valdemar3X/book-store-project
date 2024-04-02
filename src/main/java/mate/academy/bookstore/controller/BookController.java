package mate.academy.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.BookDto;
import mate.academy.bookstore.dto.BookSearchParametersDto;
import mate.academy.bookstore.dto.CreateBookRequestDto;
import mate.academy.bookstore.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Books management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Get books", description = "Get all available books")
    @GetMapping
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @Operation(summary = "Get book", description = "Get book by id")
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @Operation(summary = "Create book", description = "Save book to DB")
    @PostMapping
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @Operation(summary = "Delete book", description = "Delete book by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @Operation(summary = "Update book", description = "Update book by id")
    @PutMapping("/{id}")
    public BookDto updateBookById(@PathVariable Long id,
                                  @RequestBody CreateBookRequestDto requestDto) {
        return bookService.updateBookById(id, requestDto);
    }

    @Operation(summary = "Search books", description = "Search books by parameters")
    @GetMapping("/search")
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }

}
