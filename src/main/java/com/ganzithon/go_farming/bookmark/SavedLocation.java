package com.ganzithon.go_farming.bookmark;

//import com.ganzithon.go_farming.location.Location; // 다른 패키지의 Location 엔티티를 참조
import com.ganzithon.go_farming.bookmark.Folder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SAVED_LOCATION") // 매핑할 테이블 이름 지정
public class SavedLocation {

    @Id // 기본키(primary key) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 생성 방식 지정
    @Column(name = "saved_location_id") // 테이블 컬럼 매핑
    private Long savedLocationId;

    @ManyToOne // Folder와 다대일 관계 설정
    @JoinColumn(name = "folder_id", nullable = false) // 외래키 컬럼(folder_id) 설정
    private Folder folder;

    /*@ManyToOne // Location과 다대일 관계 설정
    @JoinColumn(name = "location_id", nullable = false) // 외래키 컬럼(location_id) 설정
    private Location location; // 다른 패키지의 Location 엔티티 참조*/

}
