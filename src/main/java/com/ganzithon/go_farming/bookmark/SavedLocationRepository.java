package com.ganzithon.go_farming.bookmark;
import com.ganzithon.go_farming.bookmark.SavedLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedLocationRepository extends JpaRepository<SavedLocation, Long> { // SavedLocation 엔티티와 기본키 타입 Long을 기반으로 한 JPA Repository

    // 특정 폴더 ID와 위치 ID를 기준으로 SavedLocation 삭제
//    void deleteByFolderFolderIdAndLocationLocationId(Long folderId, Long locationId);

    // 특정 폴더 ID와 위치 ID로 SavedLocation 존재 여부 확인
//    boolean existsByFolderFolderIdAndLocationLocationId(Long folderId, Long locationId);
}