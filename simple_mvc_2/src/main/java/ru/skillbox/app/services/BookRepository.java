package ru.skillbox.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.web.dto.Book;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(BookRepository.class);
//    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retrieveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        //Проверяю, что хоть одно поле заполнено (и не состоит только из пробелов) + проверяю, что size это число
        if(((!book.getAuthor().trim().equals("") || !book.getTitle().trim().equals("")
                || book.getSize()!=0))) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("author", book.getAuthor());
            parameterSource.addValue("title", book.getTitle());
            parameterSource.addValue("size", book.getSize());
            jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES (:author, :title, :size)",
                    parameterSource);
            logger.info("store new book: " + book);
        }
        else {
            logger.info("store new book FAIL");
        }
    }


    @Override
    public boolean removeItemByString(String bookRegexToRemove) {
        //Введу счетчик, чтобы написать сообщение, если книга не была найдена
        int countBooks = 0;
        boolean result = false;
        for(Book book : retrieveAll()) {
            //Отделим только цифровые значения, чтобы не выкидывало ошибку при переводе в Integer
            if(bookRegexToRemove.matches("\\d+")) {
                if(Objects.equals(book.getSize(), Integer.valueOf(bookRegexToRemove))){
                    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                    parameterSource.addValue("size", bookRegexToRemove);
                    jdbcTemplate.update("DELETE FROM books WHERE size = :size", parameterSource);
                    logger.info("remove book completed");
                    countBooks++;
                    result = true;
                }
                if(Objects.equals(book.getId(), Integer.valueOf(bookRegexToRemove))){
                    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                    parameterSource.addValue("id", bookRegexToRemove);
                    jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
                    logger.info("remove book completed");
                    countBooks++;
                    result = true;
                }
            }
                if (book.getAuthor().equals(bookRegexToRemove)) {
                    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                    parameterSource.addValue("author", bookRegexToRemove);
                    jdbcTemplate.update("DELETE FROM books WHERE author = :author", parameterSource);
                    logger.info("remove book completed");
                    countBooks++;
                    result = true;
                }
                if (book.getTitle().equals(bookRegexToRemove)) {
                    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                    parameterSource.addValue("title", bookRegexToRemove);
                    jdbcTemplate.update("DELETE FROM books WHERE title = :title", parameterSource);
                    logger.info("remove book completed");
                    countBooks++;
                    result = true;
                }
        }
        if(countBooks == 0) {
            result = false;
            logger.info("remove book FAIL - book was not found");
        }
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void defaultInit() {
        logger.info("default INIT in bookRepo bean");
    }

    public void defaultDestroy() {
        logger.info("default DESTROY in bookRepo bean");
    }
}
