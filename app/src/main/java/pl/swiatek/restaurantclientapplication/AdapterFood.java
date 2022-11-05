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

public class AdapterFood extends ArrayAdapter<FoodItem> {
    private Context mContext;
    private int mResource;

    public AdapterFood(@NonNull Context context, int resource, @NonNull ArrayList<FoodItem> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView=layoutInflater.inflate(mResource,parent,false);
        TextView foodName = convertView.findViewById(R.id.foodName);
        TextView foodPrice = convertView.findViewById(R.id.foodPrice);

        foodName.setText(getItem(position).getFoodName());
        String price = String.valueOf(getItem(position).getPrice());
        foodPrice.setText(price);
        return convertView;
    }
}
