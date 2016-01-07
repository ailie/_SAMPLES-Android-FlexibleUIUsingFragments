package org.example.flexibleuiusingfragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.example.flexibleuiusingfragments.fragments.ArticleDetailsFrag;

public class NewspaperArticleDetailsActivity1P extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper_article_details);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ArticleDetailsFrag displayFrag = (ArticleDetailsFrag)
                getSupportFragmentManager().findFragmentById(R.id.fragArticleDetails);

        displayFrag.setURI(getIntent().getStringExtra(NewspaperActivity.ARG_HEADLINE_ID));
    }
}
