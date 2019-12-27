package test.majiang.community.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.majiang.community.demo.dto.AccessTokenDTO;
import test.majiang.community.demo.dto.GithubUserDTO;
import test.majiang.community.demo.provider.GithubProvider;

@Controller
public class AuthorizeController {

    /**
     * github授权登陆步骤https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
     * 通过get请求获取请求体中的code
     * 通过post请求携带code获取accessToken
     * 通过get请求携带accessToken获取用户信息
     */

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("a30fee9101e3e694b1f2");
        accessTokenDTO.setClient_secret("4fc50afb8b7290969c23d9939f563596bb724e7d");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("----accessToken: " + accessToken);
        GithubUserDTO user = githubProvider.getUser(accessToken);
        System.out.println("====userLogin: " + user.getLogin());
        return "index";
    }
}
