package com.junglog.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
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
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;

    //Map 으로 되어 있어서 ㅈ금 찝찝하다.... 클래스로 수정해보자
    private final Map<String , String > validations = new HashMap<>();

    public void addValidation(String fieldName, String errorMessage) {
        this.validations.put(fieldName, errorMessage);
    }

}
