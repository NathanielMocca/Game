package tw.edu.ntut.csie.game;

import android.app.ActionBar;
import android.text.style.TtsSpan;
import android.widget.TableLayout;

import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * Created by Guan-Hong on 2015/4/3.
 */
public class ListPlant implements GameObject{
    //  預設屬性 (  暫時  )
    /*
               SunFlower    :   1
               BulletFlower    :   2
               IceFlower    :   3
               DefensePotato    :   4
               Bomb :   5
               DoubleBulletFlower   :   6
               when (Level State == 2)    Lotus Leaves :   7
               when (Level State == 3)    SunMushroom:  7
     */
    //  植物清單
    private MovingBitmap [] Table ;
    //  冷卻時間
    private int [] ColdTime ;
    //  關卡狀態    :   1 ( 一般地圖 )  、   2 ( 水地圖 )
    private int LevelState ;
    //  紀錄現在觸碰的植物編號
    private int NowTouchButtonNumber = 0 ;

    public ListPlant( int Level ){
        LevelState = Level ;
        Table = new MovingBitmap [8] ;
        //  太陽花
        Table[0] = new MovingBitmap( R.drawable.sunflower_button ) ;
        Table[0].setLocation( 5 , 0 ) ;
        // 單發子彈植物
        Table[1] = new MovingBitmap( R.drawable.one_shoot_button ) ;
        Table[1].setLocation( 5 , 47 ) ;
        //  冰凍子彈植物
        Table[2] = new MovingBitmap( R.drawable.snow_shoot_button ) ;
        Table[2].setLocation( 5 , 94 ) ;
        // 防禦馬鈴薯
        Table[3] = new MovingBitmap( R.drawable.potato_small_button ) ;
        Table[3].setLocation( 5 , 141 ) ;
        //  地雷
        Table[4] = new MovingBitmap( R.drawable.bomb_potato_button ) ;
        Table[4].setLocation( 5 , 188 ) ;
        //  雙發子彈植物
        Table[5] = new MovingBitmap( R.drawable.double_shoot_button ) ;
        Table[5].setLocation( 5 , 235 ) ;
        //  荷葉植物
        if( LevelState == 2 ){
            Table[6] = new MovingBitmap( R.drawable.lotus_leaf_button ) ;
            Table[6].setLocation( 5 , 282 ) ;
        }
        if( LevelState == 3 ){
            Table[6] = new MovingBitmap( R.drawable.sunflower_button ) ; //
            Table[6].setLocation( 5 , 282 ) ;
            Table[7] = new MovingBitmap( R.drawable.one_shoot_button );
            Table[7].setLocation( 5 , 329 );
        }
        // 冷卻時間，初始化
        ColdTime = new int [8] ;
        InitialColdTime( 0 );
    }
    //  初始化冷卻時間
    public void InitialColdTime( int feature ){
        int second = 10 ;
        if( feature == 0 ){
            int number_i;
            for( number_i = 0 ; number_i< 8 ; number_i++ ){
                ColdTime[number_i] = 0 ;
            }
        }
        else if( feature == 1 ) ColdTime[0] = 7 * second ;
        else if( feature == 2 ) ColdTime[1] = 8 * second ;
        else if( feature == 3 ) ColdTime[2] = 8 * second ;
        else if( feature == 4 ) ColdTime[3] = 25 * second ;
        else if( feature == 5 ) ColdTime[4] = 25 * second ;
        else if( feature == 6 ) ColdTime[5] = 8 * second ;
        else if( LevelState == 2 && feature == 7 ) ColdTime[6] = 8 * second ;
        else if( LevelState == 3 ){
            if( feature == 7 ) ColdTime[6] = 10 * second ;
            else if( feature == 8 ) ColdTime[7] = 12 * second ;
        }
    }
    public void OriginalPhoto( int PlantType , MovingBitmap [] Table ){
        if( PlantType == 1 ) Table[0].loadBitmap( R.drawable.sunflower_button );
        else if( PlantType == 2 ) Table[1].loadBitmap( R.drawable.one_shoot_button );
        else if( PlantType == 3 ) Table[2].loadBitmap( R.drawable.snow_shoot_button );
        else if( PlantType == 4 ) Table[3].loadBitmap( R.drawable.potato_small_button );
        else if( PlantType == 5 ) Table[4].loadBitmap( R.drawable.bomb_potato_button );
        else if( PlantType == 6 ) Table[5].loadBitmap( R.drawable.double_shoot_button );
        else if( PlantType == 7 && LevelState == 2 ) Table[6].loadBitmap( R.drawable.lotus_leaf_button );
        else if( LevelState == 3 ){
            if( PlantType == 7 ) Table[6].loadBitmap( R.drawable.sun_mushroom_button );
            else if( PlantType == 8 ) Table[7].loadBitmap( R.drawable.shoot_mushroom_button );
        }
    }
    public void SelectButtonPhoto( int PlantType , MovingBitmap [] Table ){
        if( PlantType == 1 ) Table[0].loadBitmap( R.drawable.choose_sunflower_button );
        else if( PlantType == 2 ) Table[1].loadBitmap( R.drawable.choose_one_shoot_button );
        else if( PlantType == 3 ) Table[2].loadBitmap( R.drawable.choose_snow_shoot_button );
        else if( PlantType == 4 ) Table[3].loadBitmap( R.drawable.choose_potato_small_button );
        else if( PlantType == 5 ) Table[4].loadBitmap( R.drawable.choose_bomb_potato_button );
        else if( PlantType == 6 ) Table[5].loadBitmap( R.drawable.choose_double_shoot_button );
        else if( PlantType == 7 && LevelState == 2 ) Table[6].loadBitmap( R.drawable.choose_lotus_leaf_button );
        else if( LevelState == 3 ){
            if( PlantType == 7 ) Table[6].loadBitmap( R.drawable.choose_sun_mushroom_button );
            else if( PlantType == 8 ) Table[7].loadBitmap( R.drawable.choose_shoot_mushroom_button);
        }
    }
    public void SelectCdPhoto( int PlantType , MovingBitmap [] Table ){
        if( PlantType == 1 ) Table[0].loadBitmap( R.drawable.sunflower_button_cd );
        else if( PlantType == 2 ) Table[1].loadBitmap( R.drawable.one_shoot_button_cd );
        else if( PlantType == 3 ) Table[2].loadBitmap( R.drawable.snow_shoot_button_cd );
        else if( PlantType == 4 ) Table[3].loadBitmap( R.drawable.potato_small_button_cd);
        else if( PlantType == 5 ) Table[4].loadBitmap( R.drawable.bomb_potato_button_cd );
        else if( PlantType == 6 ) Table[5].loadBitmap( R.drawable.double_shoot_button_cd );
        else if( PlantType == 7 && LevelState == 2 ) Table[6].loadBitmap( R.drawable.lotus_leaf_button_cd );
        else if( LevelState == 3 ){
            if( PlantType == 7 ) Table[6].loadBitmap( R.drawable.sun_mushroom_button_cd );
            else if( PlantType == 8 ) Table[7].loadBitmap( R.drawable.shoot_mushroom_button_cd );
        }
    }
    public void release(){
        int Begin ;
        int end = 6 ;
        if( LevelState == 2 ) end++ ;
        else if( LevelState == 3 ) end += 2 ;
        for( Begin = 0 ; Begin < end ; Begin++ ){
            Table[Begin].release();
            Table[Begin] = null ;
        }
    }
    public void move(){

    }
    public void show(){
        int Begin ;
        int end = 6 ;
        if( LevelState == 2 ) end++ ;
        else if( LevelState == 3 ) end += 2 ;
        for( Begin = 0 ; Begin < end ; Begin ++ ){
            //  當處於冷卻時間  ，  更換圖片
            //  傳入的是植物編號    EX: 1 ( 太陽花 )   、   2 ( 子彈植物 )  ...
            ColdTimeChangeImage( Begin + 1 );

            Table[Begin].show();
        }
        //  執行冷卻時間
        ExecuteColdTime();
        //  冷卻時間結束，更換原始圖片
        initial();
    }
    void ColdTimeChangeImage( int PlantType ){
        if( ColdTime[ PlantType -1 ] > 0 ) SelectCdPhoto( PlantType , Table );
    }
    //  執行冷卻時間
    void ExecuteColdTime(){
        int number_i;
        for( number_i = 0 ; number_i< 8 ; number_i++ ) if( ColdTime[number_i] > 0 ) ColdTime[number_i]-- ;
    }
    //  初始化植物清單的圖片
    void initial(){
        int Begin ;
        int end = 6 ;
        if( LevelState == 2 ) end++ ;
        else if( LevelState == 3 ) end += 2 ;
        for( Begin = 0 ; Begin < end ; Begin ++ ){
            //  當冷卻時間 <=  0  ，  才需要初始化圖片
            //  避免在冷卻時間  ，  更換了照片
            //  也避免觸碰植物按鈕，更換了圖片
            //  傳入的是植物編號    EX: 1 ( 太陽花 )   、   2 ( 子彈植物 )  ...
            if( ColdTime[ Begin ] <= 0 && NowTouchButtonNumber != Begin + 1 ) OriginalPhoto( Begin + 1 , Table );
        }
    }
    //  判斷手是否碰觸到植物清單，並回傳碰觸的值
    public int TouchPlant( int TouchX , int TouchY ){
        int Begin ;
        int end = 6 ;
        if( LevelState == 2 ) end++ ;
        else if( LevelState == 3 ) end += 2 ;
        for( Begin = 0 ; Begin < end ; Begin ++ ){
            if( TouchX > Table[Begin].getX() && TouchX < Table[Begin].getX() + Table[Begin].getWidth()
                    && TouchY > Table[Begin].getY() && TouchY < Table[Begin].getY() + Table[Begin].getHeight() )
            {
                //  如果小於冷卻時間，即可種植植物
                if( ColdTime[Begin] <= 0 ){
                    // 其他圖片，維持原始狀態
                    initial();
                    // 手觸碰的植物，更改圖片
                    SelectButtonPhoto( Begin + 1 , Table );
                    NowTouchButtonNumber = Begin + 1 ;
                    return ( Begin + 1 ) ;
                }
                //  大於冷卻時間，回傳 -1
                else{
                    NowTouchButtonNumber = 0 ;
                    initial();
                    return -1 ;
                }
            }
        }
        NowTouchButtonNumber = 0 ;
        initial();
        //  無觸碰到植物按鈕，回傳 - 1
        return -1 ;
    }
}
