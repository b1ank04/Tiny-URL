package org.blank.urlshortener.controller;

import org.blank.urlshortener.model.api.UrlDto;
import org.blank.urlshortener.model.Url;
import org.blank.urlshortener.model.api.UrlErrorResponseDto;
import org.blank.urlshortener.model.api.UrlResponseDto;
import org.blank.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
public class UrlShorteningController {
    private final UrlService urlService;

    @Value("${server.host:http://localhost}")
    private String serverHost;

    @Value("${server.port}")
    private int serverPort;

    public UrlShorteningController(UrlService urlService) {
        this.urlService = urlService;
    }
    @PostMapping("/api/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto) {
        Optional<Url> url = urlService.generateShortLink(urlDto);
        if (url.isPresent()) {
            UrlResponseDto urlResponseDto = new UrlResponseDto(url.get().getLongLink(), String.format("%s:%d/%s", serverHost, serverPort, url.get().getShortLink()));
            return new ResponseEntity<>(urlResponseDto, HttpStatus.OK);
        } else {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto("404",
                    "There was an error processing your request. Please, try again");
            return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
        }
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirectToOriginalLink(@PathVariable String shortLink, HttpServletResponse response) throws IOException {
        if (StringUtils.isNotEmpty(shortLink)) {
            Optional<Url> url = urlService.getEncodedUrl(shortLink.substring(shortLink.lastIndexOf("/")+1));
            if (url.isEmpty()) {
                UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto("Url doesn't exist", "400");
                return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
            }
            response.sendRedirect(url.get().getLongLink());
            return new ResponseEntity<>("Redirecting...", HttpStatus.OK);
        } else {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto("Invalid Url", "400");
            return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
        }
    }
}
