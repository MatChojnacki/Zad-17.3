package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.username}")
    private String trelloUsername;

    private String buildUrl(String path) {
        return trelloApiEndpoint + path;
    }

    public List<TrelloBoardDto> getTrelloBoards() {
            String url = buildUrl("/members/" + trelloUsername + "/boards");

            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
                    url + "?key=" + trelloAppKey + "&token=" + trelloToken + "&fields=name,id",
                    TrelloBoardDto[].class
            );

            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        }

    }
