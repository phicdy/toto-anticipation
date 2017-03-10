package com.phicdy.totoanticipation.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phicdy.totoanticipation.R;

public class TeamInfoFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_HOME_TEAM = "hogeTeam";
    public static final String ARG_AWAY_TEAM = "awayTeam";

    /**
     * The dummy content this fragment is presenting.
     */
    private String homeTeam;
    private String awayTeam;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TeamInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_HOME_TEAM)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            homeTeam = getArguments().getString(ARG_HOME_TEAM);
            awayTeam = getArguments().getString(ARG_AWAY_TEAM);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (homeTeam != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(homeTeam);
        }

        return rootView;
    }
}
