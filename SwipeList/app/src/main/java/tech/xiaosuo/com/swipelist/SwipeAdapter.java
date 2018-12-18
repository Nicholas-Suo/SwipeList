package tech.xiaosuo.com.swipelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SwipeAdapter extends BaseAdapter {


    List<String> data;
    Context mContext;
    public SwipeAdapter(Context context){
        mContext = context;
    }

    public void setData(List<String> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public String getItem(int i) {
        return data == null ? null : data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemHolder holder = null;
        final int position = i;
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.swipe_item,viewGroup,false);
            holder = new ItemHolder();
            holder.itemText = (TextView)view.findViewById(R.id.item_text);
         //  holder.itemDelImg = (ImageView)view.findViewById(R.id.item_delete_img);
            view.setTag(holder);
        }else{
            holder = (ItemHolder)view.getTag();
        }
        holder.itemText.setText(getItem(i));
/*        holder.itemDelImg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext," the delet img click: position " + position,Toast.LENGTH_SHORT).show();
            }
        });*/
        return view;
    }

    class ItemHolder {
        TextView itemText;
        ImageView itemDelImg;
    }
}
