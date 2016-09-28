package com.example.alex.skillintensive.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.skillintensive.R;
import com.example.alex.skillintensive.data.storage.models.UserDTO;
import com.example.alex.skillintensive.ui.adapters.RepositoriesAdapter;
import com.example.alex.skillintensive.util.ConstantManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileUserActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ImageView mProfileImage;
    private EditText mBio;
    private TextView mRating, mCodeLines, mProjects;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private ListView mRepoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProfileImage = (ImageView) findViewById(R.id.user_photo_img);
        mBio = (EditText) findViewById(R.id.user_bio_et);
        mRating = (TextView) findViewById(R.id.reat_tv);
        mCodeLines = (TextView) findViewById(R.id.coding_tv);
        mProjects = (TextView) findViewById(R.id.project_tv);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mRepoList = (ListView) findViewById(R.id.repositories_list);

        setupToolBar();
        initProfileData();


    }

    private void setupToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void initProfileData(){
        UserDTO userDTO = getIntent().getParcelableExtra(ConstantManager.PARCELABLE_KEY);

        final List<String> repositories = userDTO.getRepositories();
        RepositoriesAdapter repositoriesAdapter = new RepositoriesAdapter(this, repositories);
        mRepoList.setAdapter(repositoriesAdapter);

        mRepoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent lookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+repositories.get(i)));
                startActivity(lookIntent);
            }
        });
        mBio.setText(userDTO.getBio());
        mRating.setText(userDTO.getRating());
        mCodeLines.setText(userDTO.getCodeLines());
        mProjects.setText(userDTO.getProjects());

        mCollapsingToolbarLayout.setTitle(userDTO.getFullName());

        Picasso.with(this)
                .load(userDTO.getPhoto())
                .placeholder(R.drawable.user_bg)
                .error(R.drawable.user_bg)
                .into(mProfileImage);
    }
}
