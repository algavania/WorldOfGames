package com.dicoding.worldofgames.models

import io.realm.RealmObject
import java.io.Serializable

open class GameModel(var title: String = "", var genre: String = "", var platform: String = "", var releaseDate: String = "", var publisher: String = "", var developer: String = "",
                     var description: String = "", var thumbnail: String = "", var url: String = "") : Serializable, RealmObject()
