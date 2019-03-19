package com.flatondemand.user.fod.App;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flatondemand.user.fod.Model.Property;
import com.flatondemand.user.fod.PropertyView;
import com.flatondemand.user.fod.R;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.HolderView> {
    RequestOptions options ;
    private Context mContext ;
    private List<Property> mData;
    SQLiteHandler sqLiteOpenHelper;

    public PropertyAdapter(Context mContext, List<Property> mData) {
        this.mContext = mContext;
        this.mData = mData;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.image_logo_back)
                .error(R.drawable.image_logo_back);
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.single_property,viewGroup,false);
        // click listener here
        final HolderView viewHolder = new HolderView(view) ;


        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holderView, final int i) {

        holderView.propertyName.setText(mData.get(i).getProperty());
        holderView.propertyPrice.setText(mData.get(i).getPrice());
        Glide.with(mContext).load(mData.get(i).getCoverImage()).apply(options).into(holderView.propertyImage);
      //  holderView.propertyPrice.setText(mData.get(i).);

        holderView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteOpenHelper= new SQLiteHandler(mContext);
                sqLiteOpenHelper.addProperty(mData.get(i).getProperty() ,mData.get(i).getPrice(),
                        mData.get(i).getUid() ,mData.get(i).getCoverImage(), mData.get(i).getAddress());
                //Toast.makeText(mContext  , ""+i + mData.get(i).getAddress() ,Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(mContext , PropertyView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        ImageView propertyImage ;
        TextView propertyName , propertyPrice , propertyType ;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            propertyImage = (ImageView)itemView.findViewById(R.id.propertyImage);
            propertyName = (TextView)itemView.findViewById(R.id.propertyName);
            propertyPrice = (TextView)itemView.findViewById(R.id.proeprtyRent);
            propertyType = (TextView)itemView.findViewById(R.id.flatType);

        }

    }
}
