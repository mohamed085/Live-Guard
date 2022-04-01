package com.liveguard.repository;

import com.liveguard.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void findAllByChipId() {
    }

    @Test
    void findAllByChipIdAndAddByUserId() {
        List<Task> tasks = taskRepository.findAllByChipIdAndAddByUserId(1L, 1L);
        tasks.forEach(task -> System.out.println(task.toString()));
        assertNotNull(tasks);
    }

    @Test
    void findOtherTasks() {
        List<Task> tasks = taskRepository.findOtherTasks(1L, 1L);
        tasks.forEach(task -> System.out.println(task.toString()));
        assertNotNull(tasks);
    }

    @Test
    void testFindAllByChipId() {
    }

    @Test
    void testFindAllByChipIdAndAddByUserId() {
    }

    @Test
    void testFindOtherTasks() {
    }

    @Test
    void test1() {
        Long userId = 2L;
        Long chipId = 1L;

        taskRepository.findAllTasksInITaskDTO(userId, chipId).forEach(test -> {
            System.out.println(test.getId());
            System.out.println(test.getArea());
            System.out.println(test.getCreate_date());
            System.out.println(test.getDescription());
            System.out.println(test.getStart_time());
            System.out.println(test.getEnd_time());
            System.out.println(test.getLng());
            System.out.println(test.getLat());
            System.out.println(test.getName());
            System.out.println(test.getRingtone());
            System.out.println(test.getChip_id());
            System.out.println(test.getAdd_by_user_id());
            System.out.println(test.getStatus());
            System.out.println(test.getEmail());
            System.out.println(test.getUser_name());
            System.out.println(test.getAbout());
            System.out.println(test.getAvatar());
            System.out.println(test.getChip_name());
            System.out.println(test.getPhoto());
        });
    }

    @Test
    void findAllRepeatDaysInTask() {

    }
}