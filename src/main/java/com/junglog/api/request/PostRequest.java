package com.junglog.api.request;

import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class PostRequest {

    public String title;
    public String content;
}
