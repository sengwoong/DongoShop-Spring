package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.Customer;
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


    private UserScheduleRepository userScheduleRepository;

    private ScheduleRepository scheduleRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    UserScheduleService(UserScheduleRepository userScheduleRepository ,ScheduleRepository scheduleRepository,CustomerRepository customerRepository) {
        this.userScheduleRepository = userScheduleRepository;
        this.scheduleRepository =scheduleRepository;
        this.customerRepository =customerRepository;
    }

    @Transactional
    public Schedule createSchedule(Schedule schedule) {
        // 스케줄 생성
        Schedule createdSchedule = scheduleRepository.save(schedule);

        // 유저 스케줄 생성
        UserSchedule userSchedule = new UserSchedule();
        userSchedule.setUser( customerRepository.getCurrentCustomer());
        userSchedule.setSchedule(createdSchedule);
        userScheduleRepository.save(userSchedule);

        return createdSchedule;
    }

    public Schedule updateSchedule(int id, Schedule newScheduleData) {
        // 해당 스케줄이 유저의 것인지 확인
        UserSchedule userSchedule = userScheduleRepository.findByScheduleIdAndUserId(id,  customerRepository.getCurrentCustomer().getId());
        if (userSchedule == null) {
            throw new IllegalArgumentException("User does not have permission to update this schedule");
        }

        // 스케줄 업데이트
        Schedule updatedSchedule = scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setStartTime(newScheduleData.getStartTime());
                    schedule.setEndTime(newScheduleData.getEndTime());
                    schedule.setDescription(newScheduleData.getDescription());
                    return scheduleRepository.save(schedule);
                })
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));

        return updatedSchedule;
    }


    public void deleteSchedule(int id) {
        // 해당 스케줄이 유저의 것인지 확인
        UserSchedule userSchedule = userScheduleRepository.findByScheduleIdAndUserId(id,  customerRepository.getCurrentCustomer().getId());
        if (userSchedule == null) {
            throw new IllegalArgumentException("User does not have permission to delete this schedule");
        }

        // 스케줄 삭제
        scheduleRepository.deleteById(id);
    }





    public List<Schedule> getAllMyScheduleByName() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return entityManager.createQuery(
                        "SELECT us.schedule FROM UserSchedule us WHERE us.user.name = :username", Schedule.class)
                .setParameter("username", username)
                .getResultList();
    }



}