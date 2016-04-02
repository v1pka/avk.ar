package ru.avk.service;


public interface VaadinUIService {
    void postNavigationEvent(Object source, String target);

    boolean isUserAnonymous();
}
