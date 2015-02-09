package com.wyxz.chaogao.adapter;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseListAdapter<T > extends BaseAdapter {
    
    private Context ctx;
    private List<T> arrayList;
    
    public BaseListAdapter(Context ctx) {
        this.ctx = ctx;
        arrayList = new ArrayList<T>();
    }
    
    public void appendData(List<T> data) {
        if (arrayList != null&&data!=null) {
            arrayList.addAll(data);
        }
        notifyDataSetChanged();
    }
    
    public void clear() {
        if (arrayList != null) {
            arrayList.clear();
        }
    }
    
    @Override
    public int getCount() {
        return arrayList.isEmpty() ? 0 : arrayList.size();
    }
    
    @Override
    public T getItem(int position) {
        T t = null;
        if (!arrayList.isEmpty())
            t = arrayList.get(position);
        return t;
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = createConvertView(position, parent);
        }
        
        freshConvertView(position, convertView, parent);
        return convertView;
    }
    
    protected abstract View createConvertView(int position, ViewGroup parent);
    
    protected abstract void freshConvertView(int position, View convertView, ViewGroup parent);
    
    public void remove(T t) {
        arrayList.remove(t);
        notifyDataSetChanged();
    }
    
    public void remove(int index) {
        arrayList.remove(index);
        notifyDataSetChanged();
    }
    
    public List<T> getList(){
        return arrayList;
    }
}
