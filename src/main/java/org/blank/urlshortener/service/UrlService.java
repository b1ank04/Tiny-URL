package org.blank.urlshortener.service;

import org.blank.urlshortener.model.api.UrlDto;
import org.blank.urlshortener.model.Url;

import java.util.Optional;

public interface UrlService {
    public Optional<Url> generateShortLink(UrlDto urlDto);
    public Url persistShortLink(Url entity);
    public Optional<Url> getEncodedUrl(String url);
    public void deleteShortLink(Url entity);
}
