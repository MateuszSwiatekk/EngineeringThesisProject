package pl.swiatek.restaurantclientapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapterOrders extends ArrayAdapter<Order> {
    private Context mContext;
    private int mResource;

    public AdapterOrders(@NonNull Context context, int resource, @NonNull ArrayList<Order> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView=layoutInflater.inflate(mResource,parent,false);
        TextView tableName = convertView.findViewById(R.id.tableText);
        TextView totalPrice = convertView.findViewById(R.id.totalText);

        if(getItem(position).isFinished()==true){
        tableName.setText("Order placed");
        }else{
            tableName.setText("Order in progress");
        }
        String price = String.valueOf(getItem(position).getTotal());
        totalPrice.setText(price);
        return convertView;
    }
}
