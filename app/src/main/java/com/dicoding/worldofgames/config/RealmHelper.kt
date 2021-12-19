package com.dicoding.worldofgames.config

import android.util.Log
import com.dicoding.worldofgames.models.GameModel
import io.realm.Realm
import io.realm.RealmResults

class RealmHelper (private val realm: Realm) {

    public fun save(model: GameModel) {
        realm.executeTransaction {
            it.copyToRealm(model)
        }
        Log.d("database", "saved")
    }

    public fun delete(model: GameModel) {
        val deletedModel: RealmResults<GameModel> = realm.where(GameModel::class.java).equalTo("title", model.title).findAll()
        realm.executeTransaction {
            deletedModel.deleteAllFromRealm()
        }
        Log.d("database", "deleted")
    }

    public fun checkIfFavorite(model: GameModel): Boolean {
        val deletedModel: RealmResults<GameModel> = realm.where(GameModel::class.java).equalTo("title", model.title).findAll()
        return deletedModel.size > 0
    }

    public fun getFavoriteGames(): List<GameModel>? {
        return realm.where(GameModel::class.java).findAll()
    }

}