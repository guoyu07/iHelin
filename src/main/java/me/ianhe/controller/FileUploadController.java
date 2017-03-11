package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.utils.FileUtil;
import me.ianhe.utils.JSON;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class FileUploadController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 文件上传页面
     *
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadPage() {
        return "upload";
    }

    /**
     * 上传到七牛服务器
     *
     * @param file  待上传的文件
     * @param model
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("msg", "请选择文件……");
            return "upload";
        }
        String res = saveFile(file, "test" + UUID.randomUUID().toString());
        model.addAttribute("msg", "上传成功！");
        model.addAttribute("img", res);
        return "upload";
    }

    /**
     * simditor
     * 富文本编辑器图片文件上传接口
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "img_upload", method = RequestMethod.POST)
    @ResponseBody
    public String imgUpload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = Maps.newHashMap();
        if (file.isEmpty()) {
            res.put("success", false);
            res.put("msg", "文件不存在！");
            return JSON.toJson(res);
        }
        //扩展名
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        //文件相对路径（粘贴板文件无扩展名）
        String fileName = null;
        if (StringUtils.isBlank(fileExt)) {
            fileName = "article/" + UUID.randomUUID().toString() + ".png";
        } else {
            fileName = "article/" + UUID.randomUUID().toString() + "." + fileExt;
        }
        //保存到七牛对象存储
        String realFullPath = saveFile(file, fileName);
        res.put("success", true);
        res.put("file_path", realFullPath);
        return JSON.toJson(res);
    }

    public String saveFile(MultipartFile file, String newFileName) {
        try {
            byte[] bytes = file.getBytes();
            String result = FileUtil.uploadFile(bytes, newFileName);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
