package com.argusoft.lms.websocket;

import android.util.Log;

public class Websocket {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
