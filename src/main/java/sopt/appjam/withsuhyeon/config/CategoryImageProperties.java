package sopt.appjam.withsuhyeon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "categories")
public class CategoryImageProperties {
    private Map<String, String> images;

    public String getImageUrl(String key) {
        return images.get(key);
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }
}
