package com.mmga.cocode.ui.adapter;


import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mmga.cocode.R;
import com.mmga.cocode.data.model.Topic;
import com.mmga.cocode.util.AvatarUrlUtil;
import com.mmga.cocode.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<Topic> itemList =new ArrayList<>();
    Topic topic;
    Uri uri;

    public RecyclerViewAdapter() {

    }

    public void setAdapterData(List<Topic> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }

    public void addAdapterData(List<Topic> topics) {
        this.itemList.addAll(topics);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.latest_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        topic = itemList.get(position);
        holder.title.setText(topic.getTitle());
        holder.bgaLayout.showTextBadge("" + topic.getPostsCount());
        holder.topicInfos.setText(new StringBuilder().append("最后由 ").append(topic.getLastPosterUsername()).append(" · ").append(DateUtil.parseDate(topic.getLastPostedAt())).toString());

        uri = Uri.parse(AvatarUrlUtil.getAvatarUrl(topic.getAvatarUrl(), AvatarUrlUtil.AVATAR_SIZE_SMALL));
        holder.avatar.setImageURI(uri);
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

        @Bind(R.id.bga_layout)
        BGABadgeLinearLayout bgaLayout;

        @Bind(R.id.avatar)
        SimpleDraweeView avatar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
