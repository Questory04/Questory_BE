package com.ssafy.questory.dto.response.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
public class StampsResponseDto {
    private List<StampInfo> stamps;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StampInfo {
        private int id;
        private String title;
        private String url;
        private LocalDate date;
        private String contentTypeName;
        private String sidoName;
        private String difficulty;
        private String description;

        @Override
        public String toString() {
            return "StampInfo{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", date=" + date +
                    ", contentTypeName='" + contentTypeName + '\'' +
                    ", sidoName='" + sidoName + '\'' +
                    ", difficulty='" + difficulty + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StampsResponseDto{" +
                "stamps=" + stamps +
                '}';
    }
}
