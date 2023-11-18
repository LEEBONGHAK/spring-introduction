package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// Controller: 비즈니스 로직 또는 내부적인 것을 처리하는데 집중해야 함
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        // Model 은 MVC 중 M에 해당
        model.addAttribute("data", "hello!!!");
        return "hello";
    }

    // @RequestParam: URL Parameter를 받는 Annotation
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody   // http에서 body부분에 return 데이터를 response로 직접 넣어주겠다.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);

        // 객체를 return 함으로써 JSON 형태로 반환됨
        return hello;
    }

    static class Hello {    // static class를 사용하면 class안에 class 사용 가능
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
