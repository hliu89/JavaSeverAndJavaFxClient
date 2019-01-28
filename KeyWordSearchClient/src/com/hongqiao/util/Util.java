package com.hongqiao.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hongqiao.dto.Text;

public class Util {

	public static String TextListToJason(List<Text> items) throws JSONException {
        if (items == null) return "";
        JSONArray array = new JSONArray();
        JSONObject jsonObject = null;
        Text text = null;
        for (int i = 0; i < items.size(); i++) {
            text = items.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("id",text.getId());
            jsonObject.put("title", text.getTitle());
            jsonObject.put("summary", text.getSummary());
            array.put(jsonObject);
        }
        return array.toString();
    }
	public static List<Text> JsonToTextList(String data) throws JSONException {
        List<Text> texts = new ArrayList<>();
        if (data.equals("")) return texts;

        JSONArray array = new JSONArray(data);
        JSONObject object = null;
        Text text = null;
        for (int i = 0; i < array.length(); i++) {
            object = array.getJSONObject(i);
            String id = object.getString("id");
            String title = object.getString("title");
            String summary = object.getString("summary");
            text = new Text(id, title, summary);
            texts.add(text);
        }
        return texts;
    }
	public static JSONArray StringToJson(String data) throws JSONException{
		 return new JSONArray(data);
	}
	public enum method{
    	SearchByKeyWord,GetDetailData,GetAllText,UpdateText,DeleteText
    }

}
