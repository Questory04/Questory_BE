package com.ssafy.questory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Stamp {
    private int stampId;
    private String imageUrl;
    private String description;
}
