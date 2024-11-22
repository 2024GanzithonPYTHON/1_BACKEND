package com.ganzithon.go_farming.bookmark;

import com.ganzithon.go_farming.common.domain.Place;
import com.ganzithon.go_farming.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "SAVED_FOLDER") // 데이터베이스에서 이 엔티티가 "SAVED_FOLDER"라는 테이블과 매핑됨
public class Folder {
    @Id // 이 필드가 테이블의 기본 키임을 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키가 자동으로 증가되도록 설정
    private Long folderId; // 폴더의 고유 ID

    private String folderName; // 폴더 이름

    private String color; // 폴더 색상 추가

    @ManyToOne // 여러Folder는 하나의User에 속할 수 있음 (다대일 관계)
    @JoinColumn(name = "user_id", nullable = false)
    // 데이터베이스에서 외래 키 이름을 "user_id"로 지정하고, null이 될 수 없도록 설정
    private User user; // 폴더를 소유한 사용자

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   // Folder와 SavedLocation은 일대다 관계, "folder" 필드로 양방향 매핑
    // CascadeType.ALL로 저장/삭제가 폴더에 속한 항목에도 적용됨
    // orphanRemoval = true로 고아 객체(폴더와 연결되지 않은 위치)가 자동 삭제됨
   private List<Place> savedLocations = new ArrayList<>(); // 폴더 안에 저장된 위치(Place)의 리스트를 초기화
}
