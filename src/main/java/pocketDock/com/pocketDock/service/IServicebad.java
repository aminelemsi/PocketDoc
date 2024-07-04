package pocketDock.com.pocketDock.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface IServicebad {
    public ResponseEntity<List<String>> scrapeWebsite();
    public ResponseEntity<String> filterBadWords1(String text);
    public Mono<ResponseEntity<String>> filterBadWords(String text);

}
