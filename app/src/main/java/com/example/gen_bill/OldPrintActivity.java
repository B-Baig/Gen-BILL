package com.example.gen_bill;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class OldPrintActivity extends AppCompatActivity {

    Button printbtn;
    EditText editText;
    DataTable dataTable;
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_print);

        printbtn = findViewById(R.id.oldPrintBtn);
        editText =findViewById(R.id.oldPrintEditText);
        dataTable = findViewById(R.id.data_table);
        myHelper = new MyHelper(this);
        sqLiteDatabase=myHelper.getWritableDatabase();

        DataTableHeader header = new DataTableHeader.Builder()
                .item("Invoice No",5)
                .item("Customer Name",5)
                .item("Date",5)
                .build();

        String[] columns={"InvoiceNo","customerName","date"};
        Cursor cursor=sqLiteDatabase.query("myTable",columns,null,null,null,null,null,null);
        ArrayList<DataTableRow> rows = new ArrayList<>();
        for(int i=0;i<cursor.getCount();i++)
        {
            cursor.moveToNext();
            DataTableRow row = new DataTableRow.Builder()
                    .value(String.valueOf(cursor.getInt(0)))
                    .value(cursor.getString(1))
                    .value(dateFormat.format(cursor.getLong(2)))
                    .build();
            rows.add(row);


        }


        dataTable.setHeader(header);
        dataTable.setRows(rows);
        dataTable.inflate(this);

        printSelectedInvoice();



    }

    private void printSelectedInvoice() {
        printbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int invoiceNoForPrint = Integer.parseInt(editText.getText().toString());
                Cursor cursor = sqLiteDatabase.rawQuery("select * from myTable where invoiceNo=" + invoiceNoForPrint, null);
                cursor.moveToNext();
                try {
                    new PrintPDF(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getInt(8),cursor.getInt(9)).getPDF();
                    Toast.makeText(OldPrintActivity.this,"PDF Created",Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


        });


    }


}
