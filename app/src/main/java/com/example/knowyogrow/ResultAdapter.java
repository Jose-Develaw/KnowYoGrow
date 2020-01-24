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

    ArrayList<Result> datos = new ArrayList<>();
    Listener listener;

    public void setListener(Listener l) {this.listener = l;}

    public ResultAdapter(ArrayList<Result> datos) {

        this.datos = datos;
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

        String strainName = datos.get(position).getStrainVariety();
        holder.strainName.setText(strainName);
        String strainRace = datos.get(position).getRace();
        holder.strainRace.setText(strainRace);
        Picasso.get().load(datos.get(position).getImage()).resize(120, 120).transform(new ImageRoundCorners()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView strainName;
        TextView strainRace;

        public ViewHolder(View view) {

            super(view);
            image = view.findViewById(R.id.strainImage);
            strainName = view.findViewById(R.id.strainName);
            strainRace = view.findViewById(R.id.strainRace);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onResultClick();
            }
        }
    }

    public interface Listener {
        void onResultClick();
    }
}
