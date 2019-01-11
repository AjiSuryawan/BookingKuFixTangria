package com.example.guru.bookingku.Activity.Detail;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.example.guru.bookingku.Model.HistoryBooking;
import com.example.guru.bookingku.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.WHITE;

public class DetailHistory extends AppCompatActivity {

    @BindView(R.id.tvProductName)
    TextView tvProductName;
    @BindView(R.id.tvProductDesc)
    TextView tvProductDesc;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    TextView tvkodebooking;
    private ImageView imageViewBitmap;
    private String productName;
    private String productDesc;
    private String status;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        imageViewBitmap=(ImageView)findViewById(R.id.imageViewBitmap);
        tvkodebooking=(TextView)findViewById(R.id.tvkodebooking);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            HistoryBooking detailBooking = extras.getParcelable("history");
            productName = detailBooking.getOrder();
            productDesc = detailBooking.getOrderDesc();
            status = detailBooking.getStatus();

            tvProductName.setText(productName);
            tvProductDesc.setText(productDesc);
            tvStatus.setText(status.toUpperCase());

            if(status.equals("pending")){
                tvStatus.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
            }
            if(status.equals("cancel")){
                tvStatus.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            }
            if (status.equals("diterima")) {
                tvStatus.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                code = detailBooking.getCode();
                tvkodebooking.setText(code);
                int width =300;
                int height = 300;
                int smallestDimension = width < height ? width : height;
                String qrCodeData = code;
                //setting parameters for qr code
                String charset = "UTF-8";
                Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
                CreateQRCode(qrCodeData, charset, hintMap, smallestDimension, smallestDimension);
            }
        }
    }

    public Bitmap mergeBitmaps(Bitmap overlay, Bitmap bitmap) {

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        Bitmap combined = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bitmap, new Matrix(), null);

        int centreX = (canvasWidth  - overlay.getWidth()) /2;
        int centreY = (canvasHeight - overlay.getHeight()) /2 ;
        canvas.drawBitmap(overlay, centreX, centreY, null);

        return combined;
    }

    public  void CreateQRCode(String qrCodeData, String charset, Map hintMap, int qrCodeheight, int qrCodewidth){


        try {
            //generating qr code in bitmatrix type
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
            //converting bitmatrix to bitmap

            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            // All are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    //pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
                    pixels[offset + x] = matrix.get(x, y) ?
                            ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null) :WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            //setting bitmap to image view

            //Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.icon);

            //imageViewBitmap.setImageBitmap(mergeBitmaps(overlay,bitmap));
            imageViewBitmap.setImageBitmap(bitmap);

        }catch (Exception er){
            Log.e("QrGenerate",er.getMessage());
        }
    }


}
