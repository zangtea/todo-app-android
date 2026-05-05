package com.example.todo_app_android;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_app_android.database.DBHelper;

import java.text.SimpleDateFormat;
import java.util.*;

public class AddExpenseActivity extends AppCompatActivity {

    Spinner spnCategory;
    EditText edtAmount, edtDate, edtNote;
    Button btnSave;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        dbHelper = new DBHelper(this);

        spnCategory = findViewById(R.id.spnCategory);
        edtAmount = findViewById(R.id.edtAmount);
        edtDate = findViewById(R.id.edtDate);
        edtNote = findViewById(R.id.edtNote);

        edtAmount.requestFocus();
        edtAmount.setFocusable(true);
        edtAmount.setFocusableInTouchMode(true);

        // Danh mục
        String[] categories = {"Ăn uống", "Học tập", "Di chuyển", "Mua sắm", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);

        // Ngày mặc định
        edtDate.setText(getTodayDate());

        edtDate.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> saveExpense());
    }

    private String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            String date = String.format(Locale.getDefault(),
                    "%04d-%02d-%02d", year, month + 1, day);
            edtDate.setText(date);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveExpense() {
        String amountStr = edtAmount.getText().toString();

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Nhập số tiền", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        String category = spnCategory.getSelectedItem().toString();
        String date = edtDate.getText().toString();
        String note = edtNote.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_AMOUNT, amount);
        values.put(DBHelper.COL_CATEGORY, category);
        values.put(DBHelper.COL_DATE, date);
        values.put(DBHelper.COL_NOTE, note);

        long result = db.insert(DBHelper.TABLE_EXPENSE, null, values);
        db.close();

        if (result != -1) {
            Toast.makeText(this, "Đã lưu!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lỗi!", Toast.LENGTH_SHORT).show();
        }
    }
}