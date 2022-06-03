package ru.skillbox.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.web.dto.Book;

import java.util.List;

@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;

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

    public void removeBookById(Integer bookIdToRemove) {
        bookRepo.removeItemById(bookIdToRemove);
    }

    public void removeBookByString(String bookRegexToRemove) {
        bookRepo.removeItemByString(bookRegexToRemove);
    }
}
