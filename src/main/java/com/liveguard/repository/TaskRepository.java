package com.liveguard.repository;

import com.liveguard.domain.Task;
import com.liveguard.dto.ITaskDTO;
import com.liveguard.dto.ITaskRepeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByChipId(Long id);

    List<Task> findAllByChipIdAndAddByUserId(Long chipId, Long userId);

    @Query("SELECT t FROM Task t WHERE t.chip.id = ?1 AND t.addByUser.id <> ?2")
    List<Task> findOtherTasks(Long chipId, Long userId);

    @Query(value = "SELECT tasks.id, tasks.area, tasks.create_date, tasks.description, tasks.start_time, tasks.end_time, tasks.lng, tasks.lat, tasks.name, tasks.ringtone, tasks.add_by_user_id, tasks.chip_id,\n" +
            "users.email, users.name As user_name , users.about, users.avatar,\n" +
            "chips.name AS chip_name, chips.photo,\n" +
            "users_tasks_mute.status\n" +
            "FROM tasks\n" +
            "INNER JOIN users_tasks_mute ON tasks.id = users_tasks_mute.task_id\n" +
            "INNER JOIN users ON tasks.add_by_user_id = users.id\n" +
            "INNER JOIN chips ON tasks.chip_id = chips.id\n" +
            "WHERE users_tasks_mute.user_id = :userId AND tasks.id = :taskId ;",
            nativeQuery = true)
    Optional<ITaskDTO> findByIdInITaskDTO(@Param("taskId") Long taskId, @Param("userId") Long userId);

    @Query(value = "SELECT days.id, days.day FROM days\n" +
            "INNER JOIN task_repeat ON days.id = task_repeat.day_id\n" +
            "WHERE task_repeat.task_id = :taskId",
            nativeQuery = true)
    List<ITaskRepeat> findAllRepeatDaysInTask(@Param("taskId") Long taskId);

    @Query(value = "SELECT tasks.id, tasks.area, tasks.create_date, tasks.description, tasks.start_time, tasks.end_time, tasks.lng, tasks.lat, tasks.name, tasks.ringtone, tasks.add_by_user_id, tasks.chip_id,\n" +
            "users.email, users.name As user_name , users.about, users.avatar,\n" +
            "chips.name AS chip_name, chips.photo,\n" +
            "users_tasks_mute.status\n" +
            "FROM tasks\n" +
            "INNER JOIN users_tasks_mute ON tasks.id = users_tasks_mute.task_id\n" +
            "INNER JOIN users ON tasks.add_by_user_id = users.id\n" +
            "INNER JOIN chips ON tasks.chip_id = chips.id\n" +
            "WHERE tasks.chip_id = :chipId AND users_tasks_mute.user_id = :userId ;",
            nativeQuery = true)
    List<ITaskDTO> findAllTasksInITaskDTO(@Param("userId") Long userId, @Param("chipId") Long chipId);


    @Query(value = "SELECT tasks.id, tasks.area, tasks.create_date, tasks.description, tasks.start_time, tasks.end_time, tasks.lng, tasks.lat, tasks.name, tasks.ringtone, tasks.add_by_user_id, tasks.chip_id,\n" +
            "users.email, users.name As user_name , users.about, users.avatar,\n" +
            "chips.name AS chip_name, chips.photo,\n" +
            "users_tasks_mute.status\n" +
            "FROM tasks\n" +
            "INNER JOIN users_tasks_mute ON tasks.id = users_tasks_mute.task_id\n" +
            "INNER JOIN users ON tasks.add_by_user_id = users.id\n" +
            "INNER JOIN chips ON tasks.chip_id = chips.id\n" +
            "WHERE tasks.chip_id = :chipId AND users_tasks_mute.user_id = :userId AND tasks.add_by_user_id = :userId ;",
            nativeQuery = true)
    List<ITaskDTO> findMyTasksInITaskDTO(@Param("userId") Long userId, @Param("chipId") Long chipId);

    @Query(value = "SELECT tasks.id, tasks.area, tasks.create_date, tasks.description, tasks.start_time, tasks.end_time, tasks.lng, tasks.lat, tasks.name, tasks.ringtone, tasks.add_by_user_id, tasks.chip_id,\n" +
            "users.email, users.name As user_name , users.about, users.avatar,\n" +
            "chips.name AS chip_name, chips.photo,\n" +
            "users_tasks_mute.status\n" +
            "FROM tasks\n" +
            "INNER JOIN users_tasks_mute ON tasks.id = users_tasks_mute.task_id\n" +
            "INNER JOIN users ON tasks.add_by_user_id = users.id\n" +
            "INNER JOIN chips ON tasks.chip_id = chips.id\n" +
            "WHERE tasks.chip_id = :chipId AND users_tasks_mute.user_id = :userId AND tasks.add_by_user_id <> :userId ;",
            nativeQuery = true)
    List<ITaskDTO> findOtherTasksInITaskDTO(@Param("userId") Long userId, @Param("chipId") Long chipId);


}
