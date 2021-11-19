package com.soa.schedule.repository;

import com.soa.schedule.model.Schedule;
import com.soa.schedule.model.SchedulePrimary;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, SchedulePrimary> {

}
