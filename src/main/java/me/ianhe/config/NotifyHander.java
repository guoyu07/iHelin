package me.ianhe.config;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.entity.msg.SendMsg;

/**
 * @author iHelin
 * @since 2017/9/20 22:05
 */
public class NotifyHander {

    private static SmartQQClient smartQQClient;

    public NotifyHander(SmartQQClient smartQQClient) {
        NotifyHander.smartQQClient = smartQQClient;
    }

    // 指定不同类型的不同msg回复
    public void hander(PollMsg pollMsg) {
        switch (pollMsg.getMsgType()) {
            case message:
                smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: friend"));
                break;
            case group_message:
                if (!pollMsg.getValue().getToUin().equals(979356834L)) {
                    //smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: group"));
                }
                break;
            case discu_message:
                if (!pollMsg.getValue().getToUin().equals(979356834L)) {
                    //smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: discu"));
                }
                break;
        }
    }


    // sendMsg 接口能通过pollMsg得到msg的类型，然后自动回复该类型的msg
    /*public void hander(PollMsg pollMsg){
        smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg"));
    }*/

}
