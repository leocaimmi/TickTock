package com.TickTock.TickTock.birthday.domain.repositories;

import com.TickTock.TickTock.birthday.domain.entities.BirthdayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirthdayRepository extends JpaRepository<BirthdayEntity, Long> {
    @Query("""
                SELECT b FROM BirthdayEntity b
                JOIN FETCH b.userEntity u
                WHERE FUNCTION('MONTH', b.birthDate) = :month
                ORDER BY u.id, FUNCTION('DAY', b.birthDate)
            """)
    List<BirthdayEntity> findBirthdaysGroupedByUserInMonth(@Param("month") int month);

}
