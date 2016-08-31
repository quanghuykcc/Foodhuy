package com.devlin.core.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 8/22/2016.
 */
public class SyncHistory extends RealmObject {

    //region Properties

    @SerializedName("name_table")
    private String mNameTable;

    @SerializedName("last_sync_timestamp")
    private Date mLastSyncTimestamp;

    //endregion

    //region Getter and Setter

    public String getNameTable() {
        return mNameTable;
    }

    public void setNameTable(String nameTable) {
        mNameTable = nameTable;
    }

    public Date getLastSyncTimestamp() {
        return mLastSyncTimestamp;
    }

    public void setLastSyncTimestamp(Date lastSyncTimestamp) {
        mLastSyncTimestamp = lastSyncTimestamp;
    }


    //endregion

}
