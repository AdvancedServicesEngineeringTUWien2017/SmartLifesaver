package com.filip.lifesaverandroid.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.filip.lifesaverandroid.model.NotificationEntry;
import com.filip.lifesaverandroid.R;
import com.filip.lifesaverandroid.service.RealmService;
import com.filip.lifesaverandroid.view.adapter.NotificationEntriesRecyclerViewAdapter;

import java.util.List;


public class NotificationFragment extends Fragment implements INotificationAdder, INotificationDeletedCallback {

    public static final String TAG = NotificationFragment.class.getSimpleName();

    private RecyclerView notificationRecyclerView;
    private NotificationEntriesRecyclerViewAdapter notificationEntriesRecyclerViewAdapter;

    private RealmService realmService;

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        notificationEntriesRecyclerViewAdapter = new NotificationEntriesRecyclerViewAdapter(getActivity(), this);
        notificationRecyclerView.setAdapter(notificationEntriesRecyclerViewAdapter);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        realmService = RealmService.instance(getActivity());

        List<NotificationEntry> notificationEntries = realmService.getRealm().where(NotificationEntry.class).findAll();
        notificationEntriesRecyclerViewAdapter.addAllItems(notificationEntries);


    }


    @Override
    public void addNotification(NotificationEntry notificationEntry) {
        notificationEntriesRecyclerViewAdapter.addItem(notificationEntry);
    }

    @Override
    public void onNotificationDeleted(NotificationEntry notificationEntry) {

        notificationEntriesRecyclerViewAdapter.removeItem(notificationEntry);

        realmService.getRealm().beginTransaction();
        notificationEntry.deleteFromRealm();
        realmService.getRealm().commitTransaction();

        Toast.makeText(getActivity(), R.string.notification_deleted, Toast.LENGTH_SHORT).show();


    }
}
