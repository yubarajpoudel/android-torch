package com.yuvi.torch;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener{
    
    private static Camera camera;
    private static Parameters parameters;
    private ToggleButton onOff; 
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        setContentView(R.layout.activity_main);                
        
        onOff = (ToggleButton) this.findViewById(R.id.light_btn);
        onOff.setOnClickListener(this);
        onOff.setChecked(false); 
        
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        /**
         * Release Camera 
         */     
        if (camera != null) {            
            camera.release();
            camera = null;
        }
        onOff.setChecked(false); 
    }
    
     @Override
        protected void onResume() {
            super.onResume();
            
            if(parameters != null && parameters.getFlashMode()==Parameters.FLASH_MODE_TORCH){
                if (camera == null)
                    camera = Camera.open();
                camera.setParameters(parameters);
                onOff.setChecked(true);
            }         
        }
     
    private void setFlashOn(){
        if (camera == null)
            camera = Camera.open();         
        parameters = camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);       
        camera.setParameters(parameters);
    }
    
    private void setFlashOff(){     
        parameters = camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_OFF);     
        camera.setParameters(parameters);
    }
    
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.light_btn:
                if(onOff.isChecked()){
                    setFlashOn();
                }else{                  
                    setFlashOff();
                }
            break;
        }
        
    }
}