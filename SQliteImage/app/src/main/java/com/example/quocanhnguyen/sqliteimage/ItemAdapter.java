package com.example.quocanhnguyen.sqliteimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Item> itemList;

    public ItemAdapter(Context context, int layout, List<Item> itemList) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtvName, txtvDescription;
        ImageView imgImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtvName = (TextView) convertView.findViewById(R.id.textViewNameCustom);
            holder.txtvDescription = (TextView) convertView.findViewById(R.id.textViewDescribeCustom);
            holder.imgImage = (ImageView) convertView.findViewById(R.id.imageImageCustom);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = itemList.get(position);

        holder.txtvName.setText(item.getName());
        holder.txtvDescription.setText(item.getDescription());

        // convert byte[] -> bitmap
        byte[] Images = item.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Images, 0, Images.length);
        holder.imgImage.setImageBitmap(bitmap);

        return convertView;
    }
}
