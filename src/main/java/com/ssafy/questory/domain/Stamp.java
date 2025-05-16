package com.ssafy.questory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Stamp {
    private int stampId;
    private String imageUrl;
    private String description;
}
