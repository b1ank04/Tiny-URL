package org.blank.urlshortener.model.api;

import lombok.*;

@Data
@AllArgsConstructor
@ToString
public class UrlResponseDto {
    private String original;
    private String shorten;
}
