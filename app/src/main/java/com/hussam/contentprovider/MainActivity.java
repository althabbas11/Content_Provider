package com.hussam.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken()
                , 0);
        return true;
    }

    public void onClickAddDetails(View view) {
// تانايبلا ةدعاق يف ميقلا ةفاضلإ فص
        ContentValues values = new ContentValues();
// user لودج نم صن بلج
        values.put(MyContentProvider.name, ((EditText)
                findViewById(R.id.textName)).getText().toString());
// ىوتحم URI للاخ نم تانايبلا ةدعاق يف جاردلإا
        getContentResolver().insert(MyContentProvider.CONTENT_URI,
                values);
// ةزجوم ةلاسر ضرع
        Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_LONG).show();
    }

    public void onClickShowDetails(View view) {
// يصنلا لقحلا اذه يف ةلماك لودجلا ليصافت جاردإ
        TextView resultView = findViewById(R.id.res);
// ىوتحملا URI ل رشؤم نئاك ءاشنإ
        Cursor cursor =
                getContentResolver().query(Uri.parse("content://com.demo.user.provider/users"), null, null, null, null);
// لماكلاب لودجلا ةعابطل رشؤملا راركت
        if (cursor.moveToFirst()) {
            StringBuilder strBuild = new StringBuilder();
            while (!cursor.isAfterLast()) {
                strBuild.append("\n" + cursor.getString(cursor.getColumnIndexOrThrow("id")) + "-" +
                        cursor.getString(cursor.getColumnIndexOrThrow("name")));
                cursor.moveToNext();
            }
            resultView.setText(strBuild);
        } else {
            resultView.setText("No Records Found");
        }
    }
}