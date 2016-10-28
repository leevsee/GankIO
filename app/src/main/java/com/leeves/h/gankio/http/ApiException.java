package com.leeves.h.gankio.http;

/**
 * Function：
 * Created by h on 2016/10/17.
 *
 * @author
 */

public class ApiException extends RuntimeException {
    public static final int ERROR = 100;

    public ApiException(int resultcode){
        this(getErrorMessage(resultcode));
    }

    public ApiException(String detailMessage){
        super(detailMessage);
    }

    private static String getErrorMessage(int code){
        String message = "";
                switch (code){
                    case ERROR:
                        message = "接口返回失败";
                        break;
                }
        return message;
    }
}
