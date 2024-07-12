package com.argusoft.lms.websocket;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Websocket")
public class WebsocketPlugin extends Plugin {

    private Websocket implementation = new Websocket();

    @PluginMethod
    public void connect(PluginCall call) {
        String url = call.getString("url");
        JSObject socketConfig = call.getObject("socketConfig");
        implementation.connect(url, socketConfig, call);
    }

    @PluginMethod
    public void disconnect(PluginCall call) {
        implementation.disconnect(call);
    }

    @PluginMethod
    public void on(PluginCall call) {
        String event = call.getString("event");
        implementation.on(event, call);
    }

    @PluginMethod
    public void emit(PluginCall call) {
        String event = call.getString("event");
        JSObject data = call.getObject("data");
        implementation.emit(event, data, call);
    }
}
