package test.majiang.community.demo.dao;

import lombok.Data;

@Data
public class User {
    private int id;
    private String accountId;
    private String name;
    private String token;
    private String avatarUrl;
    private String type;
    private Long gmtCreate;
    private Long gmtModified;
}
