package ru.practicum.events.event.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.events.event.dto.EventAdminSearchDto;
import ru.practicum.events.event.dto.EventPublicSearchDto;
import ru.practicum.events.event.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepositoryCustomImpl implements EventRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> findByParamsCommon(EventPublicSearchDto searchDto) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> eventRoot = query.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (searchDto.getText() != null) {
            predicates.add(builder.or(
                    builder.like(eventRoot.get("annotation"), "%" + searchDto.getText() + "%"),
                    builder.like(eventRoot.get("description"), "%" + searchDto.getText() + "%")
            ));
        }
        if (searchDto.getCategories() != null) {
            predicates.add(eventRoot.get("category").in(searchDto.getCategories()));
        }
        if (searchDto.getPaid() != null) {
            predicates.add(builder.equal(eventRoot.get("paid"), searchDto.getPaid()));
        }
        if (searchDto.getRangeStart() != null) {
            predicates.add(builder.greaterThanOrEqualTo(eventRoot.get("eventDate"), searchDto.getRangeStart()));
        }
        if (searchDto.getRangeEnd() != null) {
            predicates.add(builder.lessThanOrEqualTo(eventRoot.get("eventDate"), searchDto.getRangeEnd()));
        }
        if (searchDto.getOnlyAvailable()) {
            predicates.add(builder.or(builder.equal(eventRoot.get("participant_limit"), 0),
                    builder.greaterThan(eventRoot.get("participant_limit"), eventRoot.get("confirmed_requests"))));
        }
        query.select(eventRoot).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(builder.asc(eventRoot.get("eventDate")));
        return entityManager.createQuery(query)
                .setFirstResult(searchDto.getFrom())
                .setMaxResults(searchDto.getSize())
                .getResultList();
    }

    @Override
    public List<Event> findByParamsForAdmin(EventAdminSearchDto searchDto) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> eventRoot = query.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (searchDto.getUsers() != null) {
            predicates.add(eventRoot.get("initiator").in(searchDto.getUsers()));
        }
        if (searchDto.getStates() != null) {
            predicates.add(eventRoot.get("state").in(searchDto.getStates()));
        }
        if (searchDto.getCategories() != null) {
            predicates.add(eventRoot.get("category").in(searchDto.getCategories()));
        }
        if (searchDto.getRangeStart() != null) {
            predicates.add(builder.greaterThanOrEqualTo(eventRoot.get("eventDate"), searchDto.getRangeStart()));
        }
        if (searchDto.getRangeEnd() != null) {
            predicates.add(builder.lessThanOrEqualTo(eventRoot.get("eventDate"), searchDto.getRangeEnd()));
        }
        query.select(eventRoot).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        return entityManager.createQuery(query)
                .setFirstResult(searchDto.getFrom())
                .setMaxResults(searchDto.getSize())
                .getResultList();
    }
}

