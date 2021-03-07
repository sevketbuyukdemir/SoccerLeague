package com.sevketbuyukdemir.soccerleague.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sevketbuyukdemir.soccerleague.R;
import com.sevketbuyukdemir.soccerleague.view_adapters.FixtureFragmentRecyclerViewAdapter;
import org.jetbrains.annotations.NotNull;


/**
 * Fixture Fragment contain one RecyclerView and this recyclerView show one week matches.
 */
public class FixtureFragment extends Fragment {
    private Context context;
    /**
     * id show week order 1 ... n
     */
    private int id;
    /**
     * RecyclerView show matches and its adapter fixtureFragmentRecyclerViewAdapter.
     * For detailed information -> look at the adapter class.
     */
    private RecyclerView weeklyFixtureRecyclerView;
    private FixtureFragmentRecyclerViewAdapter fixtureFragmentRecyclerViewAdapter;

    public FixtureFragment(int id){
        this.id = id;
    }

    /**
     * This function initialize RecyclerView and its adapter.
     * Set layout manager to RecyclerView if its not app will crash.
     * Set adapter to RecyclerView
     * @param view View -> fragment UI component
     */
    private void init(View view){
        weeklyFixtureRecyclerView = view.findViewById(R.id.weekly_fixture_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        weeklyFixtureRecyclerView.setLayoutManager(llm);
        fixtureFragmentRecyclerViewAdapter = new FixtureFragmentRecyclerViewAdapter(context, id);
        weeklyFixtureRecyclerView.setAdapter(fixtureFragmentRecyclerViewAdapter);
    }

    /**
     * This override function triggered when fragment creation start like onCreate method in
     * Activities
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return LayoutInflater
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fixture, container, false);
    }

    /**
     * This override function triggered when creation finish and out UI components ready for
     * initialize.
     * Call init(View) function in this override function.
     * @param view View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        init(view);
    }

    /**
     * This override function triggered when fragment attached to its parent activity
     * I use this function for get context of activity.
     * @param context Context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * This override function triggered when fragment detached to its parent activity
     * I use this function for release context information.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}
