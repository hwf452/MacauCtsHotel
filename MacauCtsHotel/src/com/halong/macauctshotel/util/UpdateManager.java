package com.halong.macauctshotel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.halong.macauctshotel.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class UpdateManager {
	private Context context;
	// 发起版本检测的url
	private final static String CHECKURL = "http://112.124.25.208/zjiu/api/updateVer.api.php";
	// 服务器返回的apk安装包下载路径
	private static String downloadUrl = "";
	private Dialog noticeDialog;
	private ProgressBar mProgress;
	private boolean interceptFlag = false;
	private Dialog downloadDialog;
	private static final int DOWN_UPDATE = 1;
	private static final int DOWN_OVER = 2;
	private int progress;
	// 下载包安装本地存放路径
	private static final String savePath = Environment.getExternalStorageDirectory() + "/guyun/update/";
	private static final String saveFileName = savePath + "sport.apk";

	public UpdateManager(Context context) {
		this.context = context;
	}

	private String content;

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 更新UI的Handler
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				if (downloadDialog != null)
					downloadDialog.dismiss();
				installApk();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 版本更新接口
	 */
	public void checkUpdateInfo() {
		// 向服务器发出检查请求，如果需要更新则弹出提示对话框
		if (isNeedUpdate()) {
			// 显示提示对话框
			showNoticeDialog();
		}
	}

	/**
	 * 检查当前版本是否需要更新
	 * 
	 * @return
	 */
	private boolean isNeedUpdate() {
		String versionName = getVersionName(context);
		boolean isNeedUpdate = testVersion(versionName);
		return isNeedUpdate;
	}

	/**
	 * 获取本地版本名称
	 * 
	 * @param context
	 * @return
	 */
	private String getVersionName(Context context) {
		String versionName = "";
		try {
			// 包名改为自己应用的包名即可
			versionName = context.getPackageManager().getPackageInfo("com.home.update", 1).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 向服务器发起版本检测请求，服务器根据提交的版本参数和服务器上的当前版本进行比较 如果符合更新要求，返回最新版本号和下载URL
	 * 
	 * @param versionName
	 * @return
	 */
	private boolean testVersion(String version) {
		boolean isNeedUpdate = false;
		// 这里的url具体拼装方式应根据服务器需要的格式而定
		String url = CHECKURL;
		String result = content;
		try {
			// 这里的具体解析方式应根据自己服务器返回的数据而定
			JSONObject jsonObj = new JSONObject(result);
			String code = jsonObj.getString("code");
			if ("-uv10001".equals(code)) {
				JSONObject valueObj = jsonObj.getJSONObject("result");
				String lastVersion = valueObj.getString("ver");
				//
				downloadUrl = valueObj.getString("url");
				if (version.equals(lastVersion)) {
					isNeedUpdate = false;
				} else {
					isNeedUpdate = true;
				}
			} else {
				isNeedUpdate = false;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isNeedUpdate;
	}

	/**
	 * 以get方式发送请求
	 * 
	 * @param url
	 * @return
	 */
	public String get(String url) {
		BufferedReader reader = null;
		StringBuffer sb = null;
		String result = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		request.addHeader("Content-Type", " text/json");
		try {
			// 发送请求，得到响应
			HttpResponse response = client.execute(request);
			// 如果请求成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				sb = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE) {
				result = "维护或繁忙";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				result = "维护或繁忙";
				e.printStackTrace();
			}
		}
		if (sb != null) {
			result = sb.toString();
		}
		return result;
	}

	/**
	 * 弹出更新提示对话框
	 */
	private void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("软件版本更新");
		builder.setMessage("版本可以更新哦");
		builder.setPositiveButton("下载", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
		// 设置弹出对话框大小属性
		WindowManager.LayoutParams lp = noticeDialog.getWindow().getAttributes();
		lp.width = 400;
		lp.height = 500;
		noticeDialog.getWindow().setAttributes(lp);
	}

	/**
	 * 下载进度对话框
	 */
	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("正在下载，请稍后...");
		final LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);
		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();
		downloadApk();
	}

	/**
	 * 启动线程下载apk
	 */
	private void downloadApk() {
		Thread downLoadThread = new Thread(downApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装包下载线程
	 */
	private Runnable downApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(downloadUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];
				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					handler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成通知安装
						handler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载.
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 安装APK
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		context.startActivity(i);
	}
}
