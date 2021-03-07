package com.sevketbuyukdemir.soccerleague.view_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sevketbuyukdemir.soccerleague.R;
import com.sevketbuyukdemir.soccerleague.models.SoccerTeam;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for recyclerView which is in the MainActivity UI components.
 */
public class MainActivityRecyclerViewAdapter extends
        RecyclerView.Adapter<MainActivityRecyclerViewAdapter.ViewHolder>{
    LayoutInflater layoutInflater;
    Context context;
    List<SoccerTeam> soccerTeams;

    /**
     *
     * @param context Context
     * @param soccerTeams List<SoccerTeam>
     */
    public MainActivityRecyclerViewAdapter(Context context, List<SoccerTeam> soccerTeams) {
        this.context = context;
        this.soccerTeams = soccerTeams;
    }

    /**
     * Called when RecyclerView needs a new of the given type to represent an item.
     * @param parent ViewGroup
     * @param viewType int
     * @return vh ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.soccer_team_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * if soccerTeams is null return empty item
     * Else fill item with soccerTeams' List
     * @param holder ViewHolder NonNull
     * @param position int
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(soccerTeams.size() == 0){
            holder.iv_team_icon.setImageResource(R.drawable.app_icon);
            holder.tv_team_name.setText(context.getString(R.string.main_activity_recycler_view_adapter_dataset_is_null));
        } else {
            SoccerTeam soccerTeam = soccerTeams.get(position);
            String imgPath = soccerTeam.getTeamIconURL();

            // Lazy Load Image With Picasso (also With caching)
            Picasso.get().load(imgPath)
                    .placeholder(R.drawable.app_icon)
                    .error(android.R.drawable.ic_menu_close_clear_cancel)
                    .into(holder.iv_team_icon);
            holder.tv_team_name.setText(soccerTeam.getTeamName());
        }
        //card view
        holder.card.setTag(holder);
    }

    /**
     * Items represent Soccer Teams' List
     * @return SoccerTeam List<SoccerTeam> soccerTeams size
     */
    @Override
    public int getItemCount() {
        return (soccerTeams == null) ? 0 : soccerTeams.size();
    }

    /**
     * Change shown items when dataset change
     * @param soccerTeams List<SoccerTeam>
     */
    public void setTeamList(List<SoccerTeam> soccerTeams) {
        this.soccerTeams = soccerTeams;
        notifyDataSetChanged();
    }

    /**
     * Item UI elements initiation with ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_team_icon;
        TextView tv_team_name;
        LinearLayout card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_team_icon = itemView.findViewById(R.id.iv_team_icon);
            tv_team_name = itemView.findViewById(R.id.tv_team_name);
            card = itemView.findViewById(R.id.soccer_team_item);
        }
    }
}