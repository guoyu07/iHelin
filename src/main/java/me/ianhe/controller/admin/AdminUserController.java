package me.ianhe.controller.admin;

import me.ianhe.db.entity.User;
import me.ianhe.db.plugin.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminUserController extends BaseAdminController {

    @RequestMapping(value = "user_admin", method = RequestMethod.GET)
    public String userAdmin(Model model, String nickName, Integer pageNum) {
        if (pageNum == null)
            pageNum = 1;
        List<User> users = userManager.listUserByCondition(nickName, null,
                (pageNum - 1) * DEFAULT_PAGE_LENGTH, DEFAULT_PAGE_LENGTH);
        int totalCount = userManager.listUserCount(nickName, null);
        model.addAttribute("nickName", nickName);
        model.addAttribute("users", users);
        model.addAttribute("pagination", new Pagination(totalCount, pageNum, DEFAULT_PAGE_LENGTH));
        return ftl("user_admin");
    }

    /**
     * 删除用户
     *
     * @author iHelin
     * @since 2017/8/28 13:31
     */
    @DeleteMapping(value = "user")
    public String deleteProduct(Integer id) {
        userManager.deleteUserById(id);
        return success();
    }

}
