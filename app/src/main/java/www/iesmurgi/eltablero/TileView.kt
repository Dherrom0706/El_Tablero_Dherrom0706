package www.iesmurgi.eltablero

import android.content.Context
import android.graphics.Color
import androidx.appcompat.widget.AppCompatButton

open class Button
class TileView(context: Context,x:Int,y:Int,topElementos:Int,index:Int,backgroud:Int): AppCompatButton(context){

    var coordenadaX : Int = x
    var coordenadaY : Int = y
    //index es la posicion en la que nos encontramos de todos los colores o numeros
    var index : Int = index
    //maximo que tomara index
    var topElementos: Int = topElementos
    var backgroud: Int = backgroud


    fun getNewIndex(): Int{

        index++
        if (index > topElementos)
            index = 1

        backgroud = index

        return index

    }

    fun colores() {
        when(backgroud){
            1->{
                setBackgroundColor(Color.RED)
            }
            2->{
                setBackgroundColor(Color.BLUE)
            }
            3->{
                setBackgroundColor(Color.MAGENTA)
            }
            4->{
                setBackgroundColor(Color.GRAY)
            }
            5->{
                setBackgroundColor(Color.YELLOW)
            }
            6->{
                setBackgroundColor(Color.CYAN)
            }

        }
    }

    fun numeros() {
        when(backgroud){
            1->{
                setText("1")
            }
            2->{
                setText("2")
            }
            3->{
                setText("3")
            }
            4->{
                setText("4")
            }
            5->{
                setText("5")
            }
            6->{
                setText("6")
            }
        }
    }

}