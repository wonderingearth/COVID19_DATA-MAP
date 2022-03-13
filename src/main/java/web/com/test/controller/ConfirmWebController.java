/*
*   用于在网页访问时返回相应的html，我自己是使用本地端口8888进行测试
*   有关webcontroller知识，还需要复习以下才可以完善注释
* */


package web.com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ConfirmWebController {
    @GetMapping("/ConfirmMap")
    public String map(String date){
            return "Covid-19_WriteOnly"+date+"Confirm";
        }
}
