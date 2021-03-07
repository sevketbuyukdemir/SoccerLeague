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

import java.util.Objects;

import static com.sevketbuyukdemir.soccerleague.utils.GlobalVariables.fixture_by_ids;
import static com.sevketbuyukdemir.soccerleague.utils.GlobalVariables.soccerTeamList;

/**
 * Adapter for FixtureFragment.
 * Set information to UI elements of fragment
 */
public class FixtureFragmentRecyclerViewAdapter extends
        RecyclerView.Adapter<FixtureFragmentRecyclerViewAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    Context context;
    /**
     * ID is hold week (0...n) order in fixture. Used for get week matches from
     * fixture_by_ids HashMap for detailed information look at the GlobalVariables.
     */
    int id;

    /**
     * Adapter constructor for pass information and allocate storage as an object.
     * @param context Context
     * @param id int
     */
    public FixtureFragmentRecyclerViewAdapter(Context context, int id) {
        this.context = context;
        this.id = id;
    }

    /**
     * Called when RecyclerView needs a new of the given type to represent an item.
     *
     * @param parent   ViewGroup
     * @param viewType int
     * @return vh ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.fragment_match_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * Pass information to UI elements in this override function.
     * For detailed information read comments which is in the function
     * @param holder ViewHolder
     * @param position int
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (fixture_by_ids.size() == 0) {
            holder.iv_first_team_icon.setImageResource(R.drawable.app_icon);
            holder.tv_first_team_name.setText(context.getString(R.string.main_activity_recycler_view_adapter_dataset_is_null));
            holder.iv_second_team_icon.setImageResource(R.drawable.app_icon);
            holder.tv_second_team_name.setText(context.getString(R.string.main_activity_recycler_view_adapter_dataset_is_null));
        } else {
            int[][] weekFixture = fixture_by_ids.get(id);
            if (weekFixture != null) {
                int[] currentMatch = weekFixture[position];
                SoccerTeam soccerTeamFirst;
                SoccerTeam soccerTeamSecond;

                if((currentMatch[0] == -1) || (currentMatch[1] == -1)){
                    /**
                     * if team count is an odd number each week one team pass as holiday.
                     * We don't want show this team in fixture.
                     */
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                } else {
                    soccerTeamFirst = soccerTeamList.get(currentMatch[0]-1);
                    soccerTeamSecond = soccerTeamList.get(currentMatch[1]-1);

                    String imgPathFirst = soccerTeamFirst.getTeamIconURL();
                    String imgPathSecond = soccerTeamSecond.getTeamIconURL();

                    /**
                     * Lazy Load Image With Picasso (also With caching)
                     */
                    Picasso.get().load(imgPathFirst)
                            .placeholder(R.drawable.app_icon)
                            .error(android.R.drawable.ic_menu_close_clear_cancel)
                            .into(holder.iv_first_team_icon);
                    Picasso.get().load(imgPathSecond)
                            .placeholder(R.drawable.app_icon)
                            .error(android.R.drawable.ic_menu_close_clear_cancel)
                            .into(holder.iv_second_team_icon);
                    holder.tv_first_team_name.setText(soccerTeamFirst.getTeamName());
                    holder.tv_second_team_name.setText(soccerTeamSecond.getTeamName());
                }
            }
        }
        //card view
        holder.card.setTag(holder);
    }

    /**
     * Every int[][] array show one week fixture
     * @return fixture_by_ids length
     */
    @Override
    public int getItemCount() {
        return (fixture_by_ids.get(0) == null) ? 0 : Objects.requireNonNull(fixture_by_ids.get(0)).length;
    }

    /**
     * Item UI elements initiation with ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_first_team_icon;
        ImageView iv_second_team_icon;
        TextView tv_first_team_name;
        TextView tv_second_team_name;
        LinearLayout card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_first_team_icon = itemView.findViewById(R.id.iv_first_team_icon);
            tv_first_team_name = itemView.findViewById(R.id.tv_first_team_name);
            iv_second_team_icon = itemView.findViewById(R.id.iv_second_team_icon);
            tv_second_team_name = itemView.findViewById(R.id.tv_second_team_name);
            card = itemView.findViewById(R.id.fragment_match_item);
        }
    }
}