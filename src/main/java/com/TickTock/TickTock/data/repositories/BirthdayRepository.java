package com.TickTock.TickTock.data.repositories;

import com.TickTock.TickTock.data.entities.BirthdayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthdayRepository extends JpaRepository<BirthdayEntity, Long> {

}
