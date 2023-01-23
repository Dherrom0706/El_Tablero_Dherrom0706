package www.iesmurgi.eltablero

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.service.quicksettings.Tile
import android.util.DisplayMetrics
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class GameField : AppCompatActivity() {

    val colores = arrayOf(Color.RED, Color.GREEN,Color.BLUE,Color.BLACK,Color.YELLOW,Color.MAGENTA)
    val numeros = arrayOf(1,2,3,4,5,6)
    lateinit var dibujos: Array<Int>
    var topTileX : Int = 0
    var topTileY : Int = 0
    var topElement : Int = 0
    var sonido : Boolean = false
    var vibracion : Boolean = false
    lateinit var ids: Array<IntArray>
    lateinit var values : Array<IntArray>
    var contador: Int = 0
    lateinit var mp:MediaPlayer
    lateinit var vibratorService: Vibrator
    lateinit var textView: TextView

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_field)

        textView = findViewById(R.id.textoTablero)
        topTileX = intent.extras?.getInt("X")!!
        topTileY = intent.extras?.getInt("Y")!!

        ids = Array(topTileX){ IntArray(topTileY) }
        values =  Array(topTileX){ kotlin.IntArray(topTileY) }

        topElement = intent.extras?.getInt("CANTIDAD")!!
        sonido = intent.extras?.getBoolean("SONIDO")!!
        vibracion = intent.extras?.getBoolean("VIBRACION")!!

        var miLin: LinearLayout = findViewById(R.id.tableroId)
        miLin.removeAllViews()
        val dm:DisplayMetrics = resources.displayMetrics
        var height = (dm.heightPixels - 500)/topTileY
        var width = dm.widthPixels / topTileX

        var miCrono: Chronometer = findViewById(R.id.cronometro)
        miCrono.start()
        var tv: TileView
        var ident = 0
        for (i in 0 .. topTileY-1){
            var l2: LinearLayout = LinearLayout(this)
            l2.orientation = LinearLayout.HORIZONTAL
            for (j in 0 .. topTileX-1){
                var tramaToShow = miRandom()
                values[j][i] = tramaToShow

                tv= TileView(this,j,i,topElement,tramaToShow,tramaToShow)
                ident++

                tv.id = ident
                ids[j][i] = ident
                tv.layoutParams = LinearLayout.LayoutParams(0,height,1.0f)
                tv.setOnClickListener {
                    var x = it.x
                    var y = it.y
                    if (sonido)
                        mp.start()
                    if (vibracion){
                        vibratorService = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as Vibrator
                        if (vibratorService.hasVibrator()){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                vibratorService.vibrate(
                                    VibrationEffect.createOneShot(
                                        100,
                                        VibrationEffect.DEFAULT_AMPLITUDE
                                    )
                                )
                            }else{
                                vibratorService.vibrate(100)
                            }
                        }
                    }

                   if (x == 0f && y == 0f){
                       changeView(0,1)
                       changeView(1,0)
                       changeView(1,1)
                   }else if (x == 0f && y == (topTileY - 1).toFloat()){
                       changeView(0,topTileY-2)
                       changeView(1,topTileY-2)
                       changeView(1,topTileY-1)
                   }else if (x == (topTileX - 1).toFloat() && y == 0f){
                       changeView(topTileX-2,0)
                       changeView(topTileX-2,1)
                       changeView(topTileX-1,1)
                   }else if (x == (topTileX - 1).toFloat() && y == (topTileY - 1).toFloat()){
                       changeView(topTileX-2,topTileY-2)
                       changeView(topTileX-2,topTileY-1)
                       changeView(topTileX-1,topTileY-2)
                   }
                   else if (x == 0f){
                       changeView(x.toInt(),(y-1).toInt())
                       changeView(x.toInt(),(y+1).toInt())
                       changeView((x+1).toInt(),y.toInt())
                   }else if (y == 0f){
                       changeView((x-1).toInt(),y.toInt())
                       changeView((x+1).toInt(),y.toInt())
                       changeView(x.toInt(),(y+1).toInt())
                   }else if (x == (topTileX - 1).toFloat()){
                       changeView(x.toInt(),(y-1).toInt())
                       changeView(x.toInt(),(y+1).toInt())
                       changeView((x-1).toInt(),y.toInt())
                   }else if (y == (topTileY - 1).toFloat()){
                       changeView((x-1).toInt(),y.toInt())
                       changeView((x+1).toInt(),y.toInt())
                       changeView(x.toInt(),(y-1).toInt())
                   }else{
                       changeView((x-1).toInt(),y.toInt())
                       changeView((x+1).toInt(),y.toInt())
                       changeView(x.toInt(),(y-1).toInt())
                       changeView(x.toInt(),(y+1).toInt())
                   }

                }
                l2.addView(tv)
                tv.setOnClickListener {

                }
            }
            miLin.addView(l2)

        }



    }
    fun changeView(x: Int, y:Int){
        var tt: TileView = findViewById(ids[x][y])
        var newIndex = tt.getNewIndex()
        values[x][y] = newIndex
        tt.setBackgroundResource(dibujos[newIndex])
        tt.invalidate()
    }
    fun checkIfFinished(){
        var valor = values[0][0]
        for (i in 0 .. topTileY){
            for (j in 0 .. topTileX){
                if (values[j][i] != valor)
                    return
            }
        }


    }

    fun miRandom(): Int{

        return Random.nextInt(0,topElement)

    }
}