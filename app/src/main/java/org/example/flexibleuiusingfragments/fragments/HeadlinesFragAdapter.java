package org.example.flexibleuiusingfragments.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.example.flexibleuiusingfragments.R;
import org.example.flexibleuiusingfragments.content.HeadlinesProvider.Headline;

import java.util.List;

/**
 * Mainly, the Adapter provides access to the data items defined in our app, by crafting a View
 * for each item in the data set. Some concrete ViewGroup (eg RecyclerView, ListView) uses those
 * Views to provide a limited window into our data set.
 * <p>
 * app-specific data set ---> ADAPTER ---> ViewGroup
 */
public class HeadlinesFragAdapter extends RecyclerView.Adapter<HeadlinesFragAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View     mView;
        public final TextView mView_id;
        public final TextView mView_title;
        public       Headline mHeadline;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mView_id = (TextView) view.findViewById(R.id.id);
            mView_title = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mView_title.getText() + "'";
        }
    }

    private final List<Headline>                            mHeadlines;
    private final HeadlinesFrag.ListItemInteractionObserver mInteractionListener;

    public HeadlinesFragAdapter(List<Headline> headlines, HeadlinesFrag.ListItemInteractionObserver interactionListener) {
        mHeadlines = headlines;
        mInteractionListener = interactionListener;
    }

    /** Our ViewGroup needs a new widget that will represent an item from the data set. */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.fragment_headlines_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Our ViewGroup needs to update a widget so it represents a certain piece of data (located at
     * the specified position within the adapter).
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mHeadline = mHeadlines.get(position);
        holder.mView_id.setText(mHeadlines.get(position).fID);
        holder.mView_title.setText(mHeadlines.get(position).fTitle);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteractionListener.onHeadlineSelected(mHeadlines.get(position).fID);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHeadlines.size();
    }
}
