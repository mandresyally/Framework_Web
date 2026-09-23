package main.java.util;

import java.util.Locale;
import java.util.Objects;

public final class UrlMappingKey {
    private final String url;
    private final String httpMethod;

    public UrlMappingKey(String url, String httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod.toUpperCase(Locale.ROOT);
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UrlMappingKey)) {
            return false;
        }
        UrlMappingKey that = (UrlMappingKey) other;
        return url.equals(that.url) && httpMethod.equals(that.httpMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, httpMethod);
    }
}
