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

    public class AdapterBookedTables extends ArrayAdapter<BookedTable> {

        private Context mContext;
        private int mResource;

        public AdapterBookedTables(@NonNull Context context, int resource, @NonNull ArrayList<BookedTable> objects) {
            super(context, resource, objects);
            this.mContext=context;
            this.mResource=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView=layoutInflater.inflate(mResource,parent,false);
            TextView tableNumber = convertView.findViewById(R.id.foodName);
            TextView tableSize = convertView.findViewById(R.id.foodPrice);
            TextView startDate = convertView.findViewById(R.id.startDateTextView);
            TextView endDate = convertView.findViewById(R.id.endDateTextView);
            tableNumber.setText(getItem(position).getTableNumber());
            String number = tableNumber.getText().toString();
            if(Integer.parseInt(number)<=5){
                tableSize.setText("4");
            }else{
                tableSize.setText("2");
            }
            startDate.setText(getItem(position).getStartDate());
            endDate.setText(getItem(position).getEndDate());
            return convertView;
        }
    }
