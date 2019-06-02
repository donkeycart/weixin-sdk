package com.riversoft.weixin.pay.util;

import java.util.Map;
import java.util.StringJoiner;

import com.riversoft.weixin.common.util.MD5;

/**
 * Created by exizhai on 12/1/2015.
 */
public class SignatureUtil {

    public static String sign(Map<String, Object> map, String key) {

        StringJoiner sj = new StringJoiner("&");
        map.forEach((k, v) -> {
            sj.add(k + "=" + v);
        });
        return MD5.md5Hex(sj + "&key=" + key).toUpperCase();
    }
}
