package tw.edu.ntut.csie.game;

import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * Created by Guan-Hong on 2015/5/28.
 */
public class GameMap_Dark implements GameObject {
    private static final int MapRow = 5 ;
    private static final int MapColumn = 8 ;
    private final int BeginX = 150 , BeginY = 66 ;
    private final int BlockWidth = 60 , BlockHeight = 60 ;
    private int [][] tombSpace ;
    MovingBitmap[] tombPhoto ;
    public GameMap_Dark( int [][] space ){
        tombSpace = new int[5][2];
        tombPhoto = new MovingBitmap[5] ;
        //  low到high亂數（含high）
        //  (int) (Math.random() * (high - low + 1) + low)
        int number_i ;
        int BlockX , BlockY ;
        for( number_i = 0 ; number_i < 5 ; number_i ++ ){
            BlockX = (int)( Math.random() * ( 7-4+1 ) + 4 ) ;
            BlockY = (int)( Math.random() * ( 4-0+1 ) + 0 ) ;
            tombSpace[ number_i ][0] = BlockX ;
            tombSpace[ number_i ][1] = BlockY ;
            space[ BlockY ][ BlockX ] = -1 ;
            tombPhoto[number_i] = new MovingBitmap( R.drawable.tomb_ ) ;
            tombPhoto[number_i].setLocation( 150 + BlockX * 60, 66 + BlockY * 60 );
        }
    }

    @Override
    public void release() {
        int number_i ;
        for( number_i = 0 ; number_i < 5 ; number_i ++ ){
            tombPhoto[number_i].release();
            tombPhoto[number_i] = null ;
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void show() {
        int number_i ;
        for( number_i = 0 ; number_i < 5 ; number_i++ ){
            tombPhoto[number_i].show();
        }
    }
    public int CanGrow_or_CanDig( int TouchX , int TouchY ){
        int number_i ;
        int number_j ;
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn ; number_j++ ){
                if( TouchX > BeginX + number_j * BlockWidth && TouchX < BeginX + (number_j+1) * BlockWidth &&
                        TouchY > BeginY + number_i * BlockHeight &&  TouchY < BeginY + (number_i+1) * BlockHeight )
                {
                    int tmp ;
                    for( tmp = 0 ; tmp < 5 ; tmp ++ ){
                        if( tombSpace[tmp][0] == number_i && tombSpace[tmp][1] == number_j ) return 0 ;
                    }
                }
            }
        }
        return 1;
    }
    public int [][] getTombSpace(){
        return tombSpace;
    }
}

