package com.example.fitness;

/**
 * @author: ZhangMin
 * @date: 2020/7/3 18:21
 * @version: 1.0
 * @desc:
 */
public class SportBean {
    int time;
    String name;
    String str;
    boolean select;
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public SportBean(int time, String name, String str) {
        this.time = time;
        this.name = name;
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public SportBean(int time, String name) {
        this.time = time;
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
