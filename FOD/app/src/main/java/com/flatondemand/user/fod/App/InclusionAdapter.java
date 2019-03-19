package com.flatondemand.user.fod.App;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flatondemand.user.fod.Model.InclusionProperty;
import com.flatondemand.user.fod.R;

import java.util.List;

public class InclusionAdapter extends  RecyclerView.Adapter<InclusionAdapter.MyViewHolder> {
    RequestOptions options ;
    private Context mContext ;
    private List<InclusionProperty> minclusion;
    public InclusionAdapter(Context context , List list){
        this.mContext=context;
        this.minclusion=list;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.icon)
                .error(R.drawable.icon);
    }
    @NonNull
    @Override
    public InclusionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.single_inclusion_layout,viewGroup,false);
        return new InclusionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InclusionAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(minclusion.get(i).getText());
        //myViewHolder.textView.s
        Glide.with(mContext).load(minclusion.get(i).getImage()).apply(options).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return minclusion.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iclusion_image);
            textView=itemView.findViewById(R.id.inclusion_text);
        }
    }
}
