package com.liveguard.repository;

import com.liveguard.domain.Chip;
import com.liveguard.domain.ChipUser;
import com.liveguard.domain.ChipUserDetail;
import com.liveguard.domain.ChipUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChipUserRepository extends JpaRepository<ChipUser, Long> {

    boolean existsByUserIdAndChipKey(Long id, String key);

    List<ChipUser> findAllByUserId(Long id);
    boolean existsByUserIdAndId(Long userId, Long id);

    @Query("UPDATE ChipUser cu SET cu.name = ?2, cu.photo = ?3 WHERE cu.id = ?1")
    @Modifying
    void updateChipUserInfo(Long id, String name, String photo);

    @Query("UPDATE ChipUser cu SET cu.name = ?2 WHERE cu.id = ?1")
    @Modifying
    void updateChipUserName(Long id, String name);

    List<ChipUser> findAllByChipId(Long id);

    boolean existsByUserIdAndChipUserType(Long id, ChipUserType chipUserType);

    boolean existsByUserIdAndChipId(Long userId, Long chipId);

    void deleteByUserIdAndChipId(Long userId, Long chipId);

}
