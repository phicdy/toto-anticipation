package com.phicdy.totoanticipation.view.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.phicdy.totoanticipation.R;
import com.phicdy.totoanticipation.model.Game;

import java.util.List;

class TotoAnticipationRecyclerViewAdapter extends RecyclerView.Adapter<TotoAnticipationRecyclerViewAdapter.ViewHolder> {

    private final List<Game> mValues;

    TotoAnticipationRecyclerViewAdapter(List<Game> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_totoanticipation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvHome.setText(mValues.get(position).homeTeam);
        holder.tvAway.setText(mValues.get(position).awayTeam);
        switch (mValues.get(position).anticipation) {
            case HOME:
                holder.cbHome.setChecked(true);
                break;
            case DRAW:
                holder.cbDraw.setChecked(true);
                break;
            case AWAY:
                holder.cbAway.setChecked(true);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView tvHome;
        final TextView tvAway;
        final CheckBox cbHome;
        final CheckBox cbDraw;
        final CheckBox cbAway;
        Game mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            tvHome = (TextView) view.findViewById(R.id.tv_home);
            tvAway = (TextView) view.findViewById(R.id.tv_away);
            cbHome = (CheckBox) view.findViewById(R.id.cb_home);
            cbDraw = (CheckBox) view.findViewById(R.id.cb_draw);
            cbAway = (CheckBox) view.findViewById(R.id.cb_away);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvAway.getText() + "'";
        }
    }
}
