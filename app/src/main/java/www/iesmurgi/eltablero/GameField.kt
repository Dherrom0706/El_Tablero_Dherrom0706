package www.iesmurgi.eltablero

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.DisplayMetrics
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class GameField : AppCompatActivity() {


    private var numberOfClicks = 0
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
    lateinit var opcion: String
    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_field)

        textView = findViewById(R.id.textoTableroContador)
        topTileX = intent.extras?.getInt("X")!!
        topTileY = intent.extras?.getInt("Y")!!

        ids = Array(topTileX){ IntArray(topTileY) }
        values =  Array(topTileX){ kotlin.IntArray(topTileY) }

        topElement = intent.extras?.getInt("CANTIDAD")!!
        sonido = intent.extras?.getBoolean("SONIDO")!!
        vibracion = intent.extras?.getBoolean("VIBRACION")!!

        opcion = intent.extras?.getString("OPCION").toString()

        var miLin: LinearLayout = findViewById(R.id.tableroId)
        miLin.removeAllViews()
        val dm:DisplayMetrics = resources.displayMetrics
        var height = (dm.heightPixels - 500)/topTileY


        var miCrono: Chronometer = findViewById(R.id.cronometro)
        miCrono.start()
        var tv: TileView
        var ident = 0

        if (sonido){
            mp = MediaPlayer.create(this,R.raw.doom_musica)
            mp.start()
        }

        for (y in 0 until topTileY){

            var l2: LinearLayout = LinearLayout(this)
            l2.orientation = LinearLayout.HORIZONTAL
            for (x in 0 until topTileX){
                var tramaToShow = miRandom()
                values[x][y] = tramaToShow

                println(tramaToShow)
                tv= TileView(this,x,y,topElement,tramaToShow,tramaToShow)
                ident++
                if (opcion == "colores"){
                    tv.colores()
                }else{
                    tv.numeros()
                }
                tv.id = ident
                ids[x][y] = ident

                //metodo para cambiar cambiar celdas
                cambiarCelda(tv, x, y)


                tv.layoutParams = LinearLayout.LayoutParams(0,height,1.0f)
                l2.addView(tv)

            }
            miLin.addView(l2)

        }

    }


    @SuppressLint("ServiceCast")
    private fun cambiarCelda(tv: TileView, x: Int, y: Int) {
        tv.setOnClickListener {

            /*var posX = it.x.toInt()
            var posY = it.y.toInt()*/

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

            if (x == 0 && y == 0){
                changeView(0,1)
                changeView(1,0)
                changeView(1,1)
            }else if (x == 0 && y == (topTileY - 1)){
                changeView(0,topTileY-2)
                changeView(1,topTileY-2)
                changeView(1,topTileY-1)
            }else if (x == (topTileX - 1) && y == 0){
                changeView(topTileX-2,0)
                changeView(topTileX-2,1)
                changeView(topTileX-1,1)
            }else if (x == (topTileX - 1) && y == (topTileY - 1)){
                changeView(topTileX-2,topTileY-2)
                changeView(topTileX-2,topTileY-1)
                changeView(topTileX-1,topTileY-2)
            }
            else if (x == 0){
                changeView(x,(y-1).toInt())
                changeView(x,(y+1).toInt())
                changeView((x+1),y.toInt())
            }else if (y == 0){
                changeView((x-1),y)
                changeView((x+1),y)
                changeView(x,(y+1))
            }else if (x == (topTileX - 1)){
                changeView(x,(y-1))
                changeView(x,(y+1))
                changeView((x-1),y)
            }else if (y == (topTileY - 1)){
                changeView((x-1),y)
                changeView((x+1),y)
                changeView(x,(y-1))
            }else{
                changeView((x-1),y)
                changeView((x+1),y)
                changeView(x,(y-1))
                changeView(x,(y+1))
            }
            println(x.toString()+""+y.toString())
            tv.getNewIndex()
            changeView(x,y)
            numberOfClicks++
            var campoClicks = findViewById<TextView>(R.id.textoTableroContador)
            campoClicks.text = (campoClicks.text.toString().toInt() + 1).toString()
            checkIfFinished()

        }
    }


    fun changeView(x: Int, y:Int){
        print(x+y)
        var tt: TileView = findViewById(ids[x][y])
        var newIndex = tt.getNewIndex()
        values[x][y] = newIndex
        if (opcion == "colores"){
            tt.colores()
        }else if(opcion == "numeros"){
            tt.numeros()
        }
        tt.invalidate()
    }

    fun checkIfFinished(){
        var valor = values[0][0]
        for (i in 0 until topTileY){
            for (j in 0 until topTileX){
                if (values[j][i] != valor){
                    return
                }else if(values[j][i] == valor){
                    Toast.makeText(this, "Ha ganado con un total de $numberOfClicks",Toast.LENGTH_SHORT).show()
                }

            }
        }
        var resultIntent: Intent = Intent()
        resultIntent.putExtra("CLICKS",numberOfClicks)
        setResult(RESULT_OK,resultIntent)
        finish()
    }

    fun miRandom(): Int{

        return (1..topElement).random()

    }
}