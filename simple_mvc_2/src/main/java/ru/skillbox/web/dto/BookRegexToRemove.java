package ru.skillbox.web.dto;

import javax.validation.constraints.NotBlank;

public class BookRegexToRemove {

    @NotBlank
    private String regex;

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
