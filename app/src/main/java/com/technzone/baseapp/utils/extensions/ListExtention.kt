package com.technzone.baseapp.utils.extensions

fun <M> List<M>.get(index:Int):M?{
    return try{
        this[index]
    }catch (e:Exception){
        null
    }
}
//fun <M> List<M>?.get(index:Int):M?{
//    return try{
//        this?.get(index)
//    }catch (e:Exception){
//        null
//    }
//}