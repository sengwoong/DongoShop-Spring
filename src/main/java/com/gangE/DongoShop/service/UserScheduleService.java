package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.Schedule;
import com.gangE.DongoShop.model.UserSchedule;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.ScheduleRepository;
import com.gangE.DongoShop.repository.UserScheduleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserScheduleService {


    @PersistenceContext
    private EntityManager entityManager;

    private final CustomerService customerService;
    private UserScheduleRepository userScheduleRepository;

    private ScheduleRepository scheduleRepository;

    @Autowired
    UserScheduleService( CustomerService customerService ,UserScheduleRepository userScheduleRepository ,ScheduleRepository scheduleRepository) {
        this.userScheduleRepository = userScheduleRepository;
        this.scheduleRepository =scheduleRepository;
        this.customerService =customerService;
    }

    @Transactional
    public Schedule createSchedule(Schedule schedule) {
        // 스케줄 생성
        Schedule createdSchedule = scheduleRepository.save(schedule);

        // 유저 스케줄 생성
        UserSchedule userSchedule = new UserSchedule();
        userSchedule.setUser( customerService.getCurrentCustomer());
        userSchedule.setSchedule(createdSchedule);
        userScheduleRepository.save(userSchedule);

        return createdSchedule;
    }

    public Schedule updateSchedule(int schedule_id, Schedule newScheduleData) {
        // 해당 스케줄이 유저의 것인지 확인
        UserSchedule userSchedule = userScheduleRepository.findByScheduleIdAndUserId(schedule_id,  customerService.getCurrentCustomer().getId());
        if (userSchedule == null) {
            throw new IllegalArgumentException("User does not have permission to update this schedule");
        }

        // 스케줄 업데이트
        Schedule updatedSchedule = scheduleRepository.findById(schedule_id)
                .map(schedule -> {
                    schedule.setStartTime(newScheduleData.getStartTime());
                    schedule.setEndTime(newScheduleData.getEndTime());
                    schedule.setDescription(newScheduleData.getDescription());
                    return scheduleRepository.save(schedule);
                })
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + schedule_id));

        return updatedSchedule;
    }


    public void deleteSchedule(int schedule_id) {
        // 해당 스케줄이 유저의 것인지 확인
        UserSchedule userSchedule = userScheduleRepository.findByScheduleIdAndUserId(schedule_id,  customerService.getCurrentCustomer().getId());
        if (userSchedule == null) {
            throw new IllegalArgumentException("User does not have permission to delete this schedule");
        }

        // 스케줄 삭제
        scheduleRepository.deleteById(schedule_id);
    }





    public List<Schedule> getAllMyScheduleByName() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return entityManager.createQuery(
                        "SELECT us.schedule FROM UserSchedule us WHERE us.user.name = :username", Schedule.class)
                .setParameter("username", username)
                .getResultList();
    }



}