package com.ganzithon.go_farming.user;

//import com.ganzithon.go_farming.bookmark.Folder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Lombok을 사용해 getter, setter, toString 등을 자동 생성
@Builder // 빌더 패턴 사용을 위해 추가
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 자동 생성
@Entity // JPA 엔티티 클래스임을 선언
@Table(name = "USER") // DB 테이블명을 지정
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 ID
    @Column(name = "user_id") // DB 컬럼명 지정
    private Long userId;

    @Column(nullable = false, unique = true) // unique 및 null 불가 설정
    private String username;

    @Column(nullable = false) // null 불가 설정
    private String password;

    @Column(nullable = false, unique = true) // unique 및 null 불가 설정
    private String nickname;

    @Column(name = "profile_picture")
    private String profilePicture; // 프로필 사진 URL (옵션)

    @Column(name = "age_group")
    private int ageGroup; // 연령대

    private String region; // 지역 정보

   /* @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> folders; // 사용자가 생성한 폴더 */
}

