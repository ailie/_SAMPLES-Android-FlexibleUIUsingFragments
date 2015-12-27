package org.example.flexibleuiusingfragments.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.example.flexibleuiusingfragments.R;
import org.example.flexibleuiusingfragments.content.HeadlinesProvider;

/**
 * A fragment representing a list of Headlines.
 * <p>
 * Activities containing this fragment MUST implement the {@link ListItemInteractionObserver}
 * interface.
 */
public class HeadlinesFrag extends Fragment {

    /** Propagates back user-generated events. */
    public interface ListItemInteractionObserver {
        void onHeadlineSelected(String selectedHeadlineID);
    }

    private static final int COLUMN_COUNT = 1;

    private ListItemInteractionObserver mUpstreamInteractionListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListItemInteractionObserver) {
            mUpstreamInteractionListener = (ListItemInteractionObserver) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement ListItemInteractionObserver");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_headlines, container, false);

        if (result instanceof RecyclerView) {
            Context context = result.getContext();
            RecyclerView recyclerView = (RecyclerView) result;
            if (COLUMN_COUNT <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, COLUMN_COUNT));
            }
            recyclerView.setAdapter(new HeadlinesFragAdapter(HeadlinesProvider.ITEMS,
                                                             mUpstreamInteractionListener));
        }

        return result;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mUpstreamInteractionListener = null;
    }
}
