package www.iesmurgi.eltablero

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
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
        binding.btnEmpezarJuego.setOnClickListener {
            startPlay()
        }
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
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        binding.checkBox2.setOnCheckedChangeListener { _, isCheckec ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }
    }

    private fun startPlay() {

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
}