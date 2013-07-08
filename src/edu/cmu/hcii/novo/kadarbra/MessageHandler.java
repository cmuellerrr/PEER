package edu.cmu.hcii.novo.kadarbra;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MessageHandler {
	private static String TAG = "MessageHandler";
	
	/**
	 * Sends a broadcast message to be read by other classes
	 * 
	 * @param ctx Context
	 * @param msg
	 */
	private static void sendBroadcastMsg(Context ctx, String msg){
        Intent intent = new Intent("command");
        intent.putExtra("msg", msg);
        ctx.sendBroadcast(intent);
	}
	
	
	/**
	 * Parses an input JSON string
	 * 
	 * @param ctx Context
	 * @param msg 
	 */
	public static void parseMsg(Context ctx, String msg){
		JSONObject json;
		
		try {
			json = new JSONObject(msg);
			String type = json.getString("type");
			String content = json.getString("content");	
			
			//Log.v(TAG, "type: "+type+", "+"content: "+content);
			
			if (type.equals("command")){
				String command_msg = handleCommand(content);
				Log.v(TAG, "type: "+type+", "+"command_msg: "+command_msg);

				sendBroadcastMsg(ctx, command_msg);
			}
				
		
		} catch (JSONException e) {
			
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * Reads an input command and returns a message for activities to see
	 * 
	 * @param content
	 * @return command
	 */
	private static String handleCommand(String content){
		if (content.equals("back")){
			return "back";
		}else if (content.equals("next")){
			return "next";
		}else if (content.equals("up")){
			return "up";
		}else if (content.equals("down")){
			return "down";
		}else if (content.equals("menu")){
			return "menu";
		}else if (content.equals("navigate")){
			return "navigate";
		}else if (content.equals("stowage")){
			return "stowage";
		}
		
		return "";
	}
	
	
}
