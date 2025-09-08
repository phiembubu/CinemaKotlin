package com.example.cinema

import android.app.Application
import android.content.Context
import com.example.cinema.prefs.DataStoreManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataStoreManager.init(applicationContext)
        FirebaseApp.initializeApp(this)
    }

    fun getFoodDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance(FIREBASE_URL).getReference("/food")
    }

    fun getCategoryDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance(FIREBASE_URL).getReference("/category")
    }

    fun getMovieDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance(FIREBASE_URL).getReference("/movie")
    }

    fun getBookingDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance(FIREBASE_URL).getReference("/booking")
    }

    fun getQuantityDatabaseReference(foodId: Long): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("/food/$foodId/quantity")
    }

    fun getChatMessageDatabaseReference(): DatabaseReference? {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return if (currentUser != null) {
            FirebaseDatabase.getInstance(FIREBASE_URL).getReference("/chat_messages/${currentUser.uid}")
        } else {
            null
        }
    }


    companion object {
        private const val FIREBASE_URL = "https://huee-60f3b-default-rtdb.firebaseio.com/"
        operator fun get(context: Context?): MyApplication {
            return context!!.applicationContext as MyApplication
        }
    }
}