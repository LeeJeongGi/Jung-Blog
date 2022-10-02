package com.junglog.api.controller;

import com.junglog.api.request.PostRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Slf4j
public class PostController {

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostRequest params) throws Exception {

        // 데이터를 검증하는 이유
        // 1. client 개발자가 깜빡 할 수 있다. 실수로 값을 안보낼 수 있다.
        //      > 개발자가 까먹을 수 있다.
        //      > 검증 부분에서 버그가 발생 할 수 있다.
        //      > 지겹다...반복 작업일 뿐...
        // 2. client bug 값이 누락
        // 3. 외부에 나쁜 사람이 값을 임의로 조작해서 보낼 수 있다.
        // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
        // 5. 서버개발자의 편안함읠 위해타

//        String title = params.getTitle();
//        if (title == null || "".equals(title)) {
//            // error
//            // 1. 이런 형태의 검증은 노가다일 뿐.
//            // 2. 개발팁 -> 무언가 3번이상 반복작업을 할때 내가 뭔가 잘못하고 있는건 아닐지 의심해본다.
//            // 3. 누락될 위험이 있다.
//            // 4. 생각보다 검증해야 될 것이 많다. (꼼꼼하지 않을 수가 있다)
//            // 5. 가장 중요한건 개발자 스럽지 않다.
//            throw new Exception("타이틀 값이 없습니다!");
//        }
//
//        String content = params.getContent();
//        if (content == null || "".equals(content)) {
//            //error
//        }

//        if (result.hasErrors()) {
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            FieldError firstFieldError = fieldErrors.get(0);
//
//            String fieldName = firstFieldError.getField();
//            String errorMessage = firstFieldError.getDefaultMessage();
//
//            Map<String, String> error = new HashMap<>();
//            error.put(fieldName, errorMessage);
//
//            return error;
//        }
        log.info("params={}", params.toString());

        return Map.of();
    }
}
