package com.filip.lifesaverandroid.view.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.filip.lifesaverandroid.model.NotificationEntry;
import com.filip.lifesaverandroid.R;
import com.filip.lifesaverandroid.view.INotificationDeletedCallback;


public class NotificationEntriesRecyclerViewAdapter extends AbsBaseEntityRecyclerViewAdapter<NotificationEntry> {


    private INotificationDeletedCallback notificationDeletedCallback;

    public NotificationEntriesRecyclerViewAdapter(Context context, INotificationDeletedCallback notificationDeletedCallback) {
        super(context);
        this.notificationDeletedCallback = notificationDeletedCallback;
    }

    @Override
    public AbsBindViewHolder<NotificationEntry> onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_notification_entry, parent, false);

        return new NotificationEntryViewHolder(view);
    }


    class NotificationEntryViewHolder extends AbsBindViewHolder<NotificationEntry> {

        private TextView title;
        private TextView description;
        private ImageView deleteNotification;

        public NotificationEntryViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_view);
            description = (TextView) itemView.findViewById(R.id.desc_view);
            deleteNotification = (ImageView) itemView.findViewById(R.id.delete_notification);

        }

        @Override
        public void bindView(final NotificationEntry entity, int pos) {

            title.setText(entity.getTitle());
            description.setText(entity.getText());

            deleteNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (notificationDeletedCallback != null) {
                        notificationDeletedCallback.onNotificationDeleted(entity);
                    }
                }
            });



        }
    }

}
