package com.example.smartpantrymanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.VH> {
    public interface Listener {
        void edit(Ingredient ingredient);
        void delete(Ingredient ingredient);
    }

    private List<Ingredient> list;
    private final Listener listener;

    public PantryAdapter(List<Ingredient> list, Listener listener) {
        this.list = list;
        this.listener = listener;
    }

    public void setItems(List<Ingredient> items) {
        list = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Ingredient ingredient = list.get(position);
        holder.name.setText(ingredient.getName());

        SharedPreferences preferences = holder.itemView.getContext()
                .getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean showExpiry = preferences.getBoolean("show_expiry", true);

        String details = ingredient.getQuantity() + " " + ingredient.getUnit();
        if (showExpiry && ingredient.getExpiryDate() != null && !ingredient.getExpiryDate().isEmpty()) {
            details += " | Expiry: " + ingredient.getExpiryDate();
        }
        holder.details.setText(details);
        holder.edit.setOnClickListener(v -> listener.edit(ingredient));
        holder.delete.setOnClickListener(v -> listener.delete(ingredient));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView name;
        TextView details;
        Button edit;
        Button delete;

        VH(View view) {
            super(view);
            name = view.findViewById(R.id.txtName);
            details = view.findViewById(R.id.txtDetails);
            edit = view.findViewById(R.id.btnEdit);
            delete = view.findViewById(R.id.btnDelete);
        }
    }
}
