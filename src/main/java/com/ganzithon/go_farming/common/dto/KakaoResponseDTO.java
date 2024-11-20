package com.ganzithon.go_farming.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class KakaoResponseDTO {

    private List<Document> documents;

    @Data
    @Getter
    @Setter
    public static class Document {
        private String id;

        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("category_group_name")
        private String categoryGroupName;

        private String phone;

        @JsonProperty("road_address_name")
        private String roadAddressName;
    }
}
