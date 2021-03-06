package org.example.flexibleuiusingfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.example.flexibleuiusingfragments.fragments.ArticleDetailsFrag;
import org.example.flexibleuiusingfragments.fragments.HeadlinesFrag;

public class NewspaperActivity extends AppCompatActivity
        implements HeadlinesFrag.ListItemInteractionObserver {


    public static final String ARG_HEADLINE_ID = "Lo4phi3u";

    private String mCurSelHeadlineID;

    /** panes available in the current layout */
    private int fNumOfPanesInLayout;

    @Override
    public void onHeadlineSelected(String selectedHeadlineID) {
        mCurSelHeadlineID = selectedHeadlineID;
        displayArticleDetails(selectedHeadlineID);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_HEADLINE_ID, mCurSelHeadlineID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);

        determineNumOfPanesInLayout();

        if (savedInstanceState != null) {
            mCurSelHeadlineID = savedInstanceState.getString(ARG_HEADLINE_ID);
            displayArticleDetails(mCurSelHeadlineID);
        }
    }

    private void determineNumOfPanesInLayout() {
        if (findViewById(R.id.pane1Headlines) != null) {
            fNumOfPanesInLayout = findViewById(R.id.pane2ArticleDetails) != null ? 2 : 1;
        } else {
            throw new IllegalStateException();
        }
    }

    private void displayArticleDetails(String selectedHeadlineID) {

    /* On configuration change (eg screen rotation), the system might re-create a previously-used
     * fragment, using its previous savedInstanceState. This instantiation might take place even
     * though the app, in its current state, does not create that fragment (eg nor declaratively in
     * the current activity layout, neither dynamically in the activity's code). See android.support.v4.app.Fragment.isInLayout */

        if (fNumOfPanesInLayout == 2) {
            ArticleDetailsFrag displayFrag = (ArticleDetailsFrag) getSupportFragmentManager()
                    .findFragmentById(R.id.fragArticleDetails);
            displayFrag.setURI(selectedHeadlineID);
            if (displayFrag.isResumed())
                displayFrag.refresh();

        } else if (fNumOfPanesInLayout == 1) {
            Intent intent = new Intent(this, NewspaperArticleDetailsActivity1P.class);
            intent.putExtra(ARG_HEADLINE_ID, selectedHeadlineID);
            startActivity(intent);

        } else {
            final StringBuilder msg = new StringBuilder();
            msg.append("\nfNumOfPanesInLayout = " + fNumOfPanesInLayout);
            throw new IllegalStateException(msg.toString());
        }
    }
}