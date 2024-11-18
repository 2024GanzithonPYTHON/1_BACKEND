package com.ganzithon.go_farming.bookmark;
import com.ganzithon.go_farming.bookmark.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> { // Folder 엔티티와 기본키 타입 Long을 기반으로 한 JPA Repository
    List<Folder> findByUserUserId(Long userId);
    // User 엔티티의 userId를 조건으로 특정 사용자의 폴더 리스트를 찾는 메서드
}