package com.gswtek.huyd.mysigndate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gswtek.huyd.mysign.mydatepicker.DPCManager;
import com.gswtek.huyd.mysign.mydatepicker.DPDecor;
import com.gswtek.huyd.mysign.mydatepicker.DPMode;
import com.gswtek.huyd.mysign.mydatepicker.DatePicker;
import com.gswtek.huyd.mysign.mydatepicker.DatePicker2;
import com.gswtek.huyd.util.DBOpenHelper;
import com.gswtek.huyd.util.SigndateDAO;
import com.gswtek.huyd.util.SigndateService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: huyd
 * Date: 2017-07-24
 * Time: 15:33
 * Describe:
 */
public class YuanbanDemoActivity extends AppCompatActivity {

	String result = "";
	List<String> tmp;
	int flag = 0;
	List<String> listTime = new ArrayList<String>();
	@Bind(R.id.etSearchDate)
	EditText etSearchDate;
	@Bind(R.id.btnSearchDate)
	Button btnSearchDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yuanban);
		ButterKnife.bind(this);

//        // 默认多选模式
//        DatePicker2 picker = (DatePicker2) findViewById(R.id.main_dp);
//        picker.setDate(2015, 7);
//        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
//            }
//        });

		DBOpenHelper helper = new DBOpenHelper(YuanbanDemoActivity.this);
		//调用 getWritableDatabase()或者 getReadableDatabase()其中一个方法将数据库建立
		helper.getWritableDatabase();
		SigndateService service = new SigndateDAO(YuanbanDemoActivity.this);
		//Object[] params = {"张三","北京","男"};
//		Object[] params = {result, "加班", "1"}; //新增加一条记录
//		boolean flag = service.addPersion(params);

		//查询多条记录，这里我们不需要传递参数，所以可以参数可以置为null
		List<Map<String, String>> list = service.listPersonMaps(null);
//		Log.i(TAG, "---查询所有记录--->> " + list.toString());


		// 自定义背景绘制示例 Example of custom date's background
		tmp = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			tmp.add(list.get(i).get("timename"));
		}
//        tmp.add("2015-7-1");
//        tmp.add("2015-7-8");
//        tmp.add("2015-7-16");
		DPCManager.getInstance().setDecorBG(tmp);

		DatePicker2 picker = (DatePicker2) findViewById(R.id.main_dp);
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		picker.setDate(year, month);
		picker.setDPDecor(new DPDecor() {
			@Override
			public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
				paint.setColor(Color.RED);
				canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2F, paint);
//				Toast.makeText(YuanbanDemoActivity.this, "111 ---", Toast.LENGTH_SHORT).show();
			}
		});
		//签到按钮(右上角)
		picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
			@Override
			public void onDateSelected(List<String> date) {


				Iterator iterator = date.iterator();
				while (iterator.hasNext()) {
					result += iterator.next();
					if (iterator.hasNext()) {
						result += "\n";
					}
				}


				new AlertDialog.Builder(YuanbanDemoActivity.this)
						.setTitle("加班签到")
						.setMessage("今天为" + result + ",确定签到加班？")
						.setPositiveButton("是", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								SigndateService service = new SigndateDAO(YuanbanDemoActivity.this);
								//Object[] params = {"张三","北京","男"};
								Object[] params = {result, "加班", "1"}; //新增加一条记录
								boolean flag = service.addPersion(params);
								if (flag) {
									Toast.makeText(YuanbanDemoActivity.this, result + " 已签到加班", Toast.LENGTH_SHORT).show();

								}
							}
						})
						.setNegativeButton("否", null)
						.show();


//				Toast.makeText(YuanbanDemoActivity.this, result + " 已签到加班", Toast.LENGTH_SHORT).show();
			}
		});

		// 自定义前景装饰物绘制示例 Example of custom date's foreground decor
		List<String> tmpTL = new ArrayList<>();
		tmpTL.add("2015-10-5");
		tmpTL.add("2015-10-6");
		tmpTL.add("2015-10-7");
		tmpTL.add("2015-10-8");
		tmpTL.add("2015-10-9");
		tmpTL.add("2015-10-10");
		tmpTL.add("2015-10-11");
		DPCManager.getInstance().setDecorTL(tmpTL);

		List<String> tmpTR = new ArrayList<>();
		tmpTR.add("2015-10-10");
		tmpTR.add("2015-10-11");
		tmpTR.add("2015-10-12");
		tmpTR.add("2015-10-13");
		tmpTR.add("2015-10-14");
		tmpTR.add("2015-10-15");
		tmpTR.add("2015-10-16");
		DPCManager.getInstance().setDecorTR(tmpTR);

//        DatePicker2 picker = (DatePicker2) findViewById(R.id.main_dp);
//        picker.setDate(2015, 10);
//        picker.setFestivalDisplay(false);
//        picker.setTodayDisplay(false);
//        picker.setHolidayDisplay(false);
//        picker.setDeferredDisplay(false);
//        picker.setMode(DPMode.NONE);
//        picker.setDPDecor(new DPDecor() {
//            @Override
//            public void drawDecorTL(Canvas canvas, Rect rect, Paint paint, String data) {
//                super.drawDecorTL(canvas, rect, paint, data);
//                switch (data) {
//                    case "2015-10-5":
//                    case "2015-10-7":
//                    case "2015-10-9":
//                    case "2015-10-11":
//                        paint.setColor(Color.GREEN);
//                        canvas.drawRect(rect, paint);
//                        break;
//                    default:
//                        paint.setColor(Color.RED);
//                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
//                        break;
//                }
//            }
//
//            @Override
//            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint, String data) {
//                super.drawDecorTR(canvas, rect, paint, data);
//                switch (data) {
//                    case "2015-10-10":
//                    case "2015-10-11":
//                    case "2015-10-12":
//                        paint.setColor(Color.BLUE);
//                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
//                        break;
//                    default:
//                        paint.setColor(Color.YELLOW);
//                        canvas.drawRect(rect, paint);
//                        break;
//                }
//            }
//        });
//        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
//            }
//        });

		// 对话框下的DatePicker示例 Example in dialog
		Button btnPick = (Button) findViewById(R.id.main_btn);
		btnPick.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final AlertDialog dialog = new AlertDialog.Builder(YuanbanDemoActivity.this).create();
				dialog.show();
				DatePicker2 picker = new DatePicker2(YuanbanDemoActivity.this);
				picker.setDate(2015, 10);
				picker.setMode(DPMode.SINGLE);
				picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
					@Override
					public void onDatePicked(String date) {
//						Toast.makeText(YuanbanDemoActivity.this, date, Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
				});
				ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				dialog.getWindow().setContentView(picker, params);
				dialog.getWindow().setGravity(Gravity.CENTER);
			}
		});
	}


	Runnable load = new Runnable() {
		@Override
		public void run() {

		}
	};


	//加班签到警示框
	private void showAlert() {

	}

	@OnClick(R.id.btnSearchDate)
	public void onViewClicked() {

		String searchDate = etSearchDate.getText().toString();
		for (int i = 0; i < tmp.size(); i++) {
			if (getStr(tmp.get(i), searchDate)) {
				flag = flag + 1;
				listTime.add(tmp.get(i).split("\\-")[2] + "号 ");
			}
		}

		new AlertDialog.Builder(YuanbanDemoActivity.this)
				.setTitle("加班天数")
				.setMessage("共加班" + flag + ",加班日期为" + listTime.toString())
				.setNegativeButton("返回", null)
				.show();
		flag = 0;
		listTime.clear();


	}

	public boolean getStr(String date, String searchDate) {
		String[] input = searchDate.split("\\:");//2017:7:15:2017:8:11格式
		String[] search = date.split("\\-");
		if (Integer.parseInt(search[0]) <= Integer.parseInt(input[3]) && Integer.parseInt(search[0]) >= Integer.parseInt(input[0])) {
			if (Integer.parseInt(search[1]) <= Integer.parseInt(input[4]) && Integer.parseInt(search[1]) >= Integer.parseInt(input[1])) {
				if (Integer.parseInt(search[1]) == Integer.parseInt(input[1])) {
					if (Integer.parseInt(search[2]) >= Integer.parseInt(input[2])) {
						return true;
					}
				} else if (Integer.parseInt(search[1]) == Integer.parseInt(input[4])) {
					if (Integer.parseInt(search[2]) <= Integer.parseInt(input[5])) {
						return true;
					}
				} else {
					return true;
				}
//				if (Integer.parseInt(search[2]) <= Integer.parseInt(input[5]) && Integer.parseInt(search[2]) >= Integer.parseInt(input[2])) {
//					return true;
//				}
			}
		} else {
			return false;
		}
		return false;

	}
}
