package com.example.test_commit;

/**
 * Created by ThanhHang on 10/15/2017.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class MessageAdapter extends ArrayAdapter<ChatBubble> {

    private Context activity;
    private List<ChatBubble> messages;
    private Bitmap bitmap;

    public MessageAdapter(Context context, int resource, List<ChatBubble> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        ChatBubble ChatBubble = getItem(position);
        int viewType = getItemViewType(position);

        if (ChatBubble.myMessage()==1) {
            layoutResource = R.layout.left_chat_bubble;
        }
        if (ChatBubble.myMessage()==2)
        {
            layoutResource = R.layout.right_chat_bubble;
        }
        if (ChatBubble.myMessage()==3)
        {
            layoutResource = R.layout.right_chat_bubble;
        }
        if (ChatBubble.myMessage()==3)
        {
            layoutResource = R.layout.image_chat_bubble;
        }

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        if (ChatBubble.myMessage()==1) {
            holder.msg.setText(ChatBubble.getContent());
        }
        if (ChatBubble.myMessage()==2)
        {
            holder.msg.setText(ChatBubble.getContent());
        }
        if (ChatBubble.myMessage()==3)
        {


           // holder.iv.setImageBitmap(bmp);

        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        return position % 2;
    }
    private class ViewHolder {
        private TextView msg;
        private ImageView iv;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.txt_msg);
            iv=(ImageView)v.findViewById(R.id.image_msg);
        }
    }
}