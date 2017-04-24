

package tw.edu.ntut.csie.game;

import java.sql.Time;
import java.util.List;

import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.Bullet;
import tw.edu.ntut.csie.game.GameMap;
/**
 * Created by Guan-Hong on 2015/3/21.
 */

class Position {
    private int _X , _Y ;
    public Position(){
    }
    public int GetPositionX(){
        return _X ;
    }
    public int GetPositionY(){
        return _Y ;
    }
    public void SetPosition( int X , int Y ){
        _X = X ;
        _Y = Y ;
    }
}

public class Plants implements GameObject{
    private MovingBitmap Plant ;
    // 植物在地圖上的位置
    private Position OnMapPosition ;
    //  植物的種類   :   1 ( 太陽花 ) 、 2 ( 子彈植物 ) 、 3 ( 冰凍子彈植物 ) 、 4 ( 防禦馬鈴薯 ) 、   5 ( 地雷 )、6 ( 雙發子彈植物 )
    private int PlantType ;
    //  判斷植物是否活著    :    0 ( 植物活著 ) 、 1 ( 植物死掉 )
    private int PlantDead;
    private int HP ;
    //  製造植物所需的太陽
    private int cost;
    private Sun_plant _sun ;
    private Sun_mushroom _mushSun;
    //  子彈陣列
    private Bullet [] BullBox ;
    //  計算 子彈發射時間 (  EX  :   3秒發射一發子彈   )
    private int BulletTimes ;
    // 標記植物被咬
    private boolean isHurtFlag;
    private int flash_time ;
    private int isBombingFlag,bombing_flashtime;
    private int Isgrown; // 0 :小香菇  1: 大香菇
    private int growing; //計算香菇成長時間
    public int LevelState ;
    private Plants_Water WaterPlants ;
    //  建構子
    public Plants( int Level ){
        LevelState = Level ;
        WaterPlants = new Plants_Water();
    }
    // 創造植物 ( 還沒種植物 ， dead = 1  )
    public void CreatePlant( int X , int Y ){
        Plant = new MovingBitmap( R.drawable.flower1 ) ;
        //  預設 看不見
        Plant.setVisible( false );
        OnMapPosition = new Position();
        OnMapPosition.SetPosition( 150 + ( Y * 60 ) , 66 +  ( X * 60 ) ) ;
        PlantDead = 1 ;
        PlantType = 0 ;
        isBombingFlag = 0;
        Isgrown = 1;  // 一種植就開始長大
    }
    //  根據類型 ， 種植物
    public void GrowPlant( int type ){
        //  當植物死亡  ，  才給植物新種類
        //  避免種植水植物，發生錯誤
        if( PlantDead == 1 ) PlantType = type ;
        //  當格子上有荷葉  ，  必且種植的植物不為  荷葉
        if( LevelState == 2 && PlantType == 7 && type != 7 ){
            int [] Modify ;
            Modify = new int[3] ;
            WaterPlants.GrowPlant( type , HP , cost , Modify  );
            HP = Modify[0] ;
            cost = Modify[1] ;
            PlantType = Modify[2] ;
        }
        //  取得植物  血量  &&  屬性
        else{
            HP = GetHP( PlantType ) ;
            cost = GetCost( PlantType ) ;
        }
        //  載入圖片
        OriginalPlantPhoto();
        Plant.setVisible( true );
        PlantDead = 0 ;
        //  一開始植物，沒有被殭屍打到
        isHurtFlag = false ;
        PlantCapability();
    }
    void OriginalPlantPhoto(){
        if( PlantType == 1 ) Plant.loadBitmap( R.drawable.sun_flower );
        else if( PlantType == 2 ) Plant.loadBitmap( R.drawable.one_shoot_plant );
        else if( PlantType == 3 ) Plant.loadBitmap( R.drawable.snow_shoot_plant );
        else if( PlantType == 4 ) Plant.loadBitmap( R.drawable.potato_small );
        else if( PlantType == 5 ) Plant.loadBitmap( R.drawable.bomb_potato_red );
        else if( PlantType == 6 ) Plant.loadBitmap( R.drawable.double_shoot_plant );
        else if( PlantType == 7 && LevelState == 2 ) Plant.loadBitmap( R.drawable.lotus_leaf );
        else if( LevelState == 3 ){
            if( PlantType == 7 ){
                if( Isgrown == 1 ) Plant.loadBitmap( R.drawable.sun_mush );
                else if( Isgrown == 0 ) Plant.loadBitmap( R.drawable.grown_sun_mush );
            }
            else if( PlantType == 8 ) Plant.loadBitmap( R.drawable.small_shoot_mush );
        }
    }
    void PlantCapability(){
        if( PlantType == 1 )  _sun = new Sun_plant( OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() ) ;
        if( PlantType == 7 && LevelState == 3)  _mushSun = new Sun_mushroom( OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() );
            //  子彈植物，給予子彈
        else if( BulletPlant( PlantType ) ){
            //  子彈發射時間
            BulletTimes = 0 ;
            //  子彈有 8 發 ， 循環使用
            BullBox = new Bullet [ 8 ] ;
            int number_i;
            for( number_i = 0 ; number_i < 8 ; number_i ++ ){
                if( PlantType == 2 ) BullBox [ number_i ] = new Bullet( 1 , number_i , OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() ) ;
                else if( PlantType == 3 ) BullBox [ number_i ] = new Bullet( 2 , number_i , OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() ) ;
                else if( PlantType == 6 ) BullBox [ number_i ] = new Bullet( 3 , number_i , OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() ) ;
                else if( PlantType == 8 ) BullBox [ number_i ] = new Bullet( 4 , number_i , OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() ) ;
            }
        }
    }
    //  移除植物
    public void DigPlant( int Row , int Column ){
        //  當移除的是 水地圖 上 的植物  ，  且荷葉上有植物
        if( LevelState == 2 && Row > 0 && Row < 4 && PlantType > 0 && PlantType != 7 ){
            int [] Modify ;
            Modify = new int[2];
            WaterPlants.DigPlant( PlantType , HP , Modify );
            HP = Modify[0] ;
            PlantType = Modify[1] ;
            Plant.loadBitmap( R.drawable.lotus_leaf );
        }
        else{
            HP = 0 ;
            PlantDead = 1 ;
            isHurtFlag = false ;
            Plant.setVisible( false );
        }
    }
    public void release(){
        Plant.release();
        Plant = null;
        if( PlantType == 1 ) _sun.release();
        else if( BulletPlant( PlantType ) ){
            int number_i ;
            for( number_i = 0 ; number_i < 8 ; number_i ++ ) BullBox[ number_i ].release();
        }
        WaterPlants.release();
    }
    public void move(){
        //  如果是子彈植物 && 植物活著 ， 就可以移動子彈
        if( BulletPlant( PlantType ) ){
            if( PlantDead == 0 ){
                int number_i ;
                for( number_i = 0 ; number_i < 8 ; number_i ++ ) BullBox[ number_i ].move();
            }
            else if( PlantDead == 1 ){
                //  當植物死掉，子彈威力 改成 0
                int number_i ;
                for( number_i = 0 ; number_i < 8 ; number_i ++ ) BullBox[ number_i ].PlantDead();
            }
        }
    }
    //  計算時間 ， 時間一到就初始化子彈
    void InitialBullet(){
        BulletTimes ++ ;
        int number_i ;
        for( number_i = 0 ; number_i < 8 ; number_i ++ ) {
            if ( BulletTimes >= BullBox[number_i].GetBulletShootingTime() ) {
                //  發射中的子彈，不需初始化
                if( BullBox[number_i].GetShootingState() == 0 ) BullBox[number_i].ShootingBullet();
            }
        }
        //  當子彈發射一輪過後，重新循環使用
        if( BullBox[7].GetShootingState() == 2 ){
            BulletTimes = 0 ;
            for( number_i = 0 ; number_i < 8 ; number_i ++ ){
                //  把子彈 改成 準備發射中
                BullBox[number_i].ChangeShootingState( 0 );
            }
        }
    }
    //  顯示水地圖的荷葉
    public void ShowLotus(){
        //if(  LevelState == 2 && PlantType > 0  && PlantType != 7 ) WaterPlants.ShowLotus( OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() , PlantDead );
        if( PlantType != 7 ) WaterPlants.ShowLotus( OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() , PlantDead );
    }
    //  顯示植物 、 植物發射的子彈
    public void show(){
        //  被殭屍攻擊
        if( isHurtFlag ){
            flash_time ++ ;
            if( flash_time >= 15 ){
                OriginalPlantPhoto();
                isHurtFlag = false ;
                flash_time = 0 ;
            }
        }
        Plant.setLocation( OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() );
        Plant.show();
        if( PlantType == 1){
            if( PlantDead ==0 ) _sun.show();
                //  當植物死掉，太陽停止生產
            else if( PlantDead == 1 ) _sun.ChangeProducing( 0 );
        }
        if( LevelState == 3 && PlantType == 7){
            if( PlantDead ==0 ) _mushSun.show();
                //  當植物死掉，太陽停止生產
            else if( PlantDead == 1 ) _mushSun.ChangeProducing( 0 );
        }
        //  顯示子彈
        if( BulletPlant( PlantType ) ){
            int number_i ;
            if( PlantDead == 0 ){
                //  初始化子彈
                InitialBullet() ;
                for( number_i = 0 ; number_i < 8 ; number_i ++ ) BullBox[ number_i ].show();
            }
        }

        if(isBombingFlag ==1){
            bombing_flashtime++;

            if(bombing_flashtime >= 20){
                Plant.setVisible(false);
                isBombingFlag = 0;
                bombing_flashtime = 0;
            }
        }
        if(Isgrown == 1 && LevelState == 3 && PlantType ==7){
            growing++;
            if(growing >= 300){
                GrowMushroom();
            }
        }
    }
    //  判斷太陽花生產的太陽，手是否有點到
    public int TouchSun( int touchX , int touchY  ){
        if( PlantType == 1 && PlantDead == 0 ){
            if( _sun.IsProducing() ) return _sun.TouchSun( touchX , touchY ) ;
        }
        if( LevelState == 3 && PlantType == 7 && PlantDead == 0 ){
            if( _mushSun.IsProducing() ) return (_mushSun.TouchSun( touchX , touchY , Isgrown)) ;
        }
        return 0 ;
    }
    //  種植植物所需太陽花
    public int GetCost( int type ){
        if( type == 1 ) return 50 ;
        else if( type == 2 ) return 100 ;
        else if( type == 3 ) return 175 ;
        else if( type == 4 ) return 50 ;
        else if( type == 5 ) return 25 ;
        else if( type == 6 ) return 175;
        if( LevelState == 2 && type == 7 ) return 25 ;
        if( LevelState == 3 ){
            if( type == 7 ) return 15 ;
            else if( type == 8  ) return 0;
        }
        return 0 ;
    }
    public int GetHP( int type ){
        //  太陽花
        if( type == 1 ) return 5 ;
            //  子彈植物
        else if( type == 2 ) return 5 ;
            //  冰凍子彈植物
        else if( type == 3 ) return 5 ;
            //  防禦馬鈴薯
        else if( type == 4 ) return 20 ;
            //  地雷植物
        else if( type == 5 ) return 10 ;
            //  雙發子彈植物
        else if( type == 6 ) return 10;
            //  荷葉
        else if( LevelState == 2 && type == 7 ) return 5 ;
        else if( LevelState == 3 ){
            // Sun Mushroom
            if( type == 7 ) return 5 ;
            //  蘑菇子彈植物
            else if( type == 8 ) return 5 ;
        }
        return 0 ;
    }
    //  地雷爆炸
    void BombExplode( Zombies zombie , GameMap _gamemap , int X , int Y ){
        if( zombie.GetPositionX() >= Plant.getX() + 30 && ( zombie.GetPositionX() <= Plant.getX() + 45 )
                && zombie.GetPositionY() >= Plant.getY() && ( zombie.GetPositionY() <= Plant.getY() + 40 ) )
        {
            zombie.GetHurt( 10 ) ;
            if( LevelState == 2 && X > 0 && X < 4 ){
                HP -= 10 ;
                PlantType = 7 ;
                Plant.loadBitmap( R.drawable.lotus_leaf );
            }
            else{
                Bomb_changePhoto();

                HP = 0 ;
                PlantDead = 1 ;
                _gamemap.ErasePlantOnMap(X, Y);
            }
        }
    }
    private void Bomb_changePhoto(){
        if(isBombingFlag == 0){
            Plant.loadBitmap(R.drawable.bomb_effect);
            bombing_flashtime = 0;
            isBombingFlag = 1;
        }
    }
    boolean BulletPlant( int Type ){
        if( Type == 2 || Type == 3 || Type == 6 || Type == 8 ) return true;
        return false ;
    }
    boolean BombPlant( int Type ){
        if( Type == 5 ) return true;
        return false ;
    }
    //  子彈、炸彈攻擊殭屍
    public void Fire( Zombies [] zombie , int ZombieNumber , GameMap _gamemap , int X , int Y ){
        //  子彈植物、地雷植物 可以攻擊殭屍
        if( BulletPlant( PlantType ) || BombPlant( PlantType ) ){
            int number_i ;
            for( number_i = 0 ; number_i < ZombieNumber ; number_i ++ ){
                //  植物 & 殭屍 活著
                if( PlantDead == 0 && zombie[ number_i ].IsDead() == 0 ){
                    if( BulletPlant( PlantType ) ){
                        int number_j ;
                        for( number_j = 0 ; number_j < 8 ; number_j ++ ){
                            BullBox[ number_j ].BulletHitZombie( zombie[ number_i ] );
                        }
                    }
                    else if( BombPlant( PlantType ) ){
                        BombExplode( zombie[number_i] , _gamemap , X , Y );
                    }
                }
            }
        }
    }
    public void getHurt( int damage , int MapRow , int MapColumn ){
        //  植物被攻擊
        if( !isHurtFlag  ){
            HP = HP - damage ;
            hurtPlantChange(PlantType);
            //Plant.loadBitmap( R.drawable.apple ) ;
            isHurtFlag = true ;
            flash_time = 0 ;
        }
        if( LevelState == 2 && MapRow > 0 && MapRow < 4 && PlantType != 7 ){
            if( WaterPlants.OnLotusPlantIsDead( HP ) ){
                PlantType = 7 ;
                Plant.loadBitmap( R.drawable.lotus_leaf );
            }
        }
        //  當血 <= 0 植物死掉
        if( HP <= 0 ){
            HP = 0;
            PlantDead = 1 ;
            isHurtFlag = false ;
            Plant.setVisible( false );
        }
    }

