package ru.sladkkov.ChatSimbirSoft.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

public class Search {

    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;

    private static YouTube youtube;


    public ResponseEntity<String> getyoutubeitemfull_details(String nameVideo) throws SQLException, IOException{
        try {
            YouTube youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("APP_ID").build();

            String apiKey = "AIzaSyB898JYTPcaXpyu4SCdQ8LuVlGFvUXZ19w"; // you can get it from https://console.cloud.google.com/apis/credentials
            YouTube.Videos.List listVideosRequest = youtube.videos().list("statistics");
            listVideosRequest.setId(nameVideo); // add list of video IDs here
            listVideosRequest.setKey(apiKey);
            VideoListResponse listResponse = listVideosRequest.execute();

            Video video = listResponse.getItems().get(0);

            BigInteger viewCount = video.getStatistics().getViewCount();
            BigInteger Likes = video.getStatistics().getLikeCount();
            BigInteger Comments = video.getStatistics().getCommentCount();
            return ResponseEntity.ok("[View Count] = " + viewCount + "[Likes] = " + Likes + "[Comments] = " + Comments);

        } catch (GoogleJsonResponseException e) {
        } catch (IOException e) {
        } catch (Throwable t) {
        }
        return null;
    }
}


