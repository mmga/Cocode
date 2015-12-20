package com.mmga.cocode.data.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmga.cocode.R;
import com.mmga.cocode.data.data.model.Topic;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<Topic> itemList;

    public RecyclerViewAdapter() {

    }

    public void setRecyclerViewAdapterData(List<Topic> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.main_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Topic topic = itemList.get(position);
        holder.title.setText(topic.getTitle());
        holder.topicInfos.setText("最后由 "+ topic.getLastPosterUsername() +" · "+topic.getLastPostedAt());
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.topic_info)
        TextView topicInfos;

        @Bind(R.id.title)
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
