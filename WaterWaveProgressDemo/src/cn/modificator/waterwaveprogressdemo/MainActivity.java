package cn.modificator.waterwaveprogressdemo;

import cn.modificator.waterwave_progress.WaterWaveProgress;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	WaterWaveProgress waveProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SeekBar bar = (SeekBar) findViewById(R.id.seekBar1);
		waveProgress = (WaterWaveProgress) findViewById(R.id.waterWaveProgress1);
		waveProgress.setShowProgress(true);
		waveProgress.animateWave();
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				setTitle("" + progress);
				waveProgress.setProgress(progress);

			}
		});
		((CheckBox)findViewById(R.id.checkBox1)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					waveProgress.setShowProgress(isChecked);
			}
		});
((CheckBox)findViewById(R.id.checkBox2)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					waveProgress.setShowNumerical(isChecked);
			}
		});
	}
}
