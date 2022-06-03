package ru.skillbox.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retrieveAll();

    void store(T book);

    void removeItemById(Integer bookIdToRemove);

    void removeItemByString(String bookRegexToRemove);
}
