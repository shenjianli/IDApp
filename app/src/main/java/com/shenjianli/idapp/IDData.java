package com.shenjianli.idapp;

/**
 * Created by edianzu on 2017/2/15.
 */
public class IDData {

    /**
     * resultcode : 200
     * reason : 成功的返回
     * result : {"area":"山西省临汾地区汾西县","sex":"男","birthday":"1988年04月05日","verify":""}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    /**
     * area : 山西省临汾地区汾西县
     * sex : 男
     * birthday : 1988年04月05日
     * verify :
     */

    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
