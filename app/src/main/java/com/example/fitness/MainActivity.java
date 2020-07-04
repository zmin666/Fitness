package com.example.fitness;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private RecyclerView recyclerView;
    private TextView tvTime;
    private TextView tvAdd;

    ArrayList<SportBean> project = new ArrayList<>();
    private ProjectAdpter projectAdpter;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationBar(this, View.GONE);

        textView = (TextView) findViewById(R.id.textView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvAdd = (TextView) findViewById(R.id.tv_add);

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyStyle();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        projectAdpter = new ProjectAdpter(project, this);
        projectAdpter.setListener(new ProjectAdpter.Listener() {
            @Override
            public void clickItem(String s, int position) {
                notifyProjectAdapter(position);
                setTime(project.get(position).getTime());
            }

            @Override
            public void longClickItem(String s, int position) {
                project.remove(position);
                projectAdpter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.course_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(projectAdpter);
        initProject();
    }

    private void setTime(int time) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long l = millisUntilFinished / 1000;
                tvTime.setText(change(l));
            }

            @Override
            public void onFinish() {
                tvTime.setText("00:00");
            }
        };
        timer.start();
    }

    private void notifyProjectAdapter(int position) {
        for (int i = 0; i < project.size(); i++) {
            project.get(i).setSelect(i == position);
        }
        projectAdpter.notifyDataSetChanged();
    }

    public String change(long l) {
        String s = String.valueOf(l);
        if (s.length() == 1) {
            return "00" + " : 0" + s;
        } else if (l > 60) {
            long m = l / 60;
            long s1 = l % 60;
            String s2 = String.valueOf(s1);
            if (s2.length() == 1) {
                s2 = "0" + s2;
            }
            return m + " : " + s2;
        } else {
            return "00" + " : " + s;
        }
    }

    private void initProject() {
        SportBean s1 = new SportBean(30, "开合跳", "个");
        SportBean s2 = new SportBean(30, "深蹲跳", "个");
        SportBean s3 = new SportBean(30, "高抬腿", "秒");
        SportBean s4 = new SportBean(30, "后踢臀", "个");
        project.add(s1);
        project.add(s2);
        project.add(s3);
        project.add(s4);
        projectAdpter.notifyDataSetChanged();
    }

    private void showMyStyle() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_test, null);
        final EditText name = view.findViewById(R.id.editText);
        final EditText time = view.findViewById(R.id.editText2);
        final RadioGroup rg = view.findViewById(R.id.RadioGroup1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(view).setTitle("添加运动项目").setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String str = rg.getCheckedRadioButtonId() == R.id.rb1 ? "秒" : "个";
                                SportBean s = new SportBean(Integer.parseInt(time.getText().toString()), name.getText().toString(), str);
                                project.add(s);
                                projectAdpter.notifyDataSetChanged();
                            }
                        }
                )
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.create().show();
    }

    public static void setNavigationBar(Activity activity, int visible) {
        View decorView = activity.getWindow().getDecorView();
        //显示NavigationBar
        if (View.GONE == visible) {
            int option = SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
        }
    }
}
