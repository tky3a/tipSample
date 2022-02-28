package ja.tky.tiptime

import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ja.tky.tiptime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            displayTip()
            return
        }

        // チップのパーセント
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 20.0
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // チップの料金
        var tip = tipPercentage * cost

        // 端数切り上げ処理
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        displayTip(tip)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun displayTip(tip: Double = 0.0) {
        // 数値を通貨にフォーマット(デバイスに設定されている国の形式になる)
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        // strings.xmlの値をformattedTipに変更して表示
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)

    }

}