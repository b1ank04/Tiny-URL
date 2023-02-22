package org.blank.urlshortener.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class UrlErrorResponseDto {
    private String status;
    private String error;
}
