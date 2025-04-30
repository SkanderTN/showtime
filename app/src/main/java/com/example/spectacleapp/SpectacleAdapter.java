package com.example.spectacleapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpectacleAdapter extends RecyclerView.Adapter<SpectacleAdapter.SpectacleViewHolder> {

    private final List<Spectacle> list;
    private final Context context;

    public SpectacleAdapter(List<Spectacle> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SpectacleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spectacle, parent, false);
        return new SpectacleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpectacleViewHolder holder, int position) {
        Spectacle s = list.get(position);
        holder.titre.setText(s.titre);
        holder.date.setText("📅 " + s.date);
        holder.time.setText("🕒 " + s.time);
        holder.lieu.setText("🏛️ " + s.lieu);
        holder.duree.setText("⏱️ " + s.duree);
        holder.image.setImageResource(s.imageRes);

        // Open map on lieu click
        holder.lieu.setOnClickListener(v -> {
            String query = Uri.encode(s.lieu);
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + query);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            v.getContext().startActivity(mapIntent);
        });

        // Open SpectacleDetailsActivity with all info
        holder.reserveBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, SpectacleDetailsActivity.class);
            intent.putExtra("titre", s.titre);
            intent.putExtra("lieu", s.lieu);
            intent.putExtra("imageRes", s.imageRes);
            intent.putExtra("date", s.date);
            intent.putExtra("time", s.time);
            intent.putExtra("desc", getSpectacleDescription(s.titre));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class SpectacleViewHolder extends RecyclerView.ViewHolder {
        TextView titre, date, time, lieu, duree;
        ImageView image;
        Button reserveBtn;

        public SpectacleViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.tvTitre);
            date = itemView.findViewById(R.id.tvDate);
            time = itemView.findViewById(R.id.tvTime);
            lieu = itemView.findViewById(R.id.tvLieu);
            duree = itemView.findViewById(R.id.tvDuree);
            image = itemView.findViewById(R.id.imgPoster);
            reserveBtn = itemView.findViewById(R.id.btnReserve);
        }
    }

    public void updateList(List<Spectacle> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    private String getSpectacleDescription(String title) {
        switch (title) {
            case "La Nuit des Stars":
                return "A glamorous night under the stars featuring live performances, music, and a visual journey that will dazzle your senses.";
            case "Soirée Humoristique":
                return "Laugh out loud with a lineup of Tunisia's funniest comedians. Perfect for a stress-relieving, joy-filled evening.";
            case "Danse du Monde":
                return "Travel the globe through dance! Traditional, contemporary, and fusion dance acts all in one cultural celebration.";
            case "Opéra de la Mer":
                return "A majestic opera set near the Mediterranean, merging classical voices with the peaceful sound of ocean waves.";
            case "Comédie Blanche":
                return "A charming comedy play with unexpected twists and heartwarming moments. Family-friendly and clever.";
            case "Festival de Danse":
                return "An intense and energetic show that brings together dance groups from across the region. Pure rhythm!";
            case "Nuit Amazigh":
                return "Celebrate Berber heritage with music, storytelling, and tradition in an unforgettable night of identity and pride.";
            case "Jazz & Chill":
                return "Grab a drink and chill to the smoothest jazz bands under ambient lights. Perfect for couples or a classy solo night.";
            case "Spectacle Magique":
                return "A family-friendly magic show packed with illusions, audience participation, and surprises around every corner.";
            default:
                return "A unique and breathtaking performance that promises an unforgettable evening.";
        }
    }
}
