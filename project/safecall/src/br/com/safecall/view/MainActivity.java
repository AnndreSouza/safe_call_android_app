package br.com.safecall.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import br.com.safecall.control.Alert;
import br.com.safecall.control.Controller;
import br.com.safecall.control.Toolkit;
import br.com.safecall.dao.DatabaseHandler;
import br.com.safecall.dao.DatabaseManager;
import br.com.safecall.control.listener.SafeClickListener;
import br.com.safecall.dao.DAO;

public class MainActivity extends Activity {

    private Report report;
    private Config config;
    private Home home;
    private Controller controller;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        controller = new Controller(this);
        report = new Report(this, controller);
        home = new Home(this, controller);
        config = new Config(this, controller);

        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String result = "";
                String tel = intent.getExtras().getString("tel");
                String message = intent.getExtras().getString("message");
                boolean sent = false;
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        result = "Enviado com sucesso";
                        sent = true;
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        result = "Falha de Transmissão";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        result = "Radio desligado";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        result = "Sem PDU definido";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        result = "Sem Serviço";
                        break;
                }
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                controller.insertIntoHistory(message, result, tel, sent);
                intent.putExtra("response", result);
                controller.updateMainActiviryUI();
            }
        }, new IntentFilter(Toolkit.ACTION_SMS_SENT));
        registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                intent.putExtra("response", getResultCode());
            }

        }, new IntentFilter(Toolkit.ACTION_SMS_DELIVERED));

        report.updateReport();
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Habilite sua localização via GPS", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        TabHost tabs = (TabHost) this.findViewById(android.R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tabHome");
        spec.setContent(R.id.tabHome);
        spec.setIndicator(null, getResources().getDrawable(R.drawable.home));
        tabs.addTab(spec);
        TabHost.TabSpec spec1 = tabs.newTabSpec("tabReport");
        spec1.setContent(R.id.tabReports);
        spec1.setIndicator(null, getResources().getDrawable(R.drawable.report));
        //spec1.setContent(new Intent(MainActivity.this, Report.class));
        tabs.addTab(spec1);
        TabHost.TabSpec spec2 = tabs.newTabSpec("tabConfig");
        spec2.setContent(R.id.tabConfig);
        spec2.setIndicator(null, getResources().getDrawable(R.drawable.config));
        tabs.addTab(spec2);
        ImageView b = (ImageView) this.findViewById(R.id.safe);
        b.setOnClickListener(new SafeClickListener(home));

        String configArray[] = {"Cronômetro", "Mensagem", "Telefones"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, configArray);
        ListView lv = ((ListView) findViewById(R.id.configList));
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    config.getCronDialog().show();
                } else if (position == 1) {
                    config.getMessageDialog().show();
                } else if (position == 2) {
                    config.getTelDialog().show();
                }
            }
        });
        if (controller.getConfig().getMessage().isEmpty() || controller.getConfig().getSeconds() == 0l || controller.getConfig().getTelephones().size() == 0) {
            new Alert().getAlert(this, "Informativo", "Antes de utilizar, faça sua configuração!").show();
            tabs.setCurrentTab(2);
        }
    }

    public void updateUI() {
        report.updateReport();
    }
}
