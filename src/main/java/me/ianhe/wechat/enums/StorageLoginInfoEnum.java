package me.ianhe.wechat.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * StorageLoginInfoEnum
 *
 * @author iHelin
 * @since 2017/12/11 12:14
 */
public enum StorageLoginInfoEnum {

    /**
     * URL
     */
    url("url", new String()),
    fileUrl("fileUrl", new String()),
    syncUrl("syncUrl", new String()),

    /**
     * 生成15位随机数
     */
    deviceid("deviceid", new String()),

    /**
     * baseRequest
     */
    skey("skey", new String()),
    wxsid("wxsid", new String()),
    wxuin("wxuin", new String()),
    pass_ticket("pass_ticket", new String()),


    InviteStartCount("InviteStartCount", new Integer(0)),
    USER("User", new JSONObject()),
    SyncKey("SyncKey", new JSONObject()),
    synckey("synckey", new String()),


    MemberCount("MemberCount", new String()),
    MemberList("MemberList", new JSONArray()),;

    private String key;
    private Object type;

    StorageLoginInfoEnum(String key, Object type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return key;
    }


    public Object getType() {
        return type;
    }

}
