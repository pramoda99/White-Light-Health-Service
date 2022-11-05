package com.example.study_with_teddy;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GMusicAdapter extends RecyclerView.Adapter<GMusicAdapter.MyViewHolder>{

    private List<GMusicList> list;
    private final Context context;
    private int playingPosition = 0;
    private final SongChangeListener songChangeListener;

    public GMusicAdapter(List<GMusicList> list, Context context) {
        this.list = list;
        this.context = context;
        this.songChangeListener = ((SongChangeListener) context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gmusic_adapter_layout,null));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GMusicList list2 = list.get(position);

            if(list2.isPlaying()){
                playingPosition = position;
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_blue_10);
        }
        else {
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_10);

        }

        try{

            String generateDuration = String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(list2.getDuration())),
//                    TimeUnit.MILLISECONDS.toMinutes(Long.parseLong("1")),
                TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(list2.getDuration())) -
//                    TimeUnit.MILLISECONDS.toSeconds(Long.parseLong("1")) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(list2.getDuration()))));
//                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Long.parseLong("1"))));

            holder.title.setText(list2.getTitle());
            holder.artist.setText(list2.getArtist());
            holder.musicDuration.setText(generateDuration);

            holder.rootLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    list.get(playingPosition).setPlaying(false);
                    list2.setPlaying(true);

                    songChangeListener.onChanged(position);

                    notifyDataSetChanged();

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }





    }

    public void updateList(List<GMusicList> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rootLayout;
        private final TextView title;
        private final TextView artist;
        private final TextView musicDuration;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rootLayout = itemView.findViewById(R.id.rootLayout);
            title = itemView.findViewById(R.id.musicTitle);
            artist = itemView.findViewById(R.id.musicArtist);
            musicDuration = itemView.findViewById(R.id.musicDuration);
        }
    }
}

