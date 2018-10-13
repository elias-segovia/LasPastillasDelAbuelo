package unlam.com.lpda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Declaro los elementos en pantalla
    private Button btnPlanificar, btnSensores, btnSincro;
    private TextView txtPlanificacionAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Linkeo el controlador con la activity correspondiente
        setContentView(R.layout.activity_main);

        //Linkeo los botones con los elementos de la pantalla correspondientes
        btnPlanificar = (Button) findViewById(R.id.btnPlanificar);
        btnSensores = (Button) findViewById(R.id.btnSensores);
        btnSincro = (Button) findViewById(R.id.btnSincro);
        txtPlanificacionAct = (TextView) findViewById(R.id.txtPlanificacionAct);

        //Seteo listeners a los botones
        btnPlanificar.setOnClickListener(botonesListeners);
        btnSensores.setOnClickListener(botonesListeners);
        btnSincro.setOnClickListener(botonesListeners);

        setPlanificacionActual();

        //SI QUISIERAMOS OBTENER DATOS DE OTRA ACTIVITY

        //se crea un objeto Bundle para poder recibir los parametros enviados por la activity Inicio
        //al momeento de ejecutar stratActivity
        //Bundle extras=intent.getExtras();
        //Intent intent=getIntent();

        //String texto= extras.getString("textoOrigen");
        //txtDestino.setText(texto);

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
                case R.id.btnPlanificar:

                    //se genera un Intent para poder lanzar la activity principal
                    intent=new Intent(MainActivity.this, PlanificationActivity.class);

                    //se inicia la activity principal
                    startActivity(intent);
                    break;
                case R.id.btnSensores:

                    //se genera un Intent para poder lanzar la activity principal
                    intent=new Intent(MainActivity.this, SensorsActivity.class);

                    //se inicia la activity principal
                    startActivity(intent);
                    break;

                case R.id.btnSincro:

                    //se genera un Intent para poder lanzar la activity principal
                    intent=new Intent(MainActivity.this, SincroActivity.class);

                    //se inicia la activity principal
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getApplicationContext(),"Error en Listener de botones", Toast.LENGTH_LONG).show();
            }


        }
    };

    //Cargar la planificación del pastillero
    private void setPlanificacionActual()
    {
        String planif = leerPlanificacion();
        if(!planif.equals(""))
        {
            txtPlanificacionAct.setText(planif);
        }
        else
        {
            txtPlanificacionAct.setText("No existe planificación");
        }

    }

    private String leerPlanificacion()
    {

        String planificacion = "";

        try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new
                    File(getFilesDir()+ File.separator+"data.txt")));
                String receiveString;
                ArrayList<String> lineas = new ArrayList<String> ();
                //StringBuilder stringBuilder = new StringBuilder("");

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    lineas.add(receiveString);
                    //stringBuilder.append(receiveString+"\n");
                }

                bufferedReader.close();
                planificacion = parsearTxtPlanificacion(lineas);
                //planificacion = stringBuilder.toString();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return "";
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            return "";
        }

        return planificacion;
    }

    private String parsearTxtPlanificacion(ArrayList<String> lineas)
    {
        String result = "";
        for (String linea: lineas) {
            String[] parsedLine = linea.split(";");
            result += "Tolva " + parsedLine[0] + " - " + getDay(parsedLine[1]) + " - " + parsedLine[2] + ":" + parsedLine[3]+"\n";
        }
        return result;
    }

    private String getDay(String num)
    {
        switch (num)
        {
            case "1":
                return "Lunes";
            case "2":
                return "Martes";
            case "3":
                return "Miercoles";
            case "4":
                return "Jueves";
            case "5":
                return "Viernes";
            case "6":
                return "Sabado";
            case "7":
                return "Domingo";
        }
        return "";
    }
}
