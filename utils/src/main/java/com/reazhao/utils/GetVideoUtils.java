package com.reazhao.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class GetVideoUtils {
	/**
	 * 获得sdcard中的全部视频
	 * @param context
	 * @return
	 */
	public static List<VideoBena> getAllVideo(final Context context) {
		List<VideoBena> data = new ArrayList<VideoBena>();
		ContentResolver resolver = context.getContentResolver();
		Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		// 查找的条件
		String[] projection = { MediaStore.Video.Media.TITLE, // 标题
				MediaStore.Video.Media.DESCRIPTION, // 描述
				MediaStore.Video.Media.SIZE, // 大小
				MediaStore.Video.Media.DURATION, // 时长
				MediaStore.Video.Media.DATA }; // 绝对路径
		Cursor cursor = resolver.query(uri, projection, null, null, null);
		while (cursor.moveToNext()) {
			// 判断视频大小>3KB的才添加进来。
			if (cursor.getLong(2) > 3 * 1024) {
				VideoBena beans = new VideoBena();
				beans.setTitle(cursor.getString(0));
				beans.setDescribe(cursor.getString(1));
				beans.setSize(cursor.getLong(2));
				beans.setLongtime(cursor.getString(3));
				beans.setPath(cursor.getString(4));
				data.add(beans);
			}
		}
		return data;
	}
	static class VideoBena{
		private String title;
		private String describe;
		private long size;
		private String longtime;
		private String path;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescribe() {
			return describe;
		}

		public void setDescribe(String describe) {
			this.describe = describe;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public String getLongtime() {
			return longtime;
		}

		public void setLongtime(String longtime) {
			this.longtime = longtime;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
	}
}
