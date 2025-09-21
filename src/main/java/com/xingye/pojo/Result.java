package com.xingye.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


public class Result {
    private Integer code; // 响应码，1 代表成功; 0 代表失败
    private String msg;   // 响应信息 描述字符串
    private Object data;  // 返回的数据


    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(code, result.code) && Objects.equals(msg, result.msg) && Objects.equals(data, result.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, data);
    }

    // 增删改 成功响应
    public static Result success() {
        return new Result(1, "Success", null);
    }

    // 查询 成功响应
    public static Result success(Object data) {
        return new Result(1, "Success", data);
    }

    // 失败响应
    public static Result error(String msg) {
        return new Result(0, msg, null);
    }
}
