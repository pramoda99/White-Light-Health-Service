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

public class PAdapter extends RecyclerView.Adapter<PAdapter.MyViewHolder> {
    //create variables from java classes
    private PShowActivity activity;
    private List<PModel> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public PAdapter(PShowActivity activity , List<PModel> mList){//overloaded constructor
        this.activity = activity;
        this.mList = mList;
    }

    public void updateData(int position){
        PModel item = mList.get(position);
        Bundle bundle = new Bundle();//pass data between activities
        bundle.putString("uId" , item.getId());
        bundle.putString("uTitle" , item.getTitle());
        Intent intent = new Intent(activity , PTodoListsMain.class);
        intent.putExtras(bundle);//add extended data to intent
        activity.startActivity(intent);
    }

    //delete method
    public void deleteData(int position){
        PModel item = mList.get(position);
        db.collection("TodoLists").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Note Removed !!", Toast.LENGTH_SHORT).show();
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
        View v = LayoutInflater.from(activity).inflate(R.layout.pitem, parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(mList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_text);

        }
    }
}