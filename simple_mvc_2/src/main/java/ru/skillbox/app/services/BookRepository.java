package ru.skillbox.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.skillbox.web.dto.Book;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>{

    private final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        //Проверяю, что хоть одно поле заполнено (и не состоит только из пробелов) + проверяю, что size это число
        if(((!book.getAuthor().trim().equals("") || !book.getTitle().trim().equals("")
                || !book.getSize().trim().equals(""))) && book.getSize().matches("^\\d*?")) {
            logger.info("store new book: " + book);
            repo.add(book);
        }
        else {
            logger.info("store new book FAIL");
        }
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for(Book book : retrieveAll()) {
            if(book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        logger.info("remove book FAIL");
        return false;
    }

    @Override
    public boolean removeItemByString(String bookRegexToRemove) {
        for(Book book : retrieveAll()) {
            //Проверяем, есть ли такие автор или название и удаляем все такие книги и возвращаем true в конце
            if(book.getAuthor().equals(bookRegexToRemove) || book.getTitle().equals(bookRegexToRemove)) {
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
            else {
                logger.info("remove book FAIL");
                return false;
            }
        }
        return true;
    }

}
