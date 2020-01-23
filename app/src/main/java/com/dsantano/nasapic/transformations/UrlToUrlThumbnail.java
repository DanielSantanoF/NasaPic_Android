package com.dsantano.nasapic.transformations;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlToUrlThumbnail {
    private String url;

    public String urlToThumbnail(){
        String urlThumbnailYoutube = url.split("/")[4];
        String thumbnailId = urlThumbnailYoutube.split("\\?")[0];
        return "https://img.youtube.com/vi/" + thumbnailId +"/hqdefault.jpg";
    }
}
