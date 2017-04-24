package tw.edu.ntut.csie.game;

import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * Created by Guan-Hong on 2015/4/6.
 */
public class Sun_plant implements GameObject{
    private MovingBitmap _sun;
    private int PositionX,PositionY;
    //  ***
    private int produceSpeed ;
    private int produceNumber ;
    private int times ;
    //  判斷現在是否產生中   :  1 ( Ing ) 、 0 ( 沒有產生 )
    private int producing;
    public Sun_plant( int PlantX , int PlantY ){
        PositionX = PlantX + 40;
        PositionY = PlantY - 10 ;
        _sun = new MovingBitmap(R.drawable.sun_2);
        _sun.setLocation( PositionX  , PositionY  );
        _sun.setVisible( false );
        //  一開始 9 秒產生一次
        produceSpeed = 9 * 10 ;
        produceNumber = 0 ;
        producing = 0 ;
        times = 0 ;
    }
    @Override
    public void release() {
        _sun.release();
        _sun = null;
    }
    @Override
    public void move() {

    }
    @Override
    public void show() {
        if( producing == 0 ) times ++ ;
        if( times >= produceSpeed ){
            producing = 1 ;
            _sun.setVisible( true );
        }
        _sun.setLocation( PositionX , PositionY );
        _sun.show();
    }
    public int TouchSun( int touchX , int touchY ){
        //  判斷是否碰到太陽
        if (touchX > _sun.getX() && touchX < _sun.getX() + _sun.getWidth() &&
                touchY > _sun.getY() && touchY < _sun.getY() + _sun.getHeight()
                && producing == 1 ){
            _sun.setVisible( false );
            if( produceNumber == 0 ) produceSpeed *= 2 ;
            produceNumber ++ ;
            times = 0 ;
            producing = 0 ;
            return 1 ;
        }
        return 0 ;
    }
    //  判斷是否產生太陽中
    public boolean IsProducing(){
        if( producing == 0 ) return false ;
        else if( producing == 1 ) return true ;
        return false ;
    }
    //  改變太陽生產狀態，EX : 植物死亡
    public void ChangeProducing( int state ){
        producing = state ;
    }
    public void SetProduceSpeed( int  speedSecond){
        produceSpeed = speedSecond * 10 ;
    }
}
