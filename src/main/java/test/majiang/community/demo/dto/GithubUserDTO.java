package test.majiang.community.demo.dto;

import lombok.Data;

//封装git用户信息
@Data
public class GithubUserDTO {
    private String login;
    private String type;
    private String id;
    private String avatar_url;

}
