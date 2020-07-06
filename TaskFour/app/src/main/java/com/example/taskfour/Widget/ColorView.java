package com.example.taskfour.Widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.taskfour.FloodFill;
import com.example.taskfour.PaintingOneActivity;
import com.example.taskfour.PaintingTwoActivity;
import com.example.taskfour.R;

import java.io.IOException;
import java.util.ArrayList;

public class ColorView extends View {

    Bitmap bitmap,defaultBitmap = null ;
    private ArrayList<Bitmap> listAction = new ArrayList<> (  );
    private Canvas mCanvas;
    private Context context;

    public ColorView( Context context , @Nullable AttributeSet attrs ) {
        super ( context , attrs );
        this.context = context;
    }


    @Override
    protected void onSizeChanged( int w , int h , int oldw , int oldh ) {
        super.onSizeChanged ( w , h , oldw , oldh );
        if (PaintingTwoActivity.fileId != null){
            try {
                Bitmap bitmaptemp = MediaStore.Images.Media.getBitmap ( context.getContentResolver (), PaintingTwoActivity.fileId );
                bitmap = bitmaptemp.copy(Bitmap.Config.ARGB_8888, true);

            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
        else {
            if (bitmap == null){
                Bitmap srcBitmap = BitmapFactory.decodeResource ( getResources (), PaintingTwoActivity.imageid );
                bitmap = Bitmap.createScaledBitmap ( srcBitmap,w,h,false );

                for (int i =0;i<bitmap.getWidth ();i++){
                    for (int j = 0;j<bitmap.getHeight ();j++){
                        int alpha = 255 - brightness(bitmap.getPixel ( i,j ));
                        if (alpha<200){
                            bitmap.setPixel ( i,j,Color.WHITE );
                        }
                        else {
                            bitmap.setPixel ( i,j,Color.BLACK );
                        }
                    }
                }

                if (defaultBitmap == null){
                    defaultBitmap = Bitmap.createBitmap ( bitmap );
                }
            }
        }
    }

    private int brightness( int color ) {
        return ( color >> 16 ) & 0xff;
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw ( canvas );
        canvas.drawBitmap ( bitmap,0,0,null );
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {

        paint((int)event.getX (),(int)event.getY ());
        if (PaintingTwoActivity.mediaPlayer3 != null){
            PaintingTwoActivity.mediaPlayer3.start ();
        }
        return true;
    }

    private void paint( int x , int y ) {
        if (x<0 || y<0 || x >= bitmap.getWidth () || y >= bitmap.getHeight ()){
            return;
        }
        int targetColor = bitmap.getPixel ( x,y );
        if (targetColor != Color.BLACK){
            FloodFill.floodFill ( bitmap,new Point ( x,y ),targetColor, PaintingTwoActivity.selectedColor );
            addLastAction ( Bitmap.createBitmap ( getBitmap () ) );
            invalidate ();
        }

    }
    public Bitmap getBitmap(){
        this.setDrawingCacheEnabled ( true );
        this.buildDrawingCache ();
        Bitmap bitmap = Bitmap.createBitmap ( this.getDrawingCache () );
        this.setDrawingCacheEnabled ( false );
        return bitmap;
    }
    public void addLastAction(Bitmap bitmap){
        listAction.add ( bitmap );
    }

    public void returnLastAction(){
        if (listAction.size () >0){
            listAction.remove ( listAction.size () - 1 );
            if (listAction.size () >0){
                bitmap = listAction.get ( listAction.size () - 1 );
            }
            else {
                bitmap = Bitmap.createBitmap ( defaultBitmap );
            }

            invalidate ();
        }
    }

}
