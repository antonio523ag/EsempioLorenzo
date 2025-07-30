package dev.antoniogrillo.esempilorenzo.service;

import dev.antoniogrillo.esempilorenzo.dto.DomandeWrap;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class DomandaService {

    public DomandeWrap getDomandeWrap() {
        String url="https://opentdb.com/api.php?amount=10&type=boolean";
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Authorization","sdfogihdfsgouishdfgoisd");
        //il generics nelle parentesi angolari è quello della request
        //se dobbiamo passare un body lo metteremo nel costruttore prima dell'headers
        HttpEntity<Void> entity=new HttpEntity<>(headers);
        RestTemplate restTemplate=new RestTemplate();
        try {
            ResponseEntity<DomandeWrap> response = restTemplate.exchange(url, HttpMethod.GET, entity, DomandeWrap.class);
            if(response.getStatusCode().is2xxSuccessful()){
                return response.getBody();
            }
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                System.out.println("che cazz di url hai messo?");
            }else if(e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
                System.out.println("si è rotto tutto!");
            }
        }
        return null;
    }

    private<P> HttpEntity<P> createHttpEntity(P parameter){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(parameter,headers);
    }

    private <P,R> R callGet(String path,P parameter,Class<R> classeDiRisposta){
        //questo oggetto sarà il "body" della request
        HttpEntity<P> requestEntity=createHttpEntity(parameter);
        //creo l'oggetto che mi permetterà di effettuare le chiamate
        //per quanto riguarda lo standard boot si usa il RestTemplate
        RestTemplate restTemplate=new RestTemplate();
        try{
            //il metodo del restTemplate per chiamare un servizio è il metodo exchange
            //e ritornerà una ResponseEntity di String
            ResponseEntity<R>  response=restTemplate.exchange(path, HttpMethod.GET,requestEntity, classeDiRisposta);
            //il metodo getBody prenderà il Json contenuto nel body
            return response.getBody();
            //se la chiamata ritorna un codice diverso da 2** andrà in exception
        }catch (HttpClientErrorException ex){
            System.out.println(ex.getStatusCode().value());
            System.out.println(ex.getMessage());
            return null;
        }
    }


}
