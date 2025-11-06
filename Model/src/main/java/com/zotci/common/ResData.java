package com.zotci.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResData {

    private Integer code;
    private String msg;
    private Object data;

    public static ResData build(Integer code, String msg, Object data) {
        return new ResData(code, msg, data);
    }

    public static ResData ok() {
        return new ResData(200, "OK", null);
    }

    public static ResData ok(String msg) {
        return new ResData(200, msg, null);
    }

    public static ResData ok(String msg, Object data) {
        return new ResData(200, msg, data);
    }

    public static ResData error() {
        return new ResData(500, "error", null);
    }

    public static ResData error(String msg) {
        return new ResData(500, msg, null);
    }

    public static ResData error(String msg, Object data) {
        return new ResData(500, msg, data);
    }

}
