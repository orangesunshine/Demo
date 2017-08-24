package com.orange.demo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

public class GsonHelper {
    private static Gson gson;

    private static Gson getGson() {
        if (gson == null) {
            synchronized (GsonHelper.class) {
                if (gson == null) {
                    gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                }
            }
        }
        return gson;
    }

    public static String Serialize(Object obj) {

        return getGson().toJson(obj);
    }

    public static <T> T Deserialize(String json, Class<T> classOfType) {
        try {
            return getGson().fromJson(json, classOfType);
        }catch (Exception e){
            if(e!=null){
                LogUtil.log(e.getLocalizedMessage());
            }
            return null;
        }
    }

    public static <T> T Deserialize(String json, Type type) {
        try {
            return getGson().fromJson(json, type);
        } catch (Exception e) {
            if(e!=null){
                LogUtil.log(e.getLocalizedMessage());
            }
            return null;
        }
    }

    static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            // TODO Auto-generated method stub
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }
        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }
}
