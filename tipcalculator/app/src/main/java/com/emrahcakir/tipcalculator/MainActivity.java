package com.emrahcakir.tipcalculator;
import com.emrahcakir.tipcalculator.R;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
public class MainActivity extends AppCompatActivity {
    private EditText girisTutar;
    private SeekBar bahsisYuzdesiKaydirici;
    private TextView metinYuzde, metinSonuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        girisTutar = findViewById(R.id.editTextAmount);
        bahsisYuzdesiKaydirici = findViewById(R.id.seekBarTip);
        metinYuzde = findViewById(R.id.textViewPercentage);
        metinSonuc = findViewById(R.id.textViewResult);
        bahsisYuzdesiKaydirici.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                metinYuzde.setText(progress + "%");
                bahsisiHesapla();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        girisTutar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bahsisiHesapla();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            girisTutar.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(girisTutar, InputMethodManager.SHOW_IMPLICIT);
        }
    }
    private void bahsisiHesapla() {
        String input = girisTutar.getText().toString().trim();
        if (input.isEmpty()) {
            metinSonuc.setText("Lütfen bir tutar girin.");
            return;
        }
        try {
            double tutar = Double.parseDouble(input);
            int bahsisYuzdesi = bahsisYuzdesiKaydirici.getProgress();
            double bahsisMiktari = tutar * bahsisYuzdesi / 100;
            double toplamTutar = tutar + bahsisMiktari;
            String sonucMetni = String.format("Bahşiş: ₺%.2f\nToplam: ₺%.2f", bahsisMiktari, toplamTutar);
            metinSonuc.setText(sonucMetni);
        } catch (NumberFormatException e) {
            metinSonuc.setText("Lütfen geçerli bir sayı girin.");        }
    }
}