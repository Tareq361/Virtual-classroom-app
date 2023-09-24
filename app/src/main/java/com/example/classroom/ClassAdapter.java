package com.example.classroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassAdapterVH
        > {
    private List<ClassResponse> classResponseList;
    private Context context;
    private ClickListener clickListener;


    public ClassAdapter(ClickListener clickListener) {
        this.clickListener=clickListener;

    }
    public void setData(List<ClassResponse> classResponseList) {
        this.classResponseList=classResponseList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ClassAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();

        return new ClassAdapter.ClassAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_class,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapterVH holder, int position) {
   ClassResponse classResponse=classResponseList.get(position);
   String className=classResponse.getName();
        String classSection=classResponse.getSection();
        holder.classname.setText(className);
        holder.classsection.setText(classSection);
        holder.classname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClicklis(classResponse);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classResponseList.size();
    }

    public class ClassAdapterVH extends RecyclerView.ViewHolder{
        TextView classname;
        TextView classsection;

        public ClassAdapterVH(@NonNull View itemView) {
            super(itemView);
            classname=itemView.findViewById(R.id.classname);
            classsection=itemView.findViewById(R.id.classsection);



        }


    }
    public interface ClickListener {
        void onClicklis(ClassResponse classResponse);
    }
}
