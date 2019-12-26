package test.majiang.community.demo.Controller;


import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Greeting {

    @GetMapping("/greet")
    public String sayHello(@RequestParam(name="name",required = false,defaultValue = "jack") String name, Model model) {

        model.addAttribute("name",name);
        return "greeting";
    }

}
