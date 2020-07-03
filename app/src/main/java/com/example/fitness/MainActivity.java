package com.example.fitness;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private RecyclerView recyclerView;
    private TextView tvTime;

    ArrayList<SportBean> project = new ArrayList<>();
    private ProjectAdpter projectAdpter;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvTime = (TextView) findViewById(R.id.tvTime);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        projectAdpter = new ProjectAdpter(project, this);
        projectAdpter.setListener(new ProjectAdpter.Listener() {
            @Override
            public void clickItem(String s, int position) {
                notifyProjectAdapter(position);
                timer.start();
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.course_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(projectAdpter);
        initProject();

        timer = new CountDownTimer(120000, 1000) {
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
        SportBean s1 = new SportBean(30, "开合跳", "30个");
        SportBean s2 = new SportBean(30, "深蹲跳", "30个");
        SportBean s3 = new SportBean(30, "高抬腿", "30秒");
        SportBean s4 = new SportBean(30, "后踢臀", "30个");
        project.add(s1);
        project.add(s2);
        project.add(s3);
        project.add(s4);
        projectAdpter.notifyDataSetChanged();
    }
}
