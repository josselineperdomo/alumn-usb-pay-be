package org.alumnusb.easypay.controller;

import lombok.RequiredArgsConstructor;
import org.alumnusb.easypay.model.Schedule;
import org.alumnusb.easypay.request.CreateSchedule;
import org.alumnusb.easypay.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


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
