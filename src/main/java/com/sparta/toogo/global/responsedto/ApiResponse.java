package com.sparta.toogo.global.responsedto;

import com.sparta.toogo.domain.post.dto.PostResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse {

    private int status;
    private String message;
    private PostResponseDto data;

    public ApiResponse(int status, String message, PostResponseDto data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
