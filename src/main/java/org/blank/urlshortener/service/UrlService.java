package org.blank.urlshortener.service;

import org.blank.urlshortener.model.api.UrlDto;
import org.blank.urlshortener.model.Url;

import java.util.Optional;

public interface UrlService {
    Optional<Url> generateShortLink(UrlDto urlDto);
    Url persistShortLink(Url entity);
    Optional<Url> getEncodedUrl(String url);
    void deleteShortLink(Url entity);
}
