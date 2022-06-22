package ru.skillbox.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.web.dto.Book;

import java.util.List;

@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;
    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookByString(String bookRegexToRemove) {
       return bookRepo.removeItemByString(bookRegexToRemove);
    }

//    public boolean removeBookById(Integer id) {
//        return bookRepo.removeItemByString(id);
//    }

    public void defaultInit() {
        logger.info("default INIT in bookService");
    }

    public void defaultDestroy() {
        logger.info("default DESTROY in bookService");
    }
}
