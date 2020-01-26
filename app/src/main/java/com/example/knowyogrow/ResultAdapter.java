package com.example.knowyogrow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    ArrayList<StrainComplete> data = new ArrayList<>();
    Listener listener;
    IOnLongClick iOnLongClick;

    public void setiOnLongClick(IOnLongClick iOnLongClick) {
        this.iOnLongClick = iOnLongClick;
    }

    public void setListener(Listener l) {this.listener = l;}

    public ResultAdapter(ArrayList<StrainComplete> datos) {

        this.data = datos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String strainName = data.get(position).getName();
        holder.strainName.setText(strainName);
        String strainRace = data.get(position).getStrain().getRace();
        holder.strainRace.setText(strainRace);
        switch (strainRace) {

            case "sativa" : Picasso.get().load("https://p1.pxfuel.com/preview/599/896/1019/marijuana-cannabis-hash-leaf.jpg").resize(150, 150).transform(new ImageRoundCorners()).into(holder.image);
            break;
            case "indica" : Picasso.get().load("https://cdn.pixabay.com/photo/2017/03/17/20/22/cannabis-2152604_960_720.jpg").resize(150, 150).transform(new ImageRoundCorners()).into(holder.image);
                break;
            case "hybrid" : Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/5/5a/White_widow.jpg").resize(150, 150).transform(new ImageRoundCorners()).into(holder.image);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView image;
        TextView strainName;
        TextView strainRace;

        public ViewHolder(View view) {

            super(view);
            image = view.findViewById(R.id.strainImage);
            strainName = view.findViewById(R.id.strainName);
            strainRace = view.findViewById(R.id.strainRace);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onResultClick(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (iOnLongClick != null) {
                iOnLongClick.showMenu(getAdapterPosition(), view);}
            return true;
        }
    }

    public interface Listener {
        void onResultClick(int position);
    }

    public interface IOnLongClick{

        void showMenu(int position, View view);

    }
}
