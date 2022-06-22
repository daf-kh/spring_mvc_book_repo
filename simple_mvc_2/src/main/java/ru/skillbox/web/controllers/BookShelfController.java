package ru.skillbox.web.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.app.exceptions.BookShelfLoginException;
import ru.skillbox.app.services.BookService;
import ru.skillbox.web.dto.Book;
import ru.skillbox.web.dto.BookRegexToRemove;
import ru.skillbox.web.dto.FileForUpload;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/books")
@Scope("singleton")
public class BookShelfController {

    private final Logger logger = LoggerFactory.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookRegexToRemove", new BookRegexToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookRegexToRemove", new BookRegexToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookRegexToRemove bookRegexToRemove, Model model) {
        if (bookService.removeBookByString(bookRegexToRemove.getRegex())) {
            return "redirect:/books/shelf";
        }
        else {
            model.addAttribute("notFoundRegex", Boolean.TRUE);
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("bookRegexToRemove", bookRegexToRemove);
            return "book_shelf";
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        if(file.isEmpty()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("bookRegexToRemove", new BookRegexToRemove());
            model.addAttribute("notSelectedFile", Boolean.TRUE);
            return "book_shelf";
        }
        else {
            String name = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            //create dir
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "external_uploads");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //create file
            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            logger.info("new file saved at: " + serverFile.getAbsolutePath());
            return "redirect:/books/shelf";
        }
    }

    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, BookShelfLoginException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "book_shelf";
    }



//    @PostMapping("/removeByRegex")
//    public String removeBook(@RequestParam(value = "queryRegex") String bookRegexToRemove) {
//        //Разделение на два метода уже не нужно, нужна только проверка на пустую строку
//        if(!bookRegexToRemove.trim().equals("")) {
//            bookService.removeBookByString(bookRegexToRemove);
//        }
//        else logger.info("removing book FAIL - empty regex");
//        return "redirect:/books/shelf";
//
//    }
}
