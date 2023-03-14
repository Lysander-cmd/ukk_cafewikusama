package com.readthefuckingmanual.fuckukk.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.readthefuckingmanual.fuckukk.data.model.menu.ListMenuResponse
import com.readthefuckingmanual.fuckukk.data.model.menu.MenuModel
import com.readthefuckingmanual.fuckukk.data.source.remote.datasource.MenuRemoteDataSource

object MenuRepository {
    // implements the methods from the MenuDataSources


    val selectedmenu : MutableLiveData<MenuModel?> = MutableLiveData()
    val keranjang : MutableLiveData<ArrayList<MenuModel>> = MutableLiveData(ArrayList())
//    val keranjang : LiveData<ArrayList<MenuModel>> = _keranjang

    fun addToKeranjang(menuModel: MenuModel) {
        if (keranjang.value!!.size < 10){
            keranjang.value?.add(menuModel)
            Log.d("MenuRepository", "observeSelectedMenu: ${keranjang.value?.size}")
        }

    }
    fun clearKeranjang() {
        keranjang.value?.clear()
        Log.d("MenuRepository", "observeSelectedMenu: ${keranjang.value?.size}")
    }
    fun removeFromKeranjang(menuModel: MenuModel) {
        keranjang.value?.remove(menuModel)
        Log.d("MenuRepository", "observeSelectedMenu: ${keranjang.value?.size}")
    }

    fun getAllMenus(token : String) : LiveData<ListMenuResponse?> {
        MenuRemoteDataSource.apply {
            getListMenu(token)
            return listMenu
        }
    }

    fun getMenuById(token : String, id_menu : Int) : LiveData<MenuModel?> {
        MenuRemoteDataSource.apply {
            getMenuById(token, id_menu)
            return _menu
        }
    }

    fun addMenu(token : String, menuModel: MenuModel) : LiveData<MenuModel?> {
        MenuRemoteDataSource.apply {
            addMenu(token, menuModel)
            return _menu
        }
    }
    fun edtMenu(token: String, menuModel: MenuModel) : LiveData<MenuModel?> {
        MenuRemoteDataSource.apply {
            edtMenu(token, menuModel)
            return _menu
        }
    }

}