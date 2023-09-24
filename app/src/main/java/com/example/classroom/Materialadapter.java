package com.example.classroom;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Materialadapter extends RecyclerView.Adapter<Materialadapter.MaterailadapterVH> {

    private List<MaterialResponse> materialResponseList;
    private Context context;
    private ClickListener1 clickListener;
    public Materialadapter(ClickListener1 clickListener) {
        this.clickListener=clickListener;

    }
    public void setData(List<MaterialResponse> materialResponseList) {
        this.materialResponseList=materialResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MaterailadapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();

        return new Materialadapter.MaterailadapterVH(LayoutInflater.from(context).inflate(R.layout.row_material,parent,false));
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull Materialadapter.MaterailadapterVH holder, int position) {
        MaterialResponse materialResponse=materialResponseList.get(position);
        String Title=materialResponse.getTitle();
        String Time=materialResponse.getCreated_at();
        String newtime="";
        try { SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Time=sdf.parse(Time).toString(); }
        catch (Exception e) {  }

     for(int i=0;i<10;i++){
         newtime=newtime+Time.charAt(i);
     }

        holder.title.setText(Title);
        holder.time.setText(newtime);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClicklis(materialResponse);
            }
        });
    }

    @Override
    public int getItemCount() {
         return materialResponseList.size();
    }

    public class MaterailadapterVH extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        public MaterailadapterVH(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            time=itemView.findViewById(R.id.time);
        }
    }
    public interface ClickListener1 {
        void onClicklis(MaterialResponse materialResponse);
    }
}
