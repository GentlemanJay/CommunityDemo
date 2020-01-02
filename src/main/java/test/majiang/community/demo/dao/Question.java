package test.majiang.community.demo.dao;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String content;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;//default 0
    private Integer likeCount;//default 0
    private Integer creator;
    private Integer commentCount;//default 0
}
