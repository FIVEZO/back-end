package com.sparta.toogo.domain.home.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HomeCountryCountDto {

    private Long asiaPostCount;
    private Long africaPostCount;
    private Long europePostCount;
    private Long oceaniaPostCount;
    private Long americaPostCount;


    public HomeCountryCountDto(Long asiaPostCount, Long africaPostCount, Long europePostCount, Long oceaniaPostCount, Long americaPostCount) {
        this.asiaPostCount = asiaPostCount;
        this.africaPostCount = africaPostCount;
        this.europePostCount = europePostCount;
        this.oceaniaPostCount = oceaniaPostCount;
        this.americaPostCount = americaPostCount;
    }
}
