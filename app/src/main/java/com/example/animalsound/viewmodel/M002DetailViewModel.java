package com.example.animalsound.viewmodel;

import android.os.Environment;

import com.example.animalsound.App;
import com.example.animalsound.model.Animal;

import java.io.FileOutputStream;
import java.io.InputStream;

public class M002DetailViewModel extends BaseViewModel{
    public Object coppyPhotoToStorage(Animal animal){
        try {
            InputStream in = App.getInstance().getAssets().open(animal.getIdPhoto());
            byte[] buff = new byte[1024];

            String outPath = Environment.getDataDirectory().getPath()+"/data/"+App.getInstance().getPackageName();
            FileOutputStream out = new FileOutputStream(outPath+"/"+animal.getName()+".png");

            int len = in.read(buff);
            while (len>0){
                out.write(buff, 0, len);
                len = in.read(buff);
            }
            out.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
