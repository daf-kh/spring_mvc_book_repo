package ru.skillbox.web.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.app.services.BookService;
import ru.skillbox.web.dto.Book;

@Controller
@RequestMapping("/books")
public class BookShelfController {

    private final Logger logger = LoggerFactory.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeByRegex")
    public String removeBook(@RequestParam(value = "queryRegex") String bookRegexToRemove) {
        //Разделила на два случая - если написали число и если нет
        //Число проверяем старым методом проверки id
        //String проверяем новым методом
        //Проверяем на пустую строку, чтобы она не совпадала с такими же пустыми названиями/авторами
        if(bookRegexToRemove.matches("^\\d+") && !bookRegexToRemove.trim().equals("")) {
            bookService.removeBookById(Integer.valueOf(bookRegexToRemove));
        }
        else if(!bookRegexToRemove.trim().equals("")) {
            bookService.removeBookByString(bookRegexToRemove);
        }
        return "redirect:/books/shelf";

    }
}
