package in.ac.accloud;

import androidx.appcompat.app.AppCompatActivity;

public class Upload extends AppCompatActivity {
    private String mName;
    private String filepath;

    public Upload(){

    }

    public Upload(String name,String imageUrl){
        if (name.trim().equals("")){
            name="img";
        }
        mName=name;
        filepath=imageUrl;
    }

    public String getName(){
        return mName;
    }
    public void setName(String name){
        mName=name;
    }
    public String getImageUrl(){
        return filepath;
    }
    public void setImageUrl(String imageUrl){
        filepath=imageUrl;
    }
}
