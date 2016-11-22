package net.sparkeek.farmdroptest.persistence.entities;

import android.os.Parcelable;

import java.io.Serializable;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Persistable;

@Entity
public interface Producers extends Parcelable, Persistable, Serializable {
    @Key
    @Generated
    long getBaseId();

    String getName();

    String getImages();

    String getShort_Description();

    String getDescription();

    String getLocation();
}
