package com.ares.pulkit.studyease;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main2Activity extends Activity implements View.OnClickListener {
    private static final int CAN_REQUEST =1313 ;
     private  List<String> listOfImagesPath;
     GridView gridview;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Context context;
    public static  String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GridViewDemo/";
    ImageAdapter image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        String j =(String) b.get("l_name");
        gridview = (GridView) findViewById(R.id.gridView);
       Button  camera=(Button) findViewById(R.id.add_image);
        camera.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAN_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAN_REQUEST && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thePic = extras.getParcelable("data");
            String imgcurTime = dateFormat.format(new Date());
            File imageDirectory = new File(GridViewDemo_ImagePath);
            imageDirectory.mkdirs();

            String _path = GridViewDemo_ImagePath + imgcurTime+".jpg";
            try
            {
                FileOutputStream out = new FileOutputStream(_path);
                thePic.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.close();
            }
            catch (FileNotFoundException e) {
                e.getMessage();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
