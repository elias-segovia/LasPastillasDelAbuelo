package unlam.com.lpda;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SensorsActivity extends Activity implements SensorEventListener {

    //Seteo precisión del sensor para Shake
    private final static float ACC = 15;

    //Declaro los elementos en pantalla
    private Button btnVolver;
    private SensorManager mSensorManager;
    private TextView light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Linkeo el controlador con la activity correspondiente
        setContentView(R.layout.activity_sensores);

        //Linkeo los botones con los elementos de la pantalla correspondientes
        btnVolver = (Button) findViewById(R.id.btnVolver);
        light = (TextView) findViewById(R.id.txtLuz);

        //Pido servicio de sensores
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Seteo listeners a los botones
        btnVolver.setOnClickListener(botonesListeners);
    }

    //Metodo que actua como Listener de los eventos que ocurren en los componentes graficos de la activty
    private View.OnClickListener botonesListeners = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent intent;
            //Se determina que componente genero un evento
            switch (v.getId())
            {
                //Si se ocurrio un evento en el boton OK
                case R.id.btnVolver:
                    //se genera un Intent para poder lanzar la activity principal
                    intent=new Intent(SensorsActivity.this, MainActivity.class);
                    //se inicia la activity principal
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getApplicationContext(),"Error en Listener de botones", Toast.LENGTH_LONG).show();
            }


        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Cada sensor puede lanzar un thread que pase por aqui
        // Para asegurarnos ante los accesos simult�neos sincronizamos esto

        synchronized (this)
        {
            Log.d("sensor", event.sensor.getName());

            switch(event.sensor.getType())
            {
                case Sensor.TYPE_ACCELEROMETER :
                    //Toast.makeText(getApplicationContext(),"Acelerometro!"+event.values[0]+" "+event.values[1]+" "+event.values[2], Toast.LENGTH_LONG).show();
                    if ((Math.abs(event.values[0]) > ACC || Math.abs(event.values[1]) > ACC || Math.abs(event.values[2]) > ACC))
                    {
                        Toast.makeText(getApplicationContext(),"Shakeeeee!", Toast.LENGTH_LONG).show();
                        Log.i("sensor", "running");
                    }
                    break;
                case Sensor.TYPE_PROXIMITY :

                    // Si detecta 0 lo represento
                    if( event.values[0] == 0 )
                    {
                        Toast.makeText(getApplicationContext(),"Proximidad Detectada!", Toast.LENGTH_LONG).show();
                    }
                    break;
                case Sensor.TYPE_LIGHT :
                    light.setText(String.valueOf(event.values[0]));
                    //Toast.makeText(getApplicationContext(),"Luz! "+event.values[0], Toast.LENGTH_LONG).show();
                    break;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
            registrarSensores();
    }

    @Override
    protected void onStop()
    {
        pararSensores();
        super.onStop();
    }

    @Override
    protected void onPause()
    {
        pararSensores();
        super.onPause();
    }

    private void registrarSensores()
    {
        boolean accelerometerConnected, proximityConnected, lightConnected;
        accelerometerConnected = mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),   SensorManager.SENSOR_DELAY_NORMAL);
        proximityConnected = mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),   SensorManager.SENSOR_DELAY_NORMAL);
        lightConnected = mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),   SensorManager.SENSOR_DELAY_NORMAL);

        if (!accelerometerConnected)
        {
            Toast.makeText(this, "Acelerometro no soportado", Toast.LENGTH_SHORT).show();
        }
        if (!proximityConnected)
        {
            Toast.makeText(this, "Proximidad no soportados", Toast.LENGTH_SHORT).show();
        }
        if (!lightConnected)
        {
            Toast.makeText(this, "Luz no soportados", Toast.LENGTH_SHORT).show();
        }
    }

    private void pararSensores()
    {
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
        Log.i("sensor", "unregister");
    }
}
