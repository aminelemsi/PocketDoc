package pocketDock.com.pocketDock.service.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pocketDock.com.pocketDock.service.Interfaces.IServiceMapbox;

import java.io.IOException;
import java.net.URL;

@Service
public class ServiceMapBox implements IServiceMapbox {
    private final String MAPBOX_API_BASE_URL = "https://api.mapbox.com";
    private final String MAPBOX_API_TOKEN = "pk.eyJ1IjoiYW1pbmVqdXZlMTAiLCJhIjoiY2x2Y3drOWFnMG1zNDJxbGhhbmZmaHg4MiJ9.nhRByaxU-a3nW2nr55Y68w";


    @Override
    public String getAddressFromCoordinates(Double longitude, Double latitude) throws IOException {
        String apiUrl = MAPBOX_API_BASE_URL + "/geocoding/v5/mapbox.places/" + longitude + "," + latitude + ".json?access_token=" + MAPBOX_API_TOKEN;
        URL url = new URL(apiUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree(url);

        JsonNode features = response.get("features");
        if (features.isArray() && features.size() > 0) {
            JsonNode firstFeature = features.get(0);
            JsonNode properties = firstFeature.get("properties");
            String street = properties.has("address") ? properties.get("address").asText() : "N/A";
            String postalCode = properties.has("postcode") ? properties.get("postcode").asText() : "N/A";
            String placeName = firstFeature.has("place_name") ? firstFeature.get("place_name").asText() : "N/A";

            String detailedAddress = placeName + ", " + street + ", " + postalCode;
            return detailedAddress;
        } else {
            return "Address not found";
        }
    }

}