package www.iesmurgi.eltablero

import android.content.Context

open class Button
class TileView(context: Context,x:Int,y:Int,topElementos:Int,index:Int,backgroud:Int): Button() {

    var coordenadaX : Int = x
    var coordenadaY : Int = y
    //index es la posicion en la que nos encontramos de todos los colores o numeros
    var index : Int = index
    //maximo que tomara index
    var topElementos: Int = topElementos

    fun getNewIndex(): Int{

        index++
        if (index > topElementos)
            index = 0
        return index

    }

}