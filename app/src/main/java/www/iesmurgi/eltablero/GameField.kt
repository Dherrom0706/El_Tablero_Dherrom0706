package www.iesmurgi.eltablero

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.*
import org.w3c.dom.Text
import kotlin.properties.Delegates

class GameField : AppCompatActivity() {

    val colores = arrayOf(Color.RED, Color.GREEN,Color.BLUE,Color.BLACK,Color.YELLOW,Color.MAGENTA)
    val numeros = arrayOf(1,2,3,4,5,6)
    lateinit var dibujos: Array<Int>
    var topTileX : Int = 0
    var topTileY : Int = 0
    var topElement : Int = 0
    var sonido : Boolean = false
    var vibracion : Boolean = false
    lateinit var ids: Array<Int>
    lateinit var values : Array<Int>
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
        topElement = intent.extras?.getInt("CANTIDAD")!!
        sonido = intent.extras?.getBoolean("SONIDO")!!
        vibracion = intent.extras?.getBoolean("VIBRACION")!!

        /*
        val selectedId = findViewById<RadioGroup>(R.id.radioGroupOpciones).checkedRadioButtonId
        val selectedRadioButton = findViewById<RadioButton>(selectedId)
        if (selectedRadioButton == findViewById(R.id.radioBColores)){
            intent.putExtra("OPCION","colores")
        }else if(selectedRadioButton == findViewById(R.id.radioBNumeros)){
            intent.putExtra("OPCION","numeros")
        }*/
    }
}