package pocketDock.com.pocketDock.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceBadworld implements IServicebad{
    @Override
    public ResponseEntity<List<String>> scrapeWebsite() {
        List<String> links = new ArrayList<>();                        //extraire des liens et des titres à partir d'une page Web
        try {
            // URL eli nheb ne5o mino element
            String url = "https://www.facebook.com";
            // connecti ale url
            Document doc = Jsoup.connect(url).get();
            // ye5oliili kol element tag link <a> hiya w <h1>
            Elements linkElements = doc.select("a");
            Elements div = doc.select("h1");          //extraire les éléments <a> (liens) et <h1> (titres).
            // yzidehom f list fo9aniya
            for (Element link : linkElements) {
                String linkUrl = link.absUrl("href");
                links.add(linkUrl);
            }
            // yejibli kool title f nafs page
            Elements alltitle = doc.select("h1, h2, h3, h4, h5, h6");
            for (Element e : alltitle) {
                String title = e.text();
                links.add("title : " + title);
            }
            //yerje3hom list
            return ResponseEntity.ok(links);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // Internal Server Error
        }
    }

    @Override
    public ResponseEntity<String> filterBadWords1(String text) {
        try {
            // API link mte3ha
            String url = "https://api.apilayer.com/bad_words?censor_character=censor_character";

            // key
            String apiKey = "hTszekeLoGbOnrSFn9Bjvf9FEJE1LcEQ";

            // connection
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // t7ot  method           //envoie requête POST à l'API avec le texte à filtrer.
            con.setRequestMethod("POST");

            // w nzideha apikey f headers bch yevalidli request
            con.setRequestProperty("apikey", apiKey);
            con.setRequestProperty("Content-Type", "text/plain");

            // te7oteha true bch najem nekteb f body
            con.setDoOutput(true);

            // i3abi  request body mte3ii
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(text);
            wr.flush();
            wr.close();

            // Get response code eli jetna
            int responseCode = con.getResponseCode();

            // read body eli jena
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder responseBody = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine);
            }
            in.close();

            // Return response
            return ResponseEntity.status(responseCode).body(responseBody.toString());
        } catch (Exception e) {
            // Return error response if any exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public Mono<ResponseEntity<String>> filterBadWords(String text) {    //utilise WebClient de Spring pour effectuer la requête à l'API.
        WebClient webClient = WebClient.create();

        return webClient.post()
                .uri("https://api.apilayer.com/bad_words?censor_character=censor_character")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .header("apikey", "vFmYBcb4CMB66bhFzl2Xk70K0W2l8WaE")
                .body(BodyInserters.fromValue(text))
                .exchange()
                .flatMap(response -> response.toEntity(String.class));
    }
}