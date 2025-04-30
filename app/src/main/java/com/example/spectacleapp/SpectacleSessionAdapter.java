package com.example.spectacleapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpectacleSessionAdapter extends RecyclerView.Adapter<SpectacleSessionAdapter.SessionViewHolder> {

    private List<Spectacle> sessionList;
    private Context context;

    public SpectacleSessionAdapter(List<Spectacle> sessionList, Context context) {
        this.sessionList = sessionList;
        this.context = context;
    }

    // ✅ Corrected updateList() method
    public void updateList(List<Spectacle> newList) {
        sessionList.clear();
        sessionList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_session, parent, false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        Spectacle s = sessionList.get(position);
        holder.sessionInfo.setText(s.date + " at " + s.time + "\n📍 " + s.lieu);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SpectacleDetailsActivity.class);
            intent.putExtra("titre", s.titre);
            intent.putExtra("lieu", s.lieu);
            intent.putExtra("desc", getSpectacleDescription(s.titre));
            intent.putExtra("imageRes", s.imageRes);
            intent.putExtra("date", s.date);
            intent.putExtra("time", s.time);
            context.startActivity(intent);
        });

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

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    static class SessionViewHolder extends RecyclerView.ViewHolder {
        TextView sessionInfo;

        public SessionViewHolder(@NonNull View itemView) {
            super(itemView);
            sessionInfo = itemView.findViewById(R.id.tvSessionInfo);
        }
    }
}
