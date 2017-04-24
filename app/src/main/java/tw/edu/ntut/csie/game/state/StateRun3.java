package tw.edu.ntut.csie.game.state;

import java.util.List;
import java.util.Map;

import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.GameMap;
import tw.edu.ntut.csie.game.ListPlant;
import tw.edu.ntut.csie.game.Plants;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.Sun;
import tw.edu.ntut.csie.game.WarningShow;
import tw.edu.ntut.csie.game.Zombies;
import tw.edu.ntut.csie.game.core.Audio;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.engine.GameEngine;
import tw.edu.ntut.csie.game.extend.Animation;
import tw.edu.ntut.csie.game.extend.Integer;

/**
 * Created by Guan-Hong on 2015/5/2.
 */
public class StateRun3 extends GameState {
    //  太陽數量 預設 4 位數
    public static final int DEFAULT_SCORE_DIGITS = 4 ;
    //  宣告  一個太陽圖案 、 計算太陽數量
    private MovingBitmap _SunPhoto;
    private Integer _NumberOfSun;
    //  宣告一個地圖型態
    private GameMap _gameMap;
    private static final int MapRow = 5 ;
    private static final int MapColumn = 8 ;
    private MovingBitmap _android;
    private MovingBitmap _door;
    //  宣告一個圖片  :   鏟子
    private MovingBitmap _Shovel ;
    //  宣告植物清單 ( 管理所有植物種類 ) 、 植物陣列
    private ListPlant _TablePlant;
    private Plants [][] _DataPlant;
    public WarningShow _Warning;
    private Animation _flower;
    private boolean _grab;
    //  宣告 手是否有碰到花  :   有 ( 1，才能種植物 )   ;   沒有 ( 0，不能種植物 )
    private int _OnTouchPlant;
    //  宣告 手是否有碰到鏟子  :   有 ( 1，才能移除植物 )   ;   沒有 ( 0，不能移除植物 )
    private boolean _OnTouchShovel;
    // 宣告殭屍陣列
    private Zombies [] _zombies;
    private int NumberOfZombies ;
    //  判斷殭屍出現時間
    private int ZombieAppearTimes  ;
    private boolean GameOver ;
    private boolean NextLevel ;
    public static final int Level = 3 ;
    private int LevelState = 1 ;
    private Audio _music;
    private MovingBitmap _ZombieHead ;
    private Integer [] _RemainZombie ;

    public StateRun3(GameEngine engine) {
        super(engine);
    }

