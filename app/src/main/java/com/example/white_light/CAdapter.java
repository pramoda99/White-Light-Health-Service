package com.example.white_light;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CAdapter extends RecyclerView.Adapter<CAdapter.MyViewHolder> {
    //create variables from java classes
    private CShowActivity activity;
    private List<CModel> mList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CAdapter(CShowActivity activity , List<CModel> mList){//overloaded constructor
        this.activity = activity;
        this.mList = mList;
    }

    //update method
    public void updateData(int position){
        CModel item = mList.get(position);
        Bundle bundle = new Bundle();//pass data between activities
        bundle.putString("uId" , item.getId());
        bundle.putString("uAge" , item.getAge());
        bundle.putString("uHeight" , item.getHeight());
        bundle.putString("uWeight" , item.getWeight());
        Intent intent = new Intent(activity , CFlashCardsMain.class);
        intent.putExtras(bundle);//add extended data to intent
        activity.startActivity(intent);
    }

    //delete method
    public void deleteData(int position){
        CModel item = mList.get(position);
        db.collection("BMI").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Details Removed !!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.citem , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.age.setText(mList.get(position).getAge());
        holder.height.setText(mList.get(position).getHeight());
        holder.weight.setText(mList.get(position).getWeight());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView age, height, weight;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            age = itemView.findViewById(R.id.age_text);
            height = itemView.findViewById(R.id.height_text);
            weight = itemView.findViewById(R.id.weight_text);

        }
    }
}