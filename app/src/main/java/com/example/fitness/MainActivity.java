package com.example.fitness;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitness.dialog.AddDialog;
import com.example.fitness.dialog.TipsDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private RecyclerView recyclerView;
    private TextView tvTime;
    private TextView tvCount;

    ArrayList<SportBean> project = new ArrayList<>();
    private ProjectAdpter projectAdpter;
    private CountDownTimer timer;
    private int index;
    private FloatingActionButton fab;
    private static final String DATA_PROJECT = "data_project";
    private String strDay;
    private long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationBar();
        textView = (TextView) findViewById(R.id.textView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvCount = (TextView) findViewById(R.id.tv_count);
        fab = findViewById(R.id.fab);
        strDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        projectAdpter = new ProjectAdpter(project, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.course_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(projectAdpter);
        initListener();
        initProject();
        showTipsDialog();
    }

    private void showTipsDialog() {
        int tips = SPUtils.getInstance().getInt("tips", 10);
        if (tips > 0) {
            TipsDialog tipsDialog = new TipsDialog(this);
            tipsDialog.show();
            SPUtils.getInstance().put("tips", --tips);
        }
    }

    private void initListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyStyle();
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                project.clear();
                projectAdpter.notifyDataSetChanged();
                return true;
            }
        });

        projectAdpter.setListener(new ProjectAdpter.Listener() {

            @Override
            public void clickItem(String s, int position) {
                index = position;
                notifyProjectAdapter(position);
                setTime(project.get(position).getTime());
            }

            @Override
            public void longClickItem(String s, int position) {
                project.remove(position);
                projectAdpter.notifyDataSetChanged();
                notifyCount();
            }
        });
    }

    private void setTime(int time) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentTime = millisUntilFinished;
                long l = millisUntilFinished / 1000;
                tvTime.setText(change(l));
            }

            @Override
            public void onFinish() {
                notifyCount();
                tvTime.setText("00 : 00");
            }
        };
        timer.start();
    }

    private void notifyCount() {
        int count = project.get(index).getCount();
        project.get(index).setCount(count + 1);
        projectAdpter.notifyDataSetChanged();

        int countTotal = 0;
        for (SportBean sportBean : project) {
            if (countTotal == 0) {
                countTotal = sportBean.getCount();
            } else {
                countTotal = Math.min(countTotal, sportBean.getCount());
            }
        }
        tvCount.setText("已完成 " + countTotal + " 组");
    }

    private void notifyProjectAdapter(int position) {
        for (int i = 0; i < project.size(); i++) {
            project.get(i).setSelect(i == position);
        }
        projectAdpter.notifyDataSetChanged();
    }


    @Override
    protected void onPause() {
        if (timer != null) {
            timer.cancel();
        }
        SPUtils.getInstance().put("currentTime", currentTime / 1000);
        super.onPause();
    }

    @Override
    protected void onRestart() {
        long currentTime = SPUtils.getInstance().getLong("currentTime", project.get(index).getTime());
        if (currentTime > 0) {
            setTime((int) (currentTime + 1));
        }
        super.onRestart();
    }

    @Override
    protected void onStop() {
        //保存成绩
        Gson gson = new Gson();
        String s = gson.toJson(project);
        SPUtils.getInstance().put(strDay, s);

        //保存运动项目
        for (SportBean sportBean : project) {
            sportBean.setCount(0);
        }
        Gson gson1 = new Gson();
        String s1 = gson1.toJson(project);
        SPUtils.getInstance().put(DATA_PROJECT, s1);
        super.onStop();
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
        Gson gson = new Gson();
        String currentData = SPUtils.getInstance().getString(strDay);
        String projectData = SPUtils.getInstance().getString(DATA_PROJECT);
        ArrayList<SportBean> list = gson.fromJson(TextUtils.isEmpty(currentData) ? projectData : currentData, new TypeToken<ArrayList<SportBean>>() {
        }.getType());
        if (list != null) {
            for (SportBean sportBean : list) {
                sportBean.setSelect(false);
            }
            project.addAll(list);
        }

        if (project.size() == 0) {
            SportBean s1 = new SportBean(10, "开合跳", "个");
            SportBean s2 = new SportBean(10, "深蹲跳", "个");
            SportBean s3 = new SportBean(10, "高抬腿", "秒");
            SportBean s4 = new SportBean(10, "后踢臀", "个");
            project.add(s1);
            project.add(s2);
            project.add(s3);
            project.add(s4);
        }
        projectAdpter.notifyDataSetChanged();
    }

    private void showMyStyle() {
        AddDialog addDialog = new AddDialog(this);
        addDialog.setOnListener(new AddDialog.OnListener() {
            @Override
            public void click(SportBean sportBean) {
                project.add(sportBean);
                projectAdpter.notifyDataSetChanged();
                notifyCount();
            }
        });
        addDialog.show();
    }

    public void setNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
