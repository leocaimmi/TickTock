package com.TickTock.TickTock.data.repositories;

import com.TickTock.TickTock.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE FUNCTION('DAY', u.bornDate) = :day AND FUNCTION('MONTH', u.bornDate) = :month")
    List<UserEntity> findUsersWithBirthdayToday(@Param("day") int day, @Param("month") int month);
}
