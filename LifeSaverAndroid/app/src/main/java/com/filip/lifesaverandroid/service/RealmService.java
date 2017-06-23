package com.filip.lifesaverandroid.service;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class RealmService {

    private static RealmService instance;
    public static final String REALM_DB_NAME = "realm_db_name";

    public static RealmService instance(Context context) {
        if (instance == null) {
            instance = new RealmService(context);
        }
        return instance;
    }

    private Context context;
    private Realm mRealm;

    private RealmService(Context context) {
        this.context = context;
        Realm.init(context);
    }


    public Realm buildRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(REALM_DB_NAME)
                .build();
        Realm.setDefaultConfiguration(config);

        mRealm = Realm.getInstance(config);

        return mRealm;
    }


    public Realm getRealm() {
        return mRealm;
    }

}
