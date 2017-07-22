package test.hotline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.hotline.R;

/**
 * Created by tanmay on 22/07/17.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] text;
    private final int[] images;
    private final int[] color;

    public GridAdapter(Context c,String[] text,int[] images,int[] color ) {
        mContext = c;
        this.images = images;
        this.text = text;
        this.color = color;
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_row, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            LinearLayout linearLayout = (LinearLayout)grid.findViewById(R.id.linear_layout);
            textView.setText(text[position]);
            imageView.setImageResource(images[position]);
            linearLayout.setBackgroundColor(mContext.getResources().getColor(color[position]));
        } else {
            grid = (View) convertView;
        }
        return grid;
    }
}