package dev.antoniogrillo.esempilorenzo.repository;

import dev.antoniogrillo.esempilorenzo.entity.Categoria;
import dev.antoniogrillo.esempilorenzo.entity.Domanda;
import dev.antoniogrillo.esempilorenzo.entity.Risposta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CriteriaRepository {

    private final EntityManager manager;

    public List<Domanda> getByParameter(Map<String,String> parameter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Domanda> criteria = builder.createQuery(Domanda.class);
        Root<Domanda> root = criteria.from(Domanda.class);
        List<Predicate> predicates = new ArrayList<>();
        for(String key : parameter.keySet()) {
            Predicate predicate = builder.equal(root.get(key),parameter.get(key));
            predicates.add(predicate);
        }
        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Domanda> query = manager.createQuery(criteria);
        List<Domanda> domandas = query.getResultList();
        //Domanda domanda = query.getSingleResult();
        return domandas;
    }

    public List<Domanda> getDomandeByParameter(List<ParametriQuery> parameter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        //TODO testare
        //CriteriaQuery<Tuple> query = builder.createTupleQuery();
        CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
        Root<Domanda> root = criteria.from(Domanda.class);
        Join<Domanda, Categoria> categoriaJoin = root.join("categoria");
        Join<Domanda, Risposta> rispostaJoin = root.join("risposte");
        List<Predicate> predicates = new ArrayList<>();
        for(ParametriQuery parametriQuery : parameter) {
            switch (parametriQuery.tabella.toLowerCase().trim()){
                case "domanda":
                    Predicate predicate = builder.equal(root.get(parametriQuery.nomeColonna),parametriQuery.predicatoQuery);
                    predicates.add(predicate);
                    break;
                case "risposta":
                    predicate =builder.equal(rispostaJoin.get(parametriQuery.nomeColonna),parametriQuery.predicatoQuery);
                    predicates.add(predicate);
                    break;
                case "categoria":
                    predicate = builder.equal(categoriaJoin.get(parametriQuery.nomeColonna),parametriQuery.predicatoQuery);
                    predicates.add(predicate);
            }
        }
        criteria.multiselect(root,rispostaJoin,categoriaJoin).where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Tuple> query = manager.createQuery(criteria);
        List<Tuple> tuples = query.getResultList();
        List<Domanda> domandas = tuples.stream().map(t->t.get(0,Domanda.class)).toList();
        return domandas;
    }

    @Data
    public class ParametriQuery{
        private String tabella;
        private String nomeColonna;
        private String predicatoQuery;
    }
}
