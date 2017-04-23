package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.model.Result;
import me.ianhe.utils.JSON;
import me.ianhe.utils.MailUtil;
import me.ianhe.utils.ResponseUtil;
import me.ianhe.utils.TemplateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Controller
public class TestController extends BaseController {

    @ResponseBody
    @GetMapping(value = "tpl")
    public String mail() throws MessagingException {
        String template = TemplateUtil.applyTemplate("mail_content.ftl");
//        mimeMail.sendMail("ihelin@outlook.com", "哈哈", template);
        MailUtil.sendMail("ihelin@outlook.com", "iHelin", "哈哈", template);
        return template;
    }

    @ResponseBody
    @PostMapping("test_post")
    public String testPost(@RequestBody String hello) {
        System.out.println("get:" + hello);
        return "hahahhahahhah";
    }

    @ResponseBody
    @RequestMapping(value = "console", method = RequestMethod.GET)
    public String getProperties() {
        Properties props = System.getProperties();
        return JSON.toJson(props);
    }

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        Result result = new Result();
        result.setData("<h1>三个人请问abc123</h1>");
        return JSON.toJson(result);
    }

    @RequestMapping(value = "test1", method = RequestMethod.GET)
    public void test1(HttpServletResponse response, HttpSession session) throws IOException {
        Map<String, Object> data = Maps.newHashMap();
        data.put("data", "<h1>三个人请问abc123</h1>");
        ResponseUtil.writeSuccessJSON(response, data);
    }

}
