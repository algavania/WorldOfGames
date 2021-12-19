package com.dicoding.worldofgames.models

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject

open class GameModel(
    var title: String = "",
    var genre: String = "",
    var platform: String = "",
    var releaseDate: String = "",
    var publisher: String = "",
    var developer: String = "",
    var description: String = "",
    var thumbnail: String = "",
    var url: String = ""
) : Parcelable, RealmObject() {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(genre)
        parcel.writeString(platform)
        parcel.writeString(releaseDate)
        parcel.writeString(publisher)
        parcel.writeString(developer)
        parcel.writeString(description)
        parcel.writeString(thumbnail)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameModel> {
        override fun createFromParcel(parcel: Parcel): GameModel {
            return GameModel(parcel)
        }

        override fun newArray(size: Int): Array<GameModel?> {
            return arrayOfNulls(size)
        }
    }
}
