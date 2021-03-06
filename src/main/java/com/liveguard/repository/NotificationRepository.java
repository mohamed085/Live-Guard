package com.liveguard.repository;

import com.liveguard.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

    Page<Notification> findAllByUserId(Long userId, Pageable pageable);
}