    @Override
    public void initialize(Map<String, Object> data) {
        //  new 地圖
        _gameMap = new GameMap( Level );
        _android = new MovingBitmap(R.drawable.zombie_head);
        _android.setLocation( 400 , 10 );
        _door = new MovingBitmap(R.drawable.door);
        _door.setLocation(580,0);
        _door.setVisible( true );
        //  太陽圖片
        _SunPhoto = new MovingBitmap(R.drawable.sun, 270 , 10 );
        //  太陽數量
        _NumberOfSun = new Integer(DEFAULT_SCORE_DIGITS, 50 * LevelState , 310 , 15 );
        _flower = new Animation();
        _flower.setLocation(575, 320);
        _flower.addFrame(R.drawable.flower1);
        _flower.addFrame(R.drawable.flower2);
        _flower.addFrame(R.drawable.flower3);
        _flower.addFrame(R.drawable.flower4);
        _flower.addFrame(R.drawable.flower5);
        _flower.setDelay(2);
        //  new 植物清單
        _TablePlant = new ListPlant( Level );
        // 植物陣列 new 40 個空間
        _DataPlant = new Plants [MapRow][MapColumn] ;
        int number_i , number_j ;
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn ; number_j++ ){
                _DataPlant[number_i][number_j] = new Plants( Level );
                _DataPlant[number_i][number_j].CreatePlant( number_i , number_j ) ;
            }
        }
        // new 鏟子
        _Shovel = new MovingBitmap( R.drawable.shovel1 );
        _Shovel.setLocation(160,5);

        // 創造殭屍
        NumberOfZombies = 10 ;
        NumberOfZombies *= LevelState ;
        _zombies = new Zombies[64] ;
        int [][] tombSpace ;
        tombSpace = new int[5][2] ;
        tombSpace = _gameMap.getTombSpace();
        for( number_i = 0 ; number_i< NumberOfZombies ; number_i++ ) {
            //  每個殭屍 傳入 number_i，給予不同時間
            _zombies[number_i] = new Zombies( number_i , Level );
            if( number_i >= 10 && number_i < 20 ) {
                _zombies[number_i].setTombPosition( 150 + 60 * tombSpace[ number_i%5 ][0] , 66 + 60 * tombSpace[ number_i%5 ][1] );
            }
            else if( number_i >=30 && number_i < 40 ){
                _zombies[number_i].setTombPosition( 150 + 60 * tombSpace[ number_i%5 ][0] , 66 + 60 * tombSpace[ number_i%5 ][1] );
            }
        }
        //  計算殭屍出現時間
        ZombieAppearTimes = 0 ;
        _music = new Audio(R.raw.you_make_me_feel_good);
        _music.setRepeating(true);
        _music.play();
        _grab = false;
        // 手是否有碰到花  :   有 ( 1，才能種植物 )   ;   沒有 ( 0，不能種植物 )
        _OnTouchPlant = 0 ;
        //  手是否碰到鏟子 :   有 ( 1，才能移除植物 )   ;   沒有 ( 0，不能移除植物 )
        _OnTouchShovel = false ;
        GameOver = false ;
        NextLevel = false ;
        _Warning = new WarningShow();
        _ZombieHead = new MovingBitmap( R.drawable.zombie_head );
        _ZombieHead.setLocation( 400 , 10 );
        _RemainZombie = new Integer[2] ;
        _RemainZombie[0] = new Integer( 2 , 0 , 460 , 15 ) ;
        _RemainZombie[1] = new Integer( 2 , 10 * LevelState , 500 , 15 ) ;
    }

    @Override
    public void move() {
        _flower.move();
        int number_i , number_j ;
        //  植物子彈的移動
        for( number_i = 0 ; number_i< MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn ; number_j++ ){
                _DataPlant[number_i][number_j].move();
                //  當子彈移動完之後，把 殭屍陣列 與 殭屍數量 傳入，判斷是否有擊中殭屍
                _DataPlant[number_i][number_j].Fire( _zombies , NumberOfZombies , _gameMap , number_i , number_j );
            }
        }
        //  判斷殭屍該出現的時間
        //  我們所計算的時間 ( times )  需   >=  殭屍出現的時間，殭屍才會開始移動
        ZombieAppearTimes ++ ;
        for( number_i = 0 ; number_i < NumberOfZombies ; number_i++ ) {
            if ( ZombieAppearTimes >= _zombies[number_i].GetTimes() ) {
                _zombies[number_i].move();
            }
        }
        //  判斷殭屍有沒有遇到植物
        for( number_i = 0 ; number_i< NumberOfZombies ; number_i++ ){
            _zombies[ number_i ].ZombieMeetPlant( _gameMap );
        }
        //  殭屍攻擊植物
        for( number_i = 0 ; number_i< NumberOfZombies ; number_i++ ){
            _zombies[ number_i ].Fire( _DataPlant , _gameMap );
        }
        GameOver = _gameMap.ZombieArriveBorder( _zombies , NumberOfZombies ) ;
        _gameMap.move();
        _gameMap.WeederAttackZombies( _zombies , NumberOfZombies );
        int AllDead = 0 ;
        for( number_i=0 ; number_i< NumberOfZombies ; number_i++ ){
            if( _zombies[number_i].IsDead() == 1 ) AllDead ++;
        }
        _RemainZombie[0].setValue( AllDead );
        if( AllDead == NumberOfZombies ) NextLevel = true ;
        if( NextLevel ){
            LevelState ++ ;
            if( LevelState == 5 ){
                LevelState = 1 ;
                changeState(Game.OVER_STATE);
            }
            else changeState( Game.Level_3_Ready );
        }
        if( GameOver ){
            LevelState = 1 ;
            changeState( Game.OVER_STATE );
        }
    }

    @Override
    public void show() {
        _gameMap.show();
        _ZombieHead.show();
        _RemainZombie[0].show();
        _RemainZombie[1].show();
        _SunPhoto.show();
        _NumberOfSun.show();
        _flower.show();
        _door.show();
        _android.show();
        _TablePlant.show();
        _Shovel.show();
        int number_i , number_j ;
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn ; number_j++ ){
                _DataPlant[number_i][number_j].show();
            }
        }
        for( number_i = 0 ; number_i < NumberOfZombies ; number_i++ ){
            _zombies[number_i].show();
        }
        _Warning.show();
    }

    @Override
    public void release() {
        _ZombieHead.release();
        _RemainZombie[0].release();
        _RemainZombie[1].release();
        _gameMap.release();
        _SunPhoto.release();
        _NumberOfSun.release();
        _android.release();
        _flower.release();
        _music.release();
        _door.release();
        _TablePlant.release();
        _Shovel.release();
        int number_i , number_j ;
        for( number_i = 0 ; number_i < MapRow ; number_i++ ){
            for( number_j = 0 ; number_j < MapColumn ; number_j++ ){
                _DataPlant[number_i][number_j].release();
            }
        }
        for( number_i = 0 ; number_i< NumberOfZombies ; number_i++ ){
            _zombies[number_i].release();
        }
        _Warning.release();

        _SunPhoto = null ;
        _NumberOfSun = null ;
        _android = null;
        _flower = null;
        _music = null;
        _door = null;
        _Shovel = null;
    }

    @Override
    public void keyPressed(int keyCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(int keyCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void orientationChanged(float pitch, float azimuth, float roll) {
    }

    @Override
    public void accelerationChanged(float dX, float dY, float dZ) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean pointerPressed(List<Pointer> pointers) {
        if (pointers.size() == 1) {
            int touchX = pointers.get(0).getX();
            int touchY = pointers.get(0).getY();
            if (touchX > _android.getX() && touchX < _android.getX() + _android.getWidth() &&
                    touchY > _android.getY() && touchY < _android.getY() + _android.getHeight()) {
                _grab = true;
            } else {
                _grab = false;
            }
            //  當手點的位置在地圖裡、地圖那一格沒有東西、太陽數量 >= 所花費的數量 ， 才能種植物
            if( _OnTouchPlant > 0 ){
                int [] BlockPosition = {0,0} ;
                int cost;
                cost = _DataPlant[ BlockPosition[0] ][ BlockPosition[1] ].GetCost( _OnTouchPlant ) ;
                if( _gameMap.InRange( touchX , touchY ) == 1 && _gameMap.CanGrow( touchX , touchY , BlockPosition ) == 1
                        && _NumberOfSun.getValue() >= cost ){
                    //  地圖空間改為 植物屬性
                    _gameMap.ChangeSpace( BlockPosition[0] , BlockPosition [1] , _OnTouchPlant );
                    // 種植物的時候，給植物屬性
                    _DataPlant[ BlockPosition[0] ][ BlockPosition[1] ].GrowPlant( _OnTouchPlant );
                    //  扣掉所花費的太陽
                    _NumberOfSun.subtract( cost );
                    //  冷卻時間
                    _TablePlant.InitialColdTime( _OnTouchPlant ) ;
                    _OnTouchPlant = 0 ;
                }
                else{
                    //  顯示警告，太陽數目不夠
                    //  那一格沒有職務才需判斷
                    if( _NumberOfSun.getValue() < cost ){
                        if( BlockPosition [0] > 0 && BlockPosition [0] < 4 )
                        {
                            if( _gameMap.PlantInSpace( BlockPosition[0] , BlockPosition [1]  ) == 7 ) _Warning.ChangeWarning( 1 );
                        }
                        else
                        {
                            if( _gameMap.PlantInSpace( BlockPosition[0] , BlockPosition [1]  ) == 0 ) _Warning.ChangeWarning( 1 ) ;
                        }
                    }
                }
            }
            //  如果手觸碰到植物的位置，回傳 _OnTouchPlant "  植物屬性  "，下次如果觸碰地圖，即可種植物
            _OnTouchPlant = _TablePlant.TouchPlant( touchX , touchY ) ;
            //  移除植物
            if( _OnTouchShovel){
                int [] BlockPosition = { 0 , 0 } ;
                if( _gameMap.InRange( touchX , touchY ) == 1 &&  _gameMap.CanDig( touchX , touchY , BlockPosition ) == 1 ){
                    _DataPlant[ BlockPosition[0] ][ BlockPosition[1] ].DigPlant( BlockPosition[0] , BlockPosition[1] );
                }
            }
            //  如果手觸碰到鏟子的位置，OnTouchShovel 改為 true ，下次如果觸碰植物，即可移除植物
            if (touchX > _Shovel.getX() && touchX < _Shovel.getX() + _Shovel.getWidth() &&
                    touchY > _Shovel.getY() && touchY < _Shovel.getY() + _Shovel.getHeight()) {
                _OnTouchShovel = true;
                _Shovel.loadBitmap( R.drawable.shovel2 );
            } else {
                _OnTouchShovel = false;
                _Shovel.loadBitmap( R.drawable.shovel1 );
            }
            //  撿拾太陽花的太陽
            int number_i,number_j,sunValue;
            for( number_i = 0 ; number_i < MapRow ; number_i ++ ){
                for( number_j = 0 ; number_j < MapColumn ; number_j ++ ){
                    sunValue = _DataPlant[ number_i ][ number_j ].TouchSun( touchX , touchY );
                    if( sunValue != 0) {
                        if(sunValue == 1) _NumberOfSun.add( 25 ); // Sunflower's  SUN
                        if(sunValue == 2) _NumberOfSun.add( 15 ); // Mushroom' s  SUN
                    }
                }
            }

        }
        return true;
    }

    @Override
    public boolean pointerMoved(List<Pointer> pointers) {
        if (_grab)
            _android.setLocation(pointers.get(0).getX() - _android.getWidth() / 2, pointers.get(0).getY() - _android.getHeight() / 2);
        int moveX = _android.getX();
        int moveY = _android.getY();
        if (moveX + _android.getWidth() / 2 > _door.getX() && moveX < _door.getX() + _door.getWidth() / 2 &&
                moveY + _android.getHeight() / 2 > _door.getY() && moveY < _door.getY() + _door.getHeight() / 2)
        {
            LevelState ++ ;
            if( LevelState == 5 ){
                LevelState = 1 ;
                changeState(Game.OVER_STATE);
            }
            else changeState( Game.Level_3_Ready );
        }
        return false;
    }

    @Override
    public boolean pointerReleased(List<Pointer> pointers) {
        _grab = false;
        return false;
    }

    @Override
    public void pause() {
        _music.pause();
    }

    @Override
    public void resume() {
        _music.resume();
    }
}
