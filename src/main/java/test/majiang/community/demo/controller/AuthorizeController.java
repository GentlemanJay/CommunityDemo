package test.majiang.community.demo.controller;

import cn.hutool.core.lang.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.majiang.community.demo.Mapper.UserMapper;
import test.majiang.community.demo.dao.User;
import test.majiang.community.demo.dto.AccessTokenDTO;
import test.majiang.community.demo.dto.GithubUserDTO;
import test.majiang.community.demo.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private UserMapper userMapper;


    /**
     * 通过@Value注解自动获取配置文件中的属性值并赋值给相应的变量
     */
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("----accessToken: " + accessToken);
        GithubUserDTO githubProviderUser = githubProvider.getUser(accessToken);
//        System.out.println("====userLogin: " + user.getLogin());
        if (githubProviderUser != null && githubProviderUser.getId() != null) {
            //将用户信息保存至数据库
            User user = new User();
            user.setAccountId(githubProviderUser.getId());
            user.setName(githubProviderUser.getLogin());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            System.out.println("=====user:" + user);
            //将获取到的token值写入cookie中,以便之后登陆首页进行用户校验
            response.addCookie(new Cookie("token",token));
//            request.getSession().setAttribute("user",githubProviderUser);
            return "redirect:/";
        } else {
            //登陆失败，重定向至首页
            return "redirect:/";
        }
    }
}
