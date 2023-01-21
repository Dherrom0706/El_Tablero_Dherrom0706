package www.iesmurgi.eltablero

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import www.iesmurgi.eltablero.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurar_seekBars_y_textos(binding.seekBarCantidad,binding.tvCantidad,2,6)
        configurar_seekBars_y_textos(binding.seekBarTableroX,binding.tvEjeX, 3,6)
        configurar_seekBars_y_textos(binding.seekBarTableroY,binding.tvEjeY,3,6)

        //modificacion chechBox con sonido
        val soundOn = MediaPlayer.create(this,R.raw.sound_on)
        val soundOff = MediaPlayer.create(this,R.raw.sound_off)
        binding.checkBoxSonido.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                soundOn.start()
            }
            if (!isChecked){
                soundOff.start()
            }
        }

        //modificacion chechBox con vibracion
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        binding.checkBoxVibracion.setOnCheckedChangeListener { _, isCheckec ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }

        //comenzar GameFieldActivity
        binding.btnEmpezarJuego.setOnClickListener {
            startPlay()
        }
    }

    private fun startPlay() {

        val intent = Intent(this,GameField::class.java)
        val barraX = findViewById<SeekBar>(R.id.seekBarTableroX)
        intent.putExtra("X",barraX.progress)
        val barraY = findViewById<SeekBar>(R.id.seekBarTableroY)
        intent.putExtra("Y",barraY.progress)
        val barraTrama = findViewById<SeekBar>(R.id.seekBarCantidad)
        intent.putExtra("CANTIDAD",barraTrama.progress)
        val chechBoxSonido = findViewById<CheckBox>(R.id.checkBoxSonido)
        intent.putExtra("SONIDO",chechBoxSonido.isChecked)
        val chechBoxVibracion = findViewById<CheckBox>(R.id.checkBoxVibracion)
        intent.putExtra("VIBRACION",chechBoxVibracion.isChecked)
        val selectedId = findViewById<RadioGroup>(R.id.radioGroupOpciones).checkedRadioButtonId
        val selectedRadioButton = findViewById<RadioButton>(selectedId)
        if (selectedRadioButton == findViewById(R.id.radioBColores)){
            intent.putExtra("OPCION","colores")
        }else if(selectedRadioButton == findViewById(R.id.radioBNumeros)){
            intent.putExtra("OPCION","numeros")
        }

        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun configurar_seekBars_y_textos(seekBar: SeekBar, textView: TextView, min: Int, max: Int){

        seekBar.max = max
        seekBar.min = min
        seekBar.keyProgressIncrement = 1

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                textView.text = p0?.progress.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_opciones, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.jugador-> {
                mostrarPlayer()
                return true
            }
            R.id.ayuda -> {
                mostrarHowTo()
                return true
            }
            R.id.sobre -> {
                mostrarAbout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun mostrarAbout() {
        startActivity(Intent(this,About::class.java))
    }

    private fun mostrarHowTo() {
        startActivity(Intent(this,HowTo::class.java))
    }

    private fun mostrarPlayer() {

    }
}