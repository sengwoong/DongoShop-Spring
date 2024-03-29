package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.Alarm;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.repository.AlarmRepository;
import com.gangE.DongoShop.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AlarmService {
    private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);

    private final AlarmRepository alarmRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository, CustomerRepository customerRepository,CustomerService customerService) {
        this.alarmRepository = alarmRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }



    public Page<Alarm> findAllByOrderByUserIdCreatedAtDesc(Pageable pageable) {
        return alarmRepository.findAllByUserIdOrderByCreatedAtDesc(pageable, customerService.getCurrentCustomer().getId());
    }

    public Optional<Alarm> createAlarm(int userId, Alarm alarm) {
        Optional<Customer> optionalCustomer = customerRepository.findById((long) userId);
        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }

        Customer customer = optionalCustomer.get();
        alarm.setUser(customer);
        return Optional.of(alarmRepository.save(alarm));
    }

    public void deleteAlarm(int alarmId) {

        Optional<Alarm> existingAlarmOptional = alarmRepository.findById((long) alarmId);
        if (existingAlarmOptional.isEmpty()) {
            return ;
        }

        Alarm existingAlarm = existingAlarmOptional.get();
        Customer currentCustomer = customerService.getCurrentCustomer();



        // 현재 사용자와 알람의 유저 ID가 다른 경우 에러 처리 또는 예외 처리
        if (existingAlarm.getUser().getId() != currentCustomer.getId()) {
            logger.error("Trying to update alarm belonging to another user");
            return ;
        }

        alarmRepository.deleteById((long) alarmId);
    }


}
