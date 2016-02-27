package android.com.visitingpatterns;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by srinu on 2/12/2016.
 */
public class HomeActivity extends Activity implements LocationListener {
    Button btn_start, btn_view, btn_graph, btn_exit;

    LocationManager locationManager;
    String provider;

    long endTime,durationTime;

    private View mChart;
    private String[] mMonth = new String[] {"A","B","C","D","E","F"};
    DataBaseHandler dbhandler;
    TextView tv_address;

    double latitude,longitude,accuracy,altitude;
    long time;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_view = (Button) findViewById(R.id.btn_view);
        btn_graph = (Button) findViewById(R.id.btn_graph);
        btn_exit = (Button) findViewById(R.id.btn_exit);

       // tv_address=(TextView)findViewById(R.id.tvAddress);

        dbhandler=new DataBaseHandler(this);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting LocationManager object
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                // Creating an empty criteria object
                Criteria criteria = new Criteria();

                // Getting the name of the provider that meets the criteria
                provider = locationManager.getBestProvider(criteria, false);

                if (provider != null && !provider.equals("")) {

                    // Get the location from the given provider
                    if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(provider);

                    locationManager.requestLocationUpdates(provider, 20000, 1, HomeActivity.this);

                    if(location!=null)
                        onLocationChanged(location);
                    else
                        Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
                }
            }

    });

        btn_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openChart();
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime=System.currentTimeMillis();
                durationTime=endTime-time;
                finish();
                System.exit(0);
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,GoogleMapsActivity.class));

            }
        });
    }

    private void openChart(){
        long startTime=System.currentTimeMillis();
        int[] x = { 10,20,30,40,50,60};
        int[] income = { 2000,2500,3000,2700,3200,3500};
        int[] expense={2200,2600,2800, 2900,3300,3600};
       // List<LocationInfo> income1 = dbhandler.getAddColumnDetails();
       // List<LocationInfo> expense1 = dbhandler.getTimeColumnDetails();
        LocationInfo locationInfo=new LocationInfo();

        XYSeries incomeSeries = new XYSeries("Income");
        XYSeries expenseSeries = new XYSeries("Expense");
        for(int i=0;i<x.length;i++){
            incomeSeries.add(i,income[i]);
            expenseSeries.add(i,expense[i]);
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(incomeSeries);
        dataset.addSeries(expenseSeries);

        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.BLUE);
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(2f);
        incomeRenderer.setDisplayChartValues(true);
        incomeRenderer.setDisplayChartValuesDistance(10);

        incomeRenderer.setPointStyle(PointStyle.CIRCLE);
        incomeRenderer.setStroke(BasicStroke.SOLID);

        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.RED);
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2f);
        expenseRenderer.setDisplayChartValues(true);
        expenseRenderer.setPointStyle(PointStyle.SQUARE);
        expenseRenderer.setStroke(BasicStroke.SOLID);

// Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Set of Annotated Places");
        multiRenderer.setXTitle("Self Report");
        multiRenderer.setYTitle("Servies");

/***
 * Customizing graphs
 */
//setting text size of the title
        multiRenderer.setChartTitleTextSize(28);
//setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
//setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);
//setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(false);
//setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(false, false);
//setting click false on graph
        multiRenderer.setClickEnabled(false);
//setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, false);
//setting lines to display on y axis
        multiRenderer.setShowGridY(true);

        //setting lines to display on x axis
        multiRenderer.setShowGridX(true);
//setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
//setting displaying line on grid
        multiRenderer.setShowGrid(true);
//setting zoom to false
        multiRenderer.setZoomEnabled(false);
//setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
//setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
//setting to in scroll to false
        multiRenderer.setInScroll(false);
//setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
//setting x axis label align
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
//setting y axis label to align
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
//setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
//setting no of values to display in y axis
        multiRenderer.setYLabels(10);
// setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
// if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(4000);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMax(11);
//setting bar size or space between two bars
//multiRenderer.setBarSpacing(0.5);
//Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
//Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setScale(2f);
//setting x axis point size
        multiRenderer.setPointSize(4f);
//setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 30, 30, 30});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }

// Adding incomeRenderer and expenseRenderer to multipleRenderer
// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
// should be same
        multiRenderer.addSeriesRenderer(incomeRenderer);
        multiRenderer.addSeriesRenderer(expenseRenderer);

//drawing bar chart
        mChart = ChartFactory.getLineChartView(HomeActivity.this, dataset, multiRenderer);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);
        startActivity(intent);


    }


    @Override
    public void onLocationChanged(Location location) {

        latitude=location.getLatitude();
        longitude=location.getLongitude();
        altitude=location.getAltitude();
        accuracy=location.getAccuracy();
        time=location.getTime();
        Toast.makeText(getBaseContext(), "Longitude:"+latitude+"\n Latitude:"+longitude+"\n Altitude:"+altitude+"\n Accuracy:"+accuracy+"\n Time:"+time, Toast.LENGTH_SHORT).show();


        if(location!=null)
        {
        Locationaddress locationAddress = new Locationaddress();
        locationAddress.getAddressFromLocation(location.getLatitude(), location.getLongitude(),
                getApplicationContext(), new GeocoderHandler());

        } else {
        showSettingsAlert();
    }


       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

}

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                HomeActivity.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        HomeActivity.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    address=locationAddress;
                    break;
                default:
                    locationAddress = null;
            }
           // tv_address.setText(locationAddress);
            Toast.makeText(getApplicationContext(),"location is: "+locationAddress,Toast.LENGTH_LONG).show();
            Log.d("Location is:", locationAddress);
           //dbhandler.deleteAllLocationInfo();
            dbhandler.addLocationInfo(new LocationInfo(latitude,longitude,altitude,durationTime,address));
            Toast.makeText(getApplicationContext(),"Added sucessfully",Toast.LENGTH_LONG).show();


          /* List<LocationInfo> details = dbhandler.getAllLocationDetails();

            for (LocationInfo cn : details) {
                String Loc = "Latitude: "+cn.getLatitude()+" ,Longitude: " + cn.getLongitude() + " ,Altitude: " + cn.getAltitude()+" ,Accuracy"+cn.getAccuracy()+" ,Time "+cn.getTime()+" ,Address"+cn.getAddress();
                // Writing Contacts to log
                Log.d("Loc********: ", Loc);
            }*/


        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
