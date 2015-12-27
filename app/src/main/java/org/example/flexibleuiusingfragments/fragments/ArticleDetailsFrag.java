package org.example.flexibleuiusingfragments.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.example.flexibleuiusingfragments.R;
import org.example.flexibleuiusingfragments.content.HeadlinesProvider;

public class ArticleDetailsFrag extends Fragment {

    public static final String ARG_HEADLINE_ID = "too4Zeer";
    private View fView_root;

    {
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return fView_root = inflater.inflate(R.layout.fragment_article_details, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getURI() != null) {
            refresh();
        }
    }

    public String getURI() {
        return getArguments().getString(ARG_HEADLINE_ID);
    }

    public ArticleDetailsFrag setURI(String headlineID) {
        getArguments().putString(ARG_HEADLINE_ID, headlineID);
        return this;
    }

    public void refresh() {
        if (isResumed()) {
            final String articleBody = HeadlinesProvider.ITEMS_MAP.get(getURI()).fDetails;
            ((TextView) fView_root.findViewById(R.id.articleBody)).setText(articleBody);
        } else {
            throw new IllegalStateException(
                    "Only call this when the fragment is visible to the user and actively running.");
        }
    }
}
