package com.example.kuba.databasetest.objects;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.kuba.databasetest.FragmentDatabase;
import com.example.kuba.databasetest.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemDetails extends Fragment
{
    private Item product;
    private Button buttonBackToDatabase;
    PieChart pieChart;


    public static ItemDetails newInstance(Item product)
    {
        ItemDetails fragment  = new ItemDetails();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Example Item", product);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_item_database_details, container, false);

        product = (Item) getArguments().getSerializable("Example Item");
        pieChart = (PieChart) view.findViewById(R.id.layout_item_database_details_pchart);
        setPieChart();
        addDataSet();

        buttonBackToDatabase = (Button) view.findViewById(R.id.btn_back_to_database);
        buttonBackToDatabase.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.ma_fragment_container, new FragmentDatabase());
                ft.commit();
            }
        });

        //TODO: onClick-> change % format to decimal format

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });


        return view;
    }

    public void setPieChart()
    {
        Description pieChartTitle = new Description();
        pieChartTitle.setText(product.getText1());
        pieChartTitle.setTextSize(14);
        pieChart.setDescription(pieChartTitle);
        pieChart.setCenterText(Double.toString(product.getCalories()) + " kcal");
        pieChart.setCenterTextSize(25);
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setHoleRadius(60f);
        pieChart.setTransparentCircleAlpha(0);
        //pieChart.enableScroll();
        //More options in documentation (github PhilJay)
    }

    public void addDataSet()
    {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        yEntrys.add(new PieEntry((float)product.getCarbs()));
        yEntrys.add(new PieEntry((float)product.getFat()));
        yEntrys.add(new PieEntry((float)product.getProteins()));


        xEntrys.add("Carbs");
        xEntrys.add("Fat");
        xEntrys.add("Proteins");

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Carbs/Fat/Proteins");
        pieDataSet.setSliceSpace(1);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueFormatter(new PercentFormatter());

        //add colors to data set
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add legend to chart
        /*
        ArrayList<LegendEntry> legendLabels = new ArrayList<>();
        legendLabels.add(new LegendEntry("Carbs", Legend.LegendForm.CIRCLE, 12, 5, null, Color.BLACK));
        legendLabels.add(new LegendEntry("Fat", Legend.LegendForm.DEFAULT, 10, 5, null, Color.BLACK));
        legendLabels.add(new LegendEntry("Proteins", Legend.LegendForm.DEFAULT, 10, 5, null, Color.BLACK));
        */
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        /*legend.setEntries(legendLabels);*/
        legend.setTextSize(12);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
