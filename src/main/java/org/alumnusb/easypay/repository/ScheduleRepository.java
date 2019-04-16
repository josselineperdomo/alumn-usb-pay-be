package org.alumnusb.easypay.repository;

import org.alumnusb.easypay.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    Optional<Schedule> getById(Long id);
    Page<Schedule> findAll(Pageable pageable);
}
