package org.alumnusb.easypay.service;

import lombok.extern.slf4j.Slf4j;
import org.alumnusb.easypay.model.PaymentList;
import org.alumnusb.easypay.model.Schedule;
import org.alumnusb.easypay.repository.PaymentListRepository;
import org.alumnusb.easypay.repository.ScheduleRepository;
import org.alumnusb.easypay.request.CreateSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PaymentListRepository paymentListRepository;

    public Schedule create(CreateSchedule request) {

        PaymentList paymentList = paymentListRepository.getById(request.getPaymentListId())
                .orElseThrow(() -> new NoSuchElementException("Payment List not found"));

        Schedule schedule = Schedule.builder()
                .active(request.getActive())
                .description(request.getDescription())
                .paymentList(paymentList)
                .periodicity(request.getPeriodicity())
                .build();

        return scheduleRepository.save(schedule);
    }

    public Schedule getById(Long id) {
        return scheduleRepository.getById(id).orElseThrow(() -> new NoSuchElementException("Schedule not found"));
    }

    public Page<Schedule> getAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable);
    }

}
