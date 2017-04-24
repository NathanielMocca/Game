package tw.edu.ntut.csie.game;

import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * Created by Guan-Hong on 2015/5/28.
 */
public class WarningShow implements GameObject {
    private MovingBitmap SunWarning ;
    private MovingBitmap LotusWarning ;
    private boolean SunFlag;
    private boolean LotusFlag;
    public int ContinueTime;
    public WarningShow(){
        SunWarning = new MovingBitmap(R.drawable.warningsun );
        LotusWarning = new MovingBitmap(R.drawable.warninglotus);
        SunWarning.setVisible( false );
        LotusWarning.setVisible( false );
        SunWarning.setLocation( 200 , 326 );
        LotusWarning.setLocation( 200 , 326 );
        SunFlag = false;
        LotusFlag = false;
        ContinueTime = 0 ;
    }
    @Override
    public void move() {

    }

    @Override
    public void release() {
        SunWarning.release();
        LotusWarning.release();
        SunWarning = null ;
        LotusWarning = null ;
    }

    @Override
    public void show() {
        if( LotusFlag ){
            LotusWarning.setVisible( true );
            LotusWarning.show();
            ContinueTime ++ ;
            if( ContinueTime > 3 * 10 ){
                ContinueTime = 0 ;
                LotusFlag = false ;
                LotusWarning.setVisible( false );
            }
        }
        if( SunFlag ){
            SunWarning.setVisible( true );
            SunWarning.show();
            ContinueTime ++ ;
            if( ContinueTime > 3 * 10 ){
                ContinueTime = 0 ;
                SunFlag = false ;
                SunWarning.setVisible( false );
            }
        }
    }
    //  1 : 太陽  ;   2 : 荷葉
    public void ChangeWarning( int type ){
        if( type == 1 ){
            if( LotusFlag == false ){
                SunFlag = true ;
                LotusFlag = false ;
                ContinueTime = 0 ;
            }
        }
        else if( type == 2 ){
            SunFlag = false ;
            LotusFlag = true ;
            ContinueTime = 0 ;
        }
    }
}

