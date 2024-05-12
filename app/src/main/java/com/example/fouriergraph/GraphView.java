package com.example.fouriergraph;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class GraphView extends View {

    private ArrayList<double[]> graphData;
    private Bitmap graphBitmap;
    private Paint paint;

    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
    }

    public void setGraphData(ArrayList<double[]> data) {
        this.graphData = data;
        invalidate();
    }

    public void setGraphBitmap(Bitmap bitmap) {
        this.graphBitmap = bitmap;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (graphBitmap != null) {
            canvas.drawBitmap(graphBitmap, 0, 0, null);
        } else if (graphData != null) {
            for (int i = 0; i < graphData.size() - 1; i++) {
                double[] startPoint = graphData.get(i);
                double[] endPoint = graphData.get(i + 1);
                canvas.drawLine((float) startPoint[0], (float) startPoint[1], (float) endPoint[0], (float) endPoint[1], paint);
            }
        }
    }
}