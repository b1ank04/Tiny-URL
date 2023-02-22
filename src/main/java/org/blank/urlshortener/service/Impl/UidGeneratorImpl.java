package org.blank.urlshortener.service.Impl;

import org.blank.urlshortener.service.UidGenerator;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class UidGeneratorImpl implements UidGenerator {
    @Override
    public String generate(String link) {
        return Hashing.md5()
                .hashString(link.concat(LocalDateTime.now().toString()), StandardCharsets.UTF_8)
                .toString().substring(0, 7);
    }
}
