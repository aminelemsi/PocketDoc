package pocketDock.com.pocketDock.controller;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pocketDock.com.pocketDock.service.ServiceBadworld;
import reactor.core.publisher.Mono;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/bad")
public class BadWordsController
{
    @Autowired
    ServiceBadworld serviceBadworld;
    @GetMapping("/scrape")
    public ResponseEntity<List<String>> scrapeWebsite()
    {
        return serviceBadworld.scrapeWebsite();
    }

    @PostMapping("/text")
    public ResponseEntity<String> filterBadWords1(@RequestBody String text)
    {
        return serviceBadworld.filterBadWords1(text);
    }

    @PostMapping("/filterBadWords")
    public Mono<ResponseEntity<String>> filterBadWords(@RequestBody String text)
    {
        return serviceBadworld.filterBadWords(text);
    }
}
