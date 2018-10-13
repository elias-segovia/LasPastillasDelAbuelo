package unlam.com.lpda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

public class SincroActivity extends Activity {

    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincro);

        //Botones
        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(botonesListeners);


    }

    private View.OnClickListener botonesListeners = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent intent;
            switch (v.getId())
            {
                case R.id.btnVolver:

                    intent=new Intent(SincroActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getApplicationContext(),"Error en Listener de botones", Toast.LENGTH_LONG).show();
            }
        }
    };
}
