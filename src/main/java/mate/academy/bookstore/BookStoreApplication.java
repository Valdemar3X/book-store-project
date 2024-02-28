package mate.academy.bookstore;

import java.math.BigDecimal;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {
    @Autowired
    private BookService productService;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book harryPotter = new Book();
                harryPotter.setAuthor("\tJ. K. Rowling");
                harryPotter.setPrice(new BigDecimal(100));
                harryPotter.setIsbn("1234653");
                harryPotter.setTitle("Harry Potter and the Philosopher's Stone");
                productService.save(harryPotter);
                System.out.println(productService.findAll());
            }
        };
    }

}
