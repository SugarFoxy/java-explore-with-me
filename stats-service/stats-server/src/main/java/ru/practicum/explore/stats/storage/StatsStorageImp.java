package ru.practicum.explore.stats.storage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explore.dto.StatsDto;
import ru.practicum.explore.hit.model.Hit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class StatsStorageImp implements StatsStorage {
    @PersistenceContext()
    private final EntityManager entityManager;

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, String uri, boolean unique) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatsDto> query = builder.createQuery(StatsDto.class);
        Root<Hit> root = query.from(Hit.class);
        query.multiselect(root.get("app"), root.get("uri"), unique ? builder.countDistinct(root.get("ip")) : builder.count(root));
        query.groupBy(root.get("app"), root.get("uri"));
        if (uri == null) {
            query.where(builder.between(root.get("timestamp"),start,end));
        } else {
            query.where(builder.and(builder.between(root.get("timestamp"),start,end),
                    builder.equal(root.get("uri"), uri)));
        }
        TypedQuery<StatsDto> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}