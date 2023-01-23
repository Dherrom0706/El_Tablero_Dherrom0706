package www.iesmurgi.eltablero

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
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
                    it.x
                    it.y
                }
                l2.addView(tv)
                tv.setOnClickListener {

                }
            }
            miLin.addView(l2)

        }


    }
    fun miRandom(): Int{

        return Random.nextInt(0,topElement)

    }
}