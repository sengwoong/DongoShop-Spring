package com.gangE.DongoShop.service;

import com.gangE.DongoShop.dto.PointDto;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Point;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    private final PointRepository pointRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PointService(PointRepository pointRepository, CustomerRepository customerRepository) {
        this.pointRepository = pointRepository;
        this.customerRepository = customerRepository;
    }

    // 현재 인증된 고객의 포인트 반환
    public Point getPointsByCustomerId() {
        // 현재 인증된 고객 가져오기
        Customer customer =  customerRepository.getCurrentCustomer();
        if (customer != null) {
            // 해당 고객의 포인트 조회 및 반환
            return pointRepository.findByUser(customer);
        }
        return null;
    }

    // 현재 인증된 고객의 포인트 증가
    public int addMyWallet(PointDto pointsToAdd) {
        // 유효하지 않은 포인트 추가 시도 처리
        if (pointsToAdd.getMoney() <= 0) {
            throw new IllegalArgumentException("포인트 충전만 가능합니다.");
        }
        // 현재 인증된 고객의 포인트 가져오기
        Point point = getPointsByCustomerId();
        // 포인트 추가 후 저장
        point.setPoint(point.getPoint() + pointsToAdd.getMoney());
        pointRepository.save(point);

        return point.getPoint();
    }

    // 현재 인증된 고객의 포인트 감소
    public int subtractMyWallet(PointDto pointsToSubtract) {

        // 유효하지 않은 포인트 감소 시도 처리
        if (pointsToSubtract.getMoney() <= 0) {
            throw new IllegalArgumentException("감소할 포인트를 입력을 해 주세요.");
        }
        // 현재 인증된 고객의 포인트 가져오기
        Point point = getPointsByCustomerId();
        // 포인트 감소 후 저장

        if(point.getPoint() - pointsToSubtract.getMoney() <0){
            throw new IllegalArgumentException("포인트가 부족 합니다.");
        }
        point.setPoint(point.getPoint() - pointsToSubtract.getMoney());
        pointRepository.save(point);

        return point.getPoint();
    }

    // 현재 인증된 사용자 정보 가져오기

}
