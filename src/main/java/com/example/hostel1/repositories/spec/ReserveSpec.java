package com.example.hostel1.repositories.spec;


import com.example.hostel1.entities.Reserve;
import com.example.hostel1.entities.Room;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;

@Component
public class ReserveSpec {

    public Specification<Reserve> findByDatesIntervalAndRoom(Timestamp dateIn, Timestamp dateOut, Room room) {
        return new Specification<Reserve>() {
            @Override
            public Predicate toPredicate(
                    Root<Reserve> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicateDateIn = criteriaBuilder.between(
                        root.<Timestamp>get("dateIn"),
                        dateIn, dateOut);
                Predicate predicateDateOut = criteriaBuilder.between(
                        root.<Timestamp>get("dateOut"),
                        dateIn, dateOut);
                Predicate predicateOrDates = criteriaBuilder.or(
                        predicateDateIn, predicateDateOut
                );
                Predicate predicateRoom = criteriaBuilder.equal(
                        root.<Long>get("room"), room
                );
                return criteriaBuilder.and(
                        predicateOrDates, predicateRoom
                );
            }
        };

    }
}
