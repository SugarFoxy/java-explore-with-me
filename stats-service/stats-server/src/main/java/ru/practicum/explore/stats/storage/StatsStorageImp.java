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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatsDto> cr = cb.createQuery(StatsDto.class);
        Root<Hit> root = cr.from(Hit.class);
        cr.multiselect(root.get("app"), root.get("uri"), unique ? cb.countDistinct(root.get("ip")) : cb.count(root));
        cr.groupBy(root.get("app"), root.get("uri"));
        if (uri == null) {
            cr.where(cb.between(root.get("timestamp"),start,end));
        } else {
            cr.where(cb.and(cb.between(root.get("timestamp"),start,end),
                    cb.equal(root.get("uri"), uri)));
        }
        TypedQuery<StatsDto> query = entityManager.createQuery(cr);
        return query.getResultList();
    }
}
