package com.junglog.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * {
 *     "code" : "400",
 *     "message" : "잘못된 요청입니다.",
 *     "validation" : {
 *          "title" : "값을 입력해주세요"
 *      }
 * }
 */
@Getter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY) // 비어 있는 값은 내려가지 않도록 설정하는 코드
public class ErrorResponse {

    private final String code;
    private final String message;

    private final Map<String , String > validations;

    @Builder
    public ErrorResponse(String code, String message, Map<String, String > validations) {
        this.code = code;
        this.message = message;
        this.validations = validations;
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validations.put(fieldName, errorMessage);
    }

}
