package com.example.gen_bill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText editTextName, editTextNumber, qty1, qty2;
    Spinner Spinner1, Spinner2;
    Button buttonSavePrint, buttonOldPrint;

    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    String[] itemList;
    int[] itemPrice;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objectAssignment();
        callOnClickListener();


    }

    private void callOnClickListener() {
        buttonSavePrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String number = editTextNumber.getText().toString();
                String item1 = Spinner1.getSelectedItem().toString();
                int itemQty1 = Integer.parseInt(String.valueOf(qty1.getText()));
                int itemAmount1 = itemQty1 * itemPrice[Spinner1.getSelectedItemPosition()];
                String item2 = Spinner2.getSelectedItem().toString();
                int itemQty2 = Integer.parseInt(String.valueOf(qty2.getText()));
                int itemAmount2 = itemQty2 * itemPrice[Spinner1.getSelectedItemPosition()];

                Date date = new Date();

                myHelper.insert(name, number, date.getTime(), item1, itemQty1, itemAmount1, item2, itemQty2, itemAmount2);

                Cursor cursor = sqLiteDatabase.rawQuery("select * from myTable", null);
                cursor.move(cursor.getCount());
                try {
                    new PrintPDF(cursor.getInt(0), name, number, date.getTime(), item1, itemQty1, itemAmount1, item2, itemQty2, itemAmount2).getPDF();
                    Toast.makeText(MainActivity.this, "PDF Created", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }


        });

        buttonOldPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, OldPrintActivity.class);
                startActivity(intent);
            }

        });


    }

    private void objectAssignment() {
        editTextName = findViewById(R.id.editTextName);
        editTextNumber = findViewById(R.id.editTextNumber);
        qty1 = findViewById(R.id.qty1);
        qty2 = findViewById(R.id.qty2);
        Spinner1 = findViewById(R.id.spinner1);
        Spinner2 = findViewById(R.id.spinner2);
        buttonOldPrint = findViewById(R.id.buttonprintold);
        buttonSavePrint = findViewById(R.id.buttonsaveprint);
        itemList = new String[]{"Chocolate", "biscuit", "Detergents", "Chips", "Bread"};
        itemPrice = new int[]{150, 581, 200, 699, 300};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemList);
        Spinner1.setAdapter(adapter);
        Spinner2.setAdapter(adapter);

        myHelper = new MyHelper(MainActivity.this);
        sqLiteDatabase = myHelper.getWritableDatabase();

    }


}