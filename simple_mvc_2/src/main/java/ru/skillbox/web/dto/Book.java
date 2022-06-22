package ru.skillbox.web.dto;

import javax.validation.constraints.*;

public class Book {
    private Integer id;
    @NotBlank
    @Size(max = 125)
    @Pattern(regexp = "[\\w -]+")
    private String author;
    @NotBlank
    @Size(max = 125)
    //Название может быть любым - и с цифрами, и с чем угодно, поэтому не буду устанавливать ограничений, кроме размера
    private String title;
    @NotNull
    @Digits(integer = 4, fraction = 0)
    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                '}';
    }
}
