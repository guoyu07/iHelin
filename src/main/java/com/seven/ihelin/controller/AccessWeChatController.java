package com.seven.ihelin.controller;

import com.seven.ihelin.req.LocationMessage;
import com.seven.ihelin.util.CheckUtil;
import com.seven.ihelin.util.MessageUtil;
import com.seven.ihelin.util.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 微信消息接入
 * Created by iHelin on 16/11/4.
 */
@Controller
public class AccessWeChatController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理get消息：消息验证
     */
    @ResponseBody
    @RequestMapping(value = "access_wechat", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public String doGet(String signature, String timestamp, String nonce, String echostr) {
        logger.info("验证access");
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            logger.info("验证成功");
            return echostr;
        } else {
            logger.info("验证失败，echostr：" + echostr);
            return "error";
        }
    }

    /**
     * 处理post消息
     *
     * @throws IOException
     */
    @RequestMapping(value = "access_wechat", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> msgMap = MessageUtil.xml2Map(request);
        String msgType = msgMap.get("MsgType");
        String respMessage = null;
        if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {
            respMessage = processEvent(msgMap); // 进入事件处理
        } else {
            respMessage = processMessage(msgMap); // 进入普通消息处理
        }
        System.out.println(respMessage);
        response.getWriter().print(respMessage);
    }

    /**
     * 普通消息处理
     */
    public String processMessage(Map<String, String> msgMap) {
        String message = "";
        String fromUserName = msgMap.get("FromUserName");
        String toUserName = msgMap.get("ToUserName");
        String msgType = msgMap.get("MsgType");
        String content = msgMap.get("Content");
        System.out.println("消息类型：" + msgType);
        switch (msgType) {
            case MessageUtil.MESSAGE_TEXT:
                // 处理文本消息
                System.out.println("用户发送的消息是：" + content);
                message = textMessage(content, toUserName, fromUserName);
                break;
            case MessageUtil.MESSAGE_IMAGE:
                // 图片消息处理
                message = MessageUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的图片！");
                break;
            case MessageUtil.MESSAGE_LOCATION:
                // 处理地理位置消息
                LocationMessage locationMsg = WechatUtil.MapToLocation(msgMap);
                message = MessageUtil.sendTextMsg(toUserName, fromUserName, locationMsg.getLabel());
                break;
            case MessageUtil.MESSAGE_LINK:
                // 链接消息
                message = MessageUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的链接！");
                break;
            case MessageUtil.MESSAGE_VIDEO:
                // 视频消息
                message = MessageUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的视频！");
                break;
            case MessageUtil.MESSAGE_VOICE:
                // 音频消息
                message = MessageUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的音频！");
                break;
            default:
                message = MessageUtil.sendTextMsg(toUserName, fromUserName, "未知的消息。");
                break;
        }
        return message;
    }

    /**
     * 事件类消息处理
     *
     * @param msgMap
     * @return
     */
    public String processEvent(Map<String, String> msgMap) {
        System.out.println(msgMap);
        String eventType = msgMap.get("Event");
        String fromUserName = msgMap.get("FromUserName");
        String toUserName = msgMap.get("ToUserName");
        String message = "";
        if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
            message = MessageUtil.sendTextMsg(toUserName, fromUserName, "感谢您的关注，我会继续努力！");// 关注事件
        } else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
            // view类型事件，访问网页
            String url = msgMap.get("EventKey");
            message = MessageUtil.sendTextMsg(toUserName, fromUserName, url);
        } else if (MessageUtil.MESSAGE_SCANCODE.equals(eventType)) {
            // 扫码事件
            String key = msgMap.get("EventKey");
            message = MessageUtil.sendTextMsg(toUserName, fromUserName, key);
        }
        return message;
    }

    /**
     * 文本消息处理
     */
    public static String textMessage(String content, String toUserName, String fromUserName) {
        String message = "";
        switch (content) {
            case "1":
                message = MessageUtil.sendTextMsg(toUserName, fromUserName,
                        "你想干嘛？");
                break;
            default:
                message = MessageUtil.sendTextMsg(toUserName, fromUserName, "嗯嗯");
                break;
        }
        return message;
    }

}
