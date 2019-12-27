package test.majiang.community.demo.provider;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import test.majiang.community.demo.dto.AccessTokenDTO;
import test.majiang.community.demo.dto.GithubUserDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Http客户端工具包httpUtils
 * HuTool
 * okHttp
 * Jodd-http
 */

@Component
public class GithubProvider {

    //POST https://github.com/login/oauth/access_token

    /**
     * 根据github API文档,get请求获取code后再获取accessToken
     * AccessTokenDTO封装了五个参数
     * @param accessTokenDTO
     * @return
     */

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        //Hutool---POST请求
        String jsonString = JSON.toJSONString(accessTokenDTO);
        String url = "https://github.com/login/oauth/access_token";
        String s = HttpUtil.post(url, jsonString);//access_token=e7b040ea8bc4a10a145c98d5bff090d3c0a6466e&scope=user&token_type=bearer
        return s.split("&")[0].split("=")[1];
    }

    //OkHttp方式post请求
    /*public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return string.split("&")[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/


    /**
     * get https://api.github.com/user
     * 根据上一步返回的accessToken值返回gitUser信息
     * @param accessToken
     * @return
     */


    public GithubUserDTO getUser(String accessToken) {
        //Hutool---GET请求
        String url = "https://api.github.com/user?access_token=" + accessToken;
        String content = HttpUtil.get(url);
        System.out.println(content);
        return JSON.parseObject(content, GithubUserDTO.class);
    }



    //OkHttp方式get请求
    /*public GithubUserDTO getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            return JSON.parseObject(s, GithubUserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }*/

}

