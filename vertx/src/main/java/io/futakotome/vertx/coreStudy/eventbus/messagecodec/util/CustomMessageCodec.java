package io.futakotome.vertx.coreStudy.eventbus.messagecodec.util;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class CustomMessageCodec implements MessageCodec<CustomMessage, CustomMessage> {

    @Override
    public void encodeToWire(Buffer buffer, CustomMessage customMessage) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("statusCode", customMessage.getStatusCode());
        jsonObject.put("resultCode", customMessage.getResultCode());
        jsonObject.put("summary", customMessage.getSummary());

        String json2Str = jsonObject.encode();
        int length = json2Str.getBytes().length;
        buffer.appendInt(length);
        buffer.appendString(json2Str);
    }

    @Override
    public CustomMessage decodeFromWire(int pos, Buffer buffer) {
        int _pos = pos;
        int length = buffer.getInt(_pos);
        String jsonStr = buffer.getString(_pos += 4, _pos += length);
        JsonObject contentJson = new JsonObject(jsonStr);
        int statusCode = contentJson.getInteger("statusCode");
        String resultCode = contentJson.getString("resultCode");
        String summary = contentJson.getString("summary");
        return new CustomMessage(statusCode, resultCode, summary);
    }

    @Override
    public CustomMessage transform(CustomMessage customMessage) {
        return customMessage;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
