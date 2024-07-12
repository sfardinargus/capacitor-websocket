package com.argusoft.lms.websocket;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import java.util.HashMap;
import java.util.Map;
import okhttp3.*;
import okhttp3.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

public class Websocket {

    private WebSocket webSocket;
    private OkHttpClient client;
    private Map<String, PluginCall> eventListeners;

    public Websocket() {
        client = new OkHttpClient();
        eventListeners = new HashMap<>();
    }

    public void connect(String url, JSObject socketConfig, PluginCall call) {
        String transports = socketConfig.getString("transports", "websocket");
        String path = socketConfig.getString("path", "/socket.io");

        HttpUrl httpUrl = HttpUrl.parse(url).newBuilder()
            .addQueryParameter("transport", transports)
            .addPathSegments(path.replaceFirst("^/", ""))
            .build();

        Request request = new Request.Builder().url(httpUrl).build();
        webSocket =
            client.newWebSocket(
                request,
                new WebSocketListener() {
                    @Override
                    public void onOpen(WebSocket webSocket, Response response) {
                        JSObject result = new JSObject();
                        result.put("event", "open");
                        call.resolve(result);
                    }

                    @Override
                    public void onMessage(WebSocket webSocket, String text) {
                        try {
                            JSONObject jsonMessage = new JSONObject(text);
                            String event = jsonMessage.getString("event");
                            JSObject data = JSObject.fromJSONObject(jsonMessage.getJSONObject("data"));

                            PluginCall eventCall = eventListeners.get(event);
                            if (eventCall != null) {
                                JSObject result = new JSObject();
                                result.put("event", event);
                                result.put("data", data);
                                eventCall.resolve(result);
                            }
                        } catch (JSONException e) {
                            call.reject("Error parsing message: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onClosing(WebSocket webSocket, int code, String reason) {
                        JSObject result = new JSObject();
                        result.put("event", "closing");
                        result.put("code", code);
                        result.put("reason", reason);
                        call.resolve(result);
                    }

                    @Override
                    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                        call.reject("WebSocket failure: " + t.getMessage());
                    }
                }
            );
    }

    public void disconnect(PluginCall call) {
        if (webSocket != null) {
            webSocket.close(1000, "Disconnected");
            webSocket = null;
            eventListeners.clear();
        }
        call.resolve();
    }

    public void on(String event, PluginCall call) {
        eventListeners.put(event, call);
        call.setKeepAlive(true);
    }

    public void emit(String event, JSObject data, PluginCall call) {
        if (webSocket != null) {
            try {
                JSONObject jsonMessage = new JSONObject();
                jsonMessage.put("event", event);
                jsonMessage.put("data", new JSONObject(data.toString()));
                webSocket.send(jsonMessage.toString());
                call.resolve();
            } catch (JSONException e) {
                call.reject("Error creating message: " + e.getMessage());
            }
        } else {
            call.reject("WebSocket is not connected");
        }
    }
}
