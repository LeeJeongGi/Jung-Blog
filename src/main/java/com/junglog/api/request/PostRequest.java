package com.junglog.api.request;

import com.junglog.api.exception.InvalidRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class PostRequest {

    @NotBlank(message = "타이틀을 입력해주세요.")
    public String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    public String content;

    /**
     * 빌더의 장점
     * > 가독성이 좋다.
     * > 값 생성에 대한 유연함 -> 오버로딩 가능한 조건 찾아보기.(메소드 이름이 같아야 하고 파라미터의 타입이나 갯수가 달라야한다.)
     * > 객체의 불변성
     */
    @Builder
    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if (this.title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 바보를 입력 할 수 없습니다.");
        }
    }
}
