package com.example.alex.skillintensive.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.alex.skillintensive.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Alex on 28.09.2016.
 */

public class RepositoriesAdapter extends BaseAdapter {
    private List<String> mRepoList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public RepositoriesAdapter(Context context, List<String> repoList) {
        mRepoList = repoList;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mRepoList.size();
    }

    @Override
    public Object getItem(int i) {
        return mRepoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if(itemView==null){
            itemView = mLayoutInflater.inflate(R.layout.item_repositories_list, viewGroup, false);
        }

        TextView repoName = (TextView) itemView.findViewById(R.id.user_git_et);
        repoName.setText(mRepoList.get(i));
        return itemView;
    }
}
