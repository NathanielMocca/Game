package tw.edu.ntut.csie.game;

import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * Created by Guan-Hong on 2015/3/21.
 */
public class GameMap implements GameObject{
    private MovingBitmap Grass1,Grass2 ;
    private MovingBitmap Water,Water2;
    private MovingBitmap BackGround ;
    //  除草機
    private MovingBitmap [] Weeder ;
    //  啟動除草機   :   false ( 沒有啟動 ) 、 true ( 啟動 )
    private boolean [] WeederRun ;
    private int [][] map ;
    private static final int MapRow = 5 ;
    private static final int MapColumn = 8 ;
    //  space[][]，儲存是否有植物   : 0 ( 沒有 )  ;   > = 1 ( 有 )
    private int [][] space ;
    // 地圖從左上角座標 ( 150 , 66 ) 開始建置
    private final int BeginX = 150 , BeginY = 66 ;
    // 每一格地圖大小  60 * 60
    private final int BlockWidth = 60 , BlockHeight = 60 ;
    //  水地圖
    private GameMap_Water WaterMap ;
    private GameMap_Dark DarkMap;
    //  關卡狀態    :   1 ( 一般地圖 )  、   2 ( 水地圖 )、 3 ( 暗地圖 )
    private int LevelState ;

    public GameMap( int Level ){
        //  關卡狀態    :   1 ( 一般太陽 )  、   2 ( 水地圖 )、 3 ( 暗地圖 )
        LevelState = Level ;
        if( LevelState == 3 ){
            BackGround = new MovingBitmap( R.drawable.back_night ) ;
            Grass1 = new MovingBitmap( R.drawable.grass1_night ) ;
            Grass2 = new MovingBitmap( R.drawable.grass2_night ) ;
        }
        else{
            BackGround = new MovingBitmap( R.drawable.back ) ;
            Grass1 = new MovingBitmap( R.drawable.grass1 ) ;
            Grass2 = new MovingBitmap( R.drawable.grass2 ) ;
        }

        Water = new MovingBitmap( R.drawable.water );
        Water2 = new MovingBitmap( R.drawable.water2 );
        // 建造一個 5 * 8 地圖
        map = new int[MapRow][MapColumn] ;
        // 建造一個空間，儲存是否有植物
        space = new int [MapRow][MapColumn] ;
        int number_i , number_j ;
        for( number_i = 0; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn  ; number_j++ ){
                //  給予不同顏色的土地
                if( number_i % 2 == 0 && number_j % 2 == 0 ) map[number_i][number_j] = 1 ;
                else if( number_i % 2 == 0 && number_j % 2 == 1 ) map[number_i][number_j] = 2 ;
                else if( number_i % 2 == 1 && number_j % 2 == 0 ) map[number_i][number_j] = 2 ;
                else if( number_i % 2 == 1 && number_j % 2 == 1 ) map[number_i][number_j] = 1 ;
                //  空間預設為0
                space[number_i][number_j] = 0 ;
            }
        }
        //  建造 5 台 除草機
        Weeder = new MovingBitmap[ MapRow ] ;
        WeederRun = new boolean[MapRow] ;
        for( number_i = 0 ; number_i < MapRow ; number_i ++ ) {
            Weeder[number_i] = new MovingBitmap(R.drawable.weeder);
            Weeder[number_i].setLocation( 90, 66 + number_i * 60 );
            WeederRun[number_i] = false;
        }
        //  水地圖
        WaterMap = new GameMap_Water() ;
        if( LevelState == 2 ){
            WaterMap.ChangeMap( map );
            WaterMap.ChangeWeeder( Weeder );
        }
        else if( LevelState == 3 ){
            DarkMap = new GameMap_Dark( space ) ;
        }
    }
    public void release(){
        BackGround.release();
        Grass1.release() ;
        Grass2.release() ;
        Water.release();
        Water2.release();
        BackGround = null ;
        Grass1 = null ;
        Grass2 = null ;
        Water = null ;
        Water2 = null ;
        int number_i ;
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            Weeder[number_i].release();
            Weeder[number_i] = null ;
        }
        if( LevelState == 3 ) DarkMap.release();
    }
    @Override
    public void move() {
        int number_i ;
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            //  除草機啟動
            if( WeederRun[number_i] ){
                //  除草機在範圍內，移動
                if( Weeder[ number_i ].getX() < 570 && Weeder[number_i].getY() != 0 ){
                Weeder[ number_i ].setLocation( Weeder[ number_i ].getX() + 15 , Weeder[ number_i ].getY() );
                }
                //  範圍外，停止移動
                else {
                    Weeder[ number_i ].setVisible( false );
                    Weeder[ number_i ].setLocation( 0 , 0 );
                    WeederRun[ number_i ] = false ;
                }
            }
        }
    }
    //  顯示地圖
    public void show(){
        BackGround.show();
        int number_i , number_j ;
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn  ; number_j++ ){
                switch (map[number_i][number_j]) {
                    case 1:
                        Grass1.setLocation(BeginX + number_j * BlockWidth, BeginY + number_i * BlockHeight) ;
                        Grass1.show() ;
                        break;
                    case 2:
                        Grass2.setLocation(BeginX + number_j * BlockWidth, BeginY + number_i * BlockHeight) ;
                        Grass2.show() ;
                        break;
                    case 3:
                        Water.setLocation(BeginX + number_j * BlockWidth, BeginY + number_i * BlockHeight) ;
                        Water.show() ;
                        break;
                    case 4 :
                        Water2.setLocation(BeginX + number_j * BlockWidth, BeginY + number_i * BlockHeight) ;
                        Water2.show() ;
                        break;
                    default:
                        break;
                }
            }
        }
        if( LevelState == 3 ) DarkMap.show();
        for( number_i = 0 ; number_i < MapRow ; number_i ++ ){
            Weeder[ number_i ].show();
        }
    }
    //  判斷手觸碰的位置，是否在地圖裡 :   0 ( 不在地圖裡 ) ;   1 ( 在地圖裡 )
    public int InRange( int TouchX , int TouchY ){
        if( TouchX > BeginX && TouchX < 630 && TouchY > BeginY && TouchY < 366 ) return  1 ;
        else return 0 ;
    }
    //  判斷那格是否可以種植植物    :   1 ( 可以種 )   ;   0 ( 不行種 )
    public int CanGrow( int TouchX , int TouchY , int [] BlockPosition ){
        int number_i ;
        int number_j ;
        if( LevelState == 3 ){
            if( DarkMap.CanGrow_or_CanDig( TouchX , TouchY ) == 0 ) return 0 ;
        }
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn ; number_j++ ){
                if( TouchX > BeginX + number_j * BlockWidth && TouchX < BeginX + (number_j+1) * BlockWidth &&
                    TouchY > BeginY + number_i * BlockHeight &&  TouchY < BeginY + (number_i+1) * BlockHeight )
                {
                    if( space[ number_i ][ number_j ] == 0 ){
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
    //  水地圖種植植物
    public int WaterSpaceCanGrow( int TouchX , int TouchY , int [] BlockPosition , int TouchPlant , WarningShow _Warning){
        return WaterMap.CanGrow(  TouchX ,  TouchY , BlockPosition ,  space , TouchPlant , _Warning) ;
    }
    public void ChangeSpace( int X , int Y , int feature ){
        //  水地圖更改空間屬性
        if( LevelState == 2 ){
            WaterMap.ChangeSpace( X , Y , feature , space );
        }
        else space[X][Y] = feature ;
    }
    public void ErasePlantOnMap( int X , int Y ){
        space[X][Y] = 0 ;
    }
    //  判斷那格是否可以移除植物    :   1 ( 可以移除 )   ;   0 ( 不行移除 )
    public int CanDig( int TouchX , int TouchY , int [] BlockPosition ){
        int number_i ;
        int number_j ;
        if( LevelState == 3 ){
            if( DarkMap.CanGrow_or_CanDig( TouchX , TouchY ) == 0 ) return 0 ;
        }
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn ; number_j++ ){
                if( TouchX > BeginX + number_j * BlockWidth && TouchX < BeginX + (number_j+1) * BlockWidth &&
                        TouchY > BeginY + number_i * BlockHeight &&  TouchY < BeginY + (number_i+1) * BlockHeight )
                {
                    if( space[ number_i ][ number_j ] >= 1 ){
                        // 把空間改為 0
                        space[number_i][number_j] = 0 ;
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
    //  水地圖移除植物
    public int WaterSpaceCanDig( int TouchX , int TouchY , int [] BlockPosition ){
        return WaterMap.CanDig( TouchX , TouchY , BlockPosition , space ) ;
    }
    public int PlantInSpace( int  X , int Y ){
        return space[X][Y];
    }
    //  殭屍是否抵達邊界
    public boolean ZombieArriveBorder( Zombies [] _zombies , int NumberOfZombies ){
        int number_i , number_j ;
        for( number_i = 0 ; number_i < NumberOfZombies ; number_i++ ){
            if( _zombies[number_i].IsDead() == 1 ) continue;
            if( _zombies[number_i].GetPositionX() < BeginX - 60 ) return true;
            if( _zombies[number_i].GetPositionX() < BeginX - 15 ){
                for( number_j = 0 ; number_j < MapRow ; number_j++ ){
                    if( _zombies[number_i].GetPositionY() >= BeginY + BlockHeight * number_j
                        && _zombies[number_i].GetPositionY() < BeginY + BlockHeight * number_j + 30)
                    {
                        WeederRun[number_j] = true ;
                    }
                }
            }
        }
        return false ;
    }
    //  除草機攻擊殭屍
    public void WeederAttackZombies( Zombies [] _zombies , int NumberOfZombies ){
        int number_i , number_j ;
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < NumberOfZombies ; number_j++ ){
                if( _zombies[number_j].IsDead() == 1 ) continue;
                if( _zombies[number_j].GetPositionX() <= Weeder[number_i].getX() + 40 && _zombies[number_j].GetPositionX() + 60 >= Weeder[number_i].getX() + 40
                    && Weeder[number_i].getY() <= _zombies[number_j].GetPositionY() && _zombies[number_j].GetPositionY() <= Weeder[number_i].getY() + 40)
                {
                    _zombies[number_j].GetHurt( 100 );
                }
            }
        }
    }
    public int [][] getTombSpace(){
        return DarkMap.getTombSpace();
    }
}