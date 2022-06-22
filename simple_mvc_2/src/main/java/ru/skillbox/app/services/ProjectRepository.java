package ru.skillbox.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retrieveAll();

    void store(T book);

    boolean removeItemByString(String bookRegexToRemove);

//    boolean removeItemById(Integer id);
}
