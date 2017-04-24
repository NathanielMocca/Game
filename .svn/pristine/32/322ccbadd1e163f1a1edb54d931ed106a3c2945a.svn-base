package tw.edu.ntut.csie.game;

import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * Created by Guan-Hong on 2015/5/1.
 */

public class GameMap_Water implements GameObject{
    private static final int MapRow = 5 ;
    private static final int MapColumn = 8 ;
    private final int BeginX = 150 , BeginY = 66 ;
    private final int BlockWidth = 60 , BlockHeight = 60 ;
    public GameMap_Water(){

    }
    public void release(){

    }
    @Override
    public void move() {

    }
    public void show(){

    }
    //  更改圖片參數  ，   1 ~ 4 排 顯示水地圖
    public void ChangeMap( int [][] OriginalMap ){
        int number_i , number_j ;
        for( number_i = 1 ; number_i < MapRow - 1; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn ; number_j++ ){
                if( number_i % 2 == 0 && number_j % 2 == 0 ) OriginalMap[number_i][number_j] = 3 ;
                else if( number_i % 2 == 0 && number_j % 2 == 1 ) OriginalMap[number_i][number_j] = 4 ;
                else if( number_i % 2 == 1 && number_j % 2 == 0 ) OriginalMap[number_i][number_j] = 4 ;
                else if( number_i % 2 == 1 && number_j % 2 == 1 ) OriginalMap[number_i][number_j] = 3 ;
            }
        }
    }
    //  更改  水地圖  除草機 圖片
    public void ChangeWeeder( MovingBitmap [] Weeder ){
        int number_i;
        for( number_i = 1 ; number_i < MapRow -1 ; number_i++ ){
            Weeder[number_i].loadBitmap( R.drawable.weederwater );
        }
    }
    //  判斷水地圖 可否 種植植物
    public int CanGrow( int TouchX , int TouchY , int [] BlockPosition , int [][] space , int TouchPlant , WarningShow _Warning){
        int number_i , number_j ;
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn ; number_j++ ){
                if( TouchX > BeginX + number_j * BlockWidth && TouchX < BeginX + (number_j+1) * BlockWidth &&
                        TouchY > BeginY + number_i * BlockHeight &&  TouchY < BeginY + (number_i+1) * BlockHeight )
                {
                    //  種植在水地圖
                    int flag = 0 ;
                    if( number_i > 0 && number_i < MapRow -1 ){
                        //  格子上無任何東西 && 種植植物為荷葉
                        if( space[ number_i ][ number_j ] == 0 && TouchPlant == 7 ) flag = 1 ;
                        //  格子上有荷葉 && 種植植物不為荷葉
                        else if( space[ number_i ][ number_j ] == 7 && TouchPlant != 7 ) flag = 1 ;
                        else if( space[ number_i ][ number_j ] == 0 && TouchPlant > 0 ){
                            flag = 0 ;
                            _Warning.ChangeWarning( 2 );
                        }
                        else flag = 0 ;
                    }
                    //  種植在草地
                    else if( space[ number_i ][ number_j ] == 0 && TouchPlant != 7 ) flag = 1 ;
                    if( flag == 1 ){
                        // 回傳 格子 的位置 ex : ( 0 , 0 ) ( 0 , 1 ) ( 1 , 0 )
                        BlockPosition[0] = number_i ;
                        BlockPosition[1] = number_j ;
                        return 1 ;
                    }
                    else return 0 ;
                }
            }
        }
        return 0 ;
    }
    public void ChangeSpace( int X , int Y , int feature , int [][]space ){
        if( space[X][Y] == 7 ) space[X][Y] += feature ;
        else space[X][Y] = feature ;
    }
    public int CanDig( int TouchX , int TouchY , int [] BlockPosition , int [][] space ){
        int number_i ;
        int number_j ;
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn ; number_j++ ){
                if( TouchX > BeginX + number_j * BlockWidth && TouchX < BeginX + (number_j+1) * BlockWidth &&
                        TouchY > BeginY + number_i * BlockHeight &&  TouchY < BeginY + (number_i+1) * BlockHeight )
                {
                    //  剷除的格子有植物
                    if( space[ number_i ][ number_j ] >= 1 ){
                        //  剷除的位置在 水地圖 上  ，  若荷葉上有植物  ，  只剷除 荷葉上 的植物  ;  反之  ，  移除荷葉
                        if( number_i > 0 && number_i < MapRow -1 ){
                            if( space[number_i][number_j] == 7 ) space[number_i][number_j] = 0 ;
                            else space[number_i][number_j] = 7 ;
                        }
                        //  剷除位置在  草地  上
                        else{
                            // 把空間改為 0
                            space[number_i][number_j] = 0 ;
                        }
                        // 回傳 格子 的位置 ex : ( 0 , 0 ) ( 0 , 1 ) ( 1 , 0 )
                        BlockPosition[0] = number_i ;
                        BlockPosition[1] = number_j ;
                        return 1;
                    }
                    else return 0;
                }
            }
        }
        return 0;
    }
}