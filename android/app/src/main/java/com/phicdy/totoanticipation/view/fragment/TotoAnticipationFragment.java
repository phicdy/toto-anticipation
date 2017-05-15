package com.phicdy.totoanticipation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phicdy.totoanticipation.R;
import com.phicdy.totoanticipation.model.storage.GameListStorageImpl;

public class TotoAnticipationFragment extends Fragment {

    public TotoAnticipationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_totoanticipation_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            GameListStorageImpl storage = new GameListStorageImpl(context);
            String totoNum = storage.totoNum();
            recyclerView.setAdapter(new TotoAnticipationRecyclerViewAdapter(storage.list(totoNum)));
        }
        return view;
    }
}
