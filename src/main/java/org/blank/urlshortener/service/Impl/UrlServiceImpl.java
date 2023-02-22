package org.blank.urlshortener.service.Impl;

import org.blank.urlshortener.model.api.UrlDto;
import org.blank.urlshortener.model.Url;
import org.blank.urlshortener.repository.UrlRepository;
import org.blank.urlshortener.service.UidGenerator;
import org.blank.urlshortener.service.UrlService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UidGenerator uidGenerator;

    public UrlServiceImpl(UrlRepository urlRepository, UidGenerator uidGenerator) {
        this.urlRepository = urlRepository;
        this.uidGenerator = uidGenerator;
    }

    @Override
    public Optional<Url> generateShortLink(UrlDto urlDto) {
        String encodedUrl = encodeSlug(urlDto.getUrl());
        Url url = new Url();
        url.setLongLink(urlDto.getUrl());
        url.setShortLink(encodedUrl);
        Url response = persistShortLink(url);
        return Optional.ofNullable(response);
    }

    public String encodeSlug(String url) {
        String encodedSlug = "";
        do {
            encodedSlug = uidGenerator.generate(url);
        } while (getEncodedUrl(encodedSlug).isPresent());
        return encodedSlug;
    }

    @Override
    public Url persistShortLink(Url entity) {
        return urlRepository.save(entity);
    }

    @Override
    public Optional<Url> getEncodedUrl(String slug) {
        return Optional.ofNullable(urlRepository.findByShortLink(slug));
    }

    @Override
    public void deleteShortLink(Url entity) {
        urlRepository.delete(entity);
    }
}
