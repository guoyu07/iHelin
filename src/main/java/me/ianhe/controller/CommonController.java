package me.ianhe.controller;

import me.ianhe.entity.Poem;
import me.ianhe.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iHelin
 * @since 2017/11/10 17:05
 */
@RestController
public class CommonController extends BaseController {

    @Autowired
    private TaskService taskService;

    /**
     * 古诗接口
     *
     * @author iHelin
     * @since 2017/11/10 17:26
     */
    @GetMapping("poem")
    public Poem getPoemByRandom() {
        return taskService.getPoemByRandom();
    }

}
