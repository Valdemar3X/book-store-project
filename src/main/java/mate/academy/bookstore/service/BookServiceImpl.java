package mate.academy.bookstore.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.BookDto;
import mate.academy.bookstore.dto.CreateBookRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.BookMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);

    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream().map(book -> bookMapper.toDto(book)).toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("can't find book by id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateBookById(Long id, CreateBookRequestDto createBookRequestDto) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Can't find book for updating by id: " + id);
        }
        Book book = bookMapper.toModel(createBookRequestDto);
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }
}