    private void hurtPlantChange(int type){
        switch (type){
            case 1:// sunflower
                Plant.loadBitmap(R.drawable.attaked_sun_flower);
                break;
            case 2:// oneshoot
                Plant.loadBitmap(R.drawable.attaked_one_shoot_plant);
                break;
            case 3:
                Plant.loadBitmap(R.drawable.attaked_snow_shoot_plant);
                break;
            case 4:
                Plant.loadBitmap(R.drawable.attaked_potato_small);
                break;
            case 5:
                Plant.loadBitmap(R.drawable.attaked_bomb_potato);
                break;
            case 6:
                Plant.loadBitmap(R.drawable.attaked_double_shoot_plant);
                break;
            case 7:
                if(LevelState == 2 ) Plant.loadBitmap(R.drawable.attaked_lotus_leaf);
                if(LevelState == 3 ){
                    if( Isgrown == 1 ) Plant.loadBitmap( R.drawable.attacked_sun_mush );
                    else if( Isgrown == 0 ) Plant.loadBitmap( R.drawable.attacked_grown_sun_mush );
                }
            case 8 :
                if( LevelState == 3 ) Plant.loadBitmap( R.drawable.attacked_small_shoot_mush );
                break;
            default:
                break;
        }
    }

    public void deadPlantErase(GameMap _gamemap , int X , int Y){
        if( PlantDead == 1 || HP <= 0 ){
            _gamemap.ErasePlantOnMap( X , Y );
        }
    }

    private void GrowMushroom(){
        Isgrown = 0;
        Plant.loadBitmap(R.drawable.grown_sun_mush);
    }
}
