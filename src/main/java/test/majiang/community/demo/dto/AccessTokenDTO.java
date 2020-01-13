package test.majiang.community.demo.dto;


import lombok.Data;

//dto数据传输对象
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
