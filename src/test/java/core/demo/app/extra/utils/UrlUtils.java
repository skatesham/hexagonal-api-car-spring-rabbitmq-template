package core.demo.app.extra.utils;

import org.springframework.web.util.UriComponentsBuilder;

public class UrlUtils {

    public static final String HTTP_LOCALHOST_PORT = "http://localhost:{port}";

    public static String defaultUrl(int port) {
        return UriComponentsBuilder
                .fromUriString(HTTP_LOCALHOST_PORT)
                .path("/api/v1")
                .buildAndExpand(port)
                .toUriString();
    }

}