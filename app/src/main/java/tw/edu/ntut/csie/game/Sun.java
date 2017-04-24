package tw.edu.ntut.csie.game;

import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * Created by Guan-Hong on 2015/4/5.
 */
public class Sun implements GameObject{
    private MovingBitmap _sun;
    private int PositionX,PositionY;
    //  ***
    private int ContinueTime , PruduceTime ;
    //  判斷現在是否產生中   :  1 ( Ing ) 、 0 ( 沒有產生 )
    private int producing;
    public Sun(){
        _sun = new MovingBitmap(R.drawable.sun_2);
        _sun.setVisible( false );
        producing = 0 ;
        ContinueTime = 0 ;
        PruduceTime = 0 ;
    }
    @Override
    public void release() {
        _sun.release();
        _sun = null;
    }
    //  初始化 位置
    void initial(){
        //  low到high亂數（含high）
        //  (int) (Math.random() * (high - low + 1) + low)
        //  一開始，隨機產生 X 座標
        producing = 0 ;
        PruduceTime ++ ;
        //  6秒生產一次太陽
        if( PruduceTime >= 6 * 10 ){
            producing = 1 ;
            PruduceTime = 0 ;
            ContinueTime = 0 ;
            PositionX = ( int ) ( Math.random() * ( 480 - 240 ) + 240 ) ;
            PositionY = 15;
            _sun.setLocation( PositionX , PositionY );
            _sun.setVisible(true);
        }
    }
    @Override
    public void move() {
        if( producing == 0 ){
            initial();
        }
        //  當太陽座標 <=306 ，就一直移動
        else if( producing == 1 && PositionY <= 306 ){
            PositionY += 2 ;
            _sun.setLocation( PositionX , PositionY );
        }
        //  判斷 如果太陽出現多久 會自動消失
        if( _sun.getY() > 306 ) {
            ContinueTime ++ ;
            if( ContinueTime >= 5 * 10 ){
                initial() ;
            }
        }
    }
    @Override
    public void show() {
        _sun.setLocation( PositionX , PositionY );
        _sun.show();
    }
    public int TouchSun( int touchX , int touchY ){
        //  判斷是否碰到太陽 ( 太陽必須正在製造中 )
        if (touchX > _sun.getX() && touchX < _sun.getX() + _sun.getWidth() &&
                touchY > _sun.getY() && touchY < _sun.getY() + _sun.getHeight() &&
                producing == 1 ){
            _sun.setVisible( false );
            initial();
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
}
