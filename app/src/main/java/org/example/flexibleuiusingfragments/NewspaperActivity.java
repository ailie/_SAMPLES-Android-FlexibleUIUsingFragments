package org.example.flexibleuiusingfragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.example.flexibleuiusingfragments.fragments.ArticleDetailsFrag;
import org.example.flexibleuiusingfragments.fragments.HeadlinesFrag;

public class NewspaperActivity extends AppCompatActivity
        implements HeadlinesFrag.ListItemInteractionObserver {

    private static final String TAG_FRAG_HEADLINES       = "Aeghah0K";
    private static final String TAG_FRAG_ARTICLE_DETAILS = "bael8Ies";

    private static final String ARG_HEADLINE_ID = "Lo4phi3u";

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
        }

        showHeadlinesFragIfNeeded();
        showArticleDetailsFragIfNeeded();
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

        if (fNumOfPanesInLayout == 2 && getAddedFrag(TAG_FRAG_ARTICLE_DETAILS) != null) {
            ArticleDetailsFrag displayFrag = (ArticleDetailsFrag) getSupportFragmentManager()
                    .findFragmentByTag(TAG_FRAG_ARTICLE_DETAILS);
            displayFrag.setURI(selectedHeadlineID);
            if (displayFrag.isResumed())
                displayFrag.refresh();

        } else if (fNumOfPanesInLayout == 1 && getAddedFrag(TAG_FRAG_ARTICLE_DETAILS) == null) {
            ArticleDetailsFrag articleDetailsFrag = new ArticleDetailsFrag().setURI(
                    selectedHeadlineID);
            newFT().replace(R.id.pane1Headlines, articleDetailsFrag, TAG_FRAG_ARTICLE_DETAILS)
                   .addToBackStack(null)
                   .commit();

        } else {
            final StringBuilder msg = new StringBuilder();
            msg.append("\nfNumOfPanesInLayout = " + fNumOfPanesInLayout);
            throw new IllegalStateException(msg.toString());
        }
    }

    private void showArticleDetailsFragIfNeeded() {

        final ArticleDetailsFrag oldFrag = (ArticleDetailsFrag)
                getAddedFrag(TAG_FRAG_ARTICLE_DETAILS);

        final ArticleDetailsFrag newFrag = new ArticleDetailsFrag();

        if (oldFrag == null) {
            if (fNumOfPanesInLayout == 2)
                newFT().add(R.id.pane2ArticleDetails, newFrag, TAG_FRAG_ARTICLE_DETAILS).commit();

        } else if (fNumOfPanesInLayout == 1 && oldFrag.getId() == R.id.pane2ArticleDetails) { // the ArticleDetailsFrag comes from the 2P layout
            newFT().remove(oldFrag).commit();   // not adding to back stack on purpose
            if (mCurSelHeadlineID != null)
                newFT().replace(R.id.pane1Headlines, newFrag.setURI(mCurSelHeadlineID),
                                TAG_FRAG_ARTICLE_DETAILS).addToBackStack(null).commit();

        } else if (fNumOfPanesInLayout == 2 && oldFrag.getId() == R.id.pane1Headlines) { // the ArticleDetailsFrag comes from the 1P layout and obstructs the HeadlinesFrag
            getSupportFragmentManager().popBackStackImmediate(null, 0);
            newFT().add(R.id.pane2ArticleDetails, newFrag.setURI(mCurSelHeadlineID),
                        TAG_FRAG_ARTICLE_DETAILS).commit();
        }
    }

    private void showHeadlinesFragIfNeeded() {
        if (getAddedFrag(TAG_FRAG_HEADLINES) == null)
            newFT().add(R.id.pane1Headlines, new HeadlinesFrag(), TAG_FRAG_HEADLINES).commit();
    }

    /** @return a new FragmentTransaction */
    @SuppressLint("CommitTransaction")
    private FragmentTransaction newFT() {
        return getSupportFragmentManager().beginTransaction();
    }

    /** This first searches the manager's activity, then the back stack. */
    private Fragment getAddedFrag(String fragTag) {
        return getSupportFragmentManager().findFragmentByTag(fragTag);
    }
}