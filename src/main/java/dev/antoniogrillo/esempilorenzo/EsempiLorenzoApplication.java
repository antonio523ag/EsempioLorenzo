package dev.antoniogrillo.esempilorenzo;

import dev.antoniogrillo.esempilorenzo.dto.DomandeWrap;
import dev.antoniogrillo.esempilorenzo.dto.SingolaDomanda;
import dev.antoniogrillo.esempilorenzo.service.DomandaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EsempiLorenzoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext c= SpringApplication.run(EsempiLorenzoApplication.class, args);
        DomandaService domandaService = c.getBean(DomandaService.class);
        DomandeWrap d=domandaService.getDomandeWrap();
        for(SingolaDomanda s:d.getDomande()){
            System.out.println(s);
        }
    }

}
