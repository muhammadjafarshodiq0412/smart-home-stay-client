package com.smarthomestayclient.controller;

import com.smarthomestayclient.model.GeneralResponse;
import com.smarthomestayclient.model.Room;
import com.smarthomestayclient.model.request.AddRoomRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
   Logger log =  LoggerFactory.getLogger(this.getClass());

    //call from application properties
    @Value("${smart.home.stay.base.url}")
    private String smartHomeStayBaseUrl;

    HttpClient httpClient = HttpClient.newHttpClient();

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/httpclient/get-all-room")
    public String getAllRoomHttpClient() {
        URI uri = URI.create(smartHomeStayBaseUrl.concat("/room"));

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "FAILED";
        }
    }

    @GetMapping("/resttempalte/get-all-room")
    public String getAllRoomRestTemplate() {
        try {
            String url = smartHomeStayBaseUrl.concat("/room");
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "FAILED";
        }
    }

    @GetMapping("/resttempalte-pojo/get-all-room")
    public GeneralResponse<List<Room>> getAllRoomRestTemplatePojoResponse() {
        log.info("incoming request get all room");
        try {
            String url = smartHomeStayBaseUrl.concat("/room");
            ParameterizedTypeReference<GeneralResponse<List<Room>>> responseType =
                    new ParameterizedTypeReference<>() {
                    };

            ResponseEntity<GeneralResponse<List<Room>>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            for (Room data : response.getBody().getData()) {
                log.info("status {}", data.getStatus());
            }
            return response.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/resttempalte/add")
    public GeneralResponse addRoom(@RequestBody AddRoomRequest addRoomRequest) {
        log.info("incoming request add room");
        try {
            String url = smartHomeStayBaseUrl.concat("/room/create");

            //set request body
//            AddRoomRequest addRoomRequest = new AddRoomRequest();
//            addRoomRequest.setRoomNumber(1);
//            addRoomRequest.setRoomType(2);
//            addRoomRequest.setCapacity("2");
//            addRoomRequest.setFloor("1");

            HttpEntity requestBody = new HttpEntity<>(addRoomRequest);
            //set response body
            ParameterizedTypeReference<GeneralResponse<Room>> responseBody = new ParameterizedTypeReference<>() {
            };
            //call api
            ResponseEntity<GeneralResponse<Room>> result = restTemplate.exchange(url, HttpMethod.POST, requestBody, responseBody);
            return result.getBody();
        } catch (Exception e) {
            log.error("error {}", e.getMessage());
            return null;
        }
    }
}
