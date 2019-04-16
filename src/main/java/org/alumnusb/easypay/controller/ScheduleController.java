package org.alumnusb.easypay.controller;

import lombok.RequiredArgsConstructor;
import org.alumnusb.easypay.model.Schedule;
import org.alumnusb.easypay.request.CreateSchedule;
import org.alumnusb.easypay.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule create(@Validated @RequestBody CreateSchedule request) {
        return scheduleService.create(request);
    }

    @GetMapping(value = "/{id}")
    public Schedule fetchScheduleById(@PathVariable(name = "id") Long id) {
        return scheduleService.getById(id);
    }

    @GetMapping
    public Page<Schedule> fetchAllItems(Pageable pageable) {
        return scheduleService.getAll(pageable);
    }

}
