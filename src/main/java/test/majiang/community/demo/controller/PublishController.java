package test.majiang.community.demo.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.majiang.community.demo.Mapper.QueationMapper;
import test.majiang.community.demo.Mapper.UserMapper;
import test.majiang.community.demo.dao.Question;
import test.majiang.community.demo.dao.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QueationMapper queationMapper;


    //get请求路由跳转
    @GetMapping("/publish")
    public String publishText() {
        return "publish";
    }



    //发布问题post请求路由跳转
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model) {

        //首先将前端传来的数据先包装起来，若出现异常页面仍然保留之前填写的数据
        model.addAttribute("title",title);
        model.addAttribute("content",content);
        model.addAttribute("tag",tag);

        //判断的填写的值不能为空
        if (StringUtils.isAnyBlank(title,content,tag)) {
            model.addAttribute("error","empty title,content and tag is not allowed");
            return "publish";
        }


        //在发布问题前先要获取当前登陆用户，若用户没有登陆则页面停留在当前界面，否则跳转至首页
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            model.addAttribute("error","Please Sign in first");
            return "publish";
        }
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if (cookieName.equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        if (user == null) {
            model.addAttribute("error","Please Sign in first");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        queationMapper.publishQuestion(question);
        return "redirect:/";



    }

}
