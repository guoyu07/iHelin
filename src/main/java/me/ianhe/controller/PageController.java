package me.ianhe.controller;

import me.ianhe.db.entity.Article;
import me.ianhe.utils.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PageController extends BaseController {

    private static final String READ_COUNT_KEY = "article:readCount:";

    @RequestMapping(value = "post/{id}", method = RequestMethod.GET)
    public String postPage(@PathVariable Integer id, Model model) {
        Article article = null;
        if (id == null || id == 0) {
            List<Article> articles = articleManager.listByCondition(null, 0, 1);
            article = articles.get(0);
        } else {
            article = articleManager.selectArticleById(id);
        }
        if (article == null) {
            return "article";
        }
        Integer count = commonRedisDao.getInt(READ_COUNT_KEY + id);
        if (count == null) {
            count = 0;
        }
        count++;
        commonRedisDao.set(READ_COUNT_KEY + id, String.valueOf(count));
        model.addAttribute("readCount", count);
        model.addAttribute("article", article);
        return "article";
    }

    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String aboutPage() {
        return "about";
    }

    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public String contactPage() {
        return "contact";
    }

    @RequestMapping(value = "music", method = RequestMethod.GET)
    public String musicPage(HttpServletRequest request) {
        logger.info("Browser's user-agent: " + request.getHeader("User-Agent"));
        logger.info("remote ip:" + RequestUtil.getRealIp(request));
        logger.info("music...");
        return "music";
    }

}
