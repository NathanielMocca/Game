package tw.edu.ntut.csie.game;

import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * Created by Guan-Hong on 2015/4/7.
 */

class ZombiesPosition {
    private int _X , _Y ;
    public ZombiesPosition(){

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
    public void RandomSelectRow(){
        //  low到high亂數（含high）
        //  (int) (Math.random() * (high - low + 1) + low)
        int BlockY;
        BlockY = (int)( Math.random() * ( 4-0+1 ) + 0 ) ;
        SetPosition(625, 66 + BlockY * 60) ;
    }
}

public class Zombies implements GameObject {
    private MovingBitmap _zombie;
    private ZombiesPosition OnMapPosition;
    private int HP;
    private int ZombieType ;
    private int damage ;
    private boolean appearOnMap ;
    private int ZombieDead;
    private int ZombieSpeed;
    // 用來記錄 跳高殭屍是否跳躍過一次  0: 還沒跳  1: 跳過了
    private int jumpOnce;
    //  緩速 ( 被冰凍植物打到 )
    private int SlowSpeed ;
    //  殭屍出現的時間
    private int ZombieAppearTime;
    //  標記被打到的殭屍  0 : 沒被打  1 : 被打中
    private int attackedFlag;
    // 標記咬植物 0 :沒有在攻擊  1 : 攻擊中
    private int biteFlag;
    // 標記 跳躍中 0 : 走路中    1 :跳躍中
    private int jumpFlag;
    //  閃爍延遲次數
    private int attacked_flash_time ,bite_flash_time ,jump_flash_time;
    //  殭屍 多久 走路一次
    private int WalkTimes ;
    //  前方是否有植物
    private int MeetPlants;
    private int LevelState ;

    public Zombies( int number_i , int Level ){
        LevelState = Level ;
        _zombie = new MovingBitmap( R.drawable.zombie );
        _zombie.setVisible( false );
        jumpOnce = 0;
        CreateZombies( number_i );
        if( LevelState == 2 ){
            if( OnMapPosition.GetPositionY() >= 126 && OnMapPosition.GetPositionY() <= 246 ){
                if( ZombieType == 1 || ZombieType == 2 ) _zombie.loadBitmap( R.drawable.swim_zombie );
            }
        }
    }
    void initial( int type ){
        if( type == 1 ){ //normal
            HP = 15 ;
            damage = 1 ;
            ZombieSpeed = 1 ;
            if( LevelState == 1 ) _zombie.loadBitmap(R.drawable.zombie);
        }
        else if( type == 2 ){ // cap
            HP = 20 ;
            damage = 1 ;
            ZombieSpeed = 1 ;
            if( LevelState == 1 ) _zombie.loadBitmap(R.drawable.cap_zombie);
        }
        else if( type == 3 ){ // jump
            HP = 25 ;
            damage = 1 ;
            ZombieSpeed = 2 ;
            _zombie.loadBitmap(R.drawable.b_jumpzombie); // before jump
        }
    }
    public void ZombieList( int number_i ){
        int type ;
        //  前10隻為一般殭屍

        if( number_i < 10 ){
            type = (int)( Math.random() * ( 2-1+1 ) + 1 ) ;
        }
        else type = (int)( Math.random() * ( 3-1+1 ) + 1 ) ;

        //type = 3;// 測試跳高殭屍
        ZombieType = type ;
        initial( ZombieType );
        ZombieDead = 0 ;
        appearOnMap = false ;
        SlowSpeed = 0 ;
        //  遇到植物狀態 false
        MeetPlants = 0 ;
        //  殭屍被攻擊狀態 false
        attackedFlag = 0 ;
        //  殭屍打植物狀態 false
        biteFlag = 0 ;
        //  殭屍被植物攻擊更換圖片的延遲時間
        attacked_flash_time = 0 ;
        //  殭屍攻擊植物更換圖片的延遲時間
        bite_flash_time = 0;
        WalkTimes = 0 ;
    }
    public void CreateZombies( int  number_i ){
        OnMapPosition = new ZombiesPosition() ;
        //  選擇出現的列
        OnMapPosition.RandomSelectRow();
        //  選擇殭屍種類
        ZombieList(number_i);
        if( number_i >= 0 && number_i < 10 ){
            //  第一隻 30秒 出現 ，之後每隔 10 秒出現一次
            ZombieAppearTime = 300 + number_i * 100 ;
        }
        else if( number_i >= 10 && number_i < 20 ){
            //  最後一隻 130秒出現，隔 10 秒開始出現一波殭屍，每隔 5 秒 出現一次
            ZombieAppearTime = 1400 + ( number_i - 10 ) * 50 ;
        }
        else if( number_i >= 20 && number_i < 30 ){
            //  最後一隻 190秒出現，隔 10秒開始出現零星殭屍，每隔 10秒 出現一次
            ZombieAppearTime = 2000 + ( number_i - 20 ) * 10 ;
        }
        else if( number_i >= 30 && number_i < 40 ){
            //  最後一隻 280秒出現，隔 15 秒開始出現零星殭屍，每隔 4秒 出現一次
            ZombieAppearTime = 2950 + ( number_i - 30 ) * 4 ;
        }
    }
    public void release(){
        _zombie.release();
        _zombie = null ;
    }
    public void move(){
        //  殭屍活著，每次移動  speed，0.2秒動一次
        WalkTimes ++ ;
        if( ZombieDead == 0 && WalkTimes >= 2 && MeetPlants == 0 ){
            _zombie.setVisible( true );
            appearOnMap = true ;
            OnMapPosition.SetPosition(OnMapPosition.GetPositionX() - (ZombieSpeed - SlowSpeed), OnMapPosition.GetPositionY());
            _zombie.setLocation( OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() );
            WalkTimes = 0 ;
            if( LevelState == 2 && attackedFlag == 0
                && OnMapPosition.GetPositionY() >= 126 && OnMapPosition.GetPositionY() <= 246 )
            {
                if( OnMapPosition.GetPositionX() < 590 ) {
                    if (ZombieType == 3) _zombie.loadBitmap(R.drawable.dolphin_zombie_underwater); // 殭屍with海豚
                    else _zombie.loadBitmap(R.drawable.swim_zombie_water); // 游泳殭屍
                }
                    else {
                        if(ZombieType == 3 )_zombie.loadBitmap(R.drawable.dolphin_zombie); // 殭屍with 海豚 陸上
                        else _zombie.loadBitmap(R.drawable.swim_zombie); // 游泳殭屍 陸上
                }
            }
        }
    }
    public void show(){
        if( attackedFlag == 1 ){
            attacked_flash_time ++ ;
            //  殭屍被冰凍子彈打到，緩速1.5秒
            if( SlowSpeed > 0 ){
                if( attacked_flash_time >= 15 ){
                    if(ZombieType ==1 ){_zombie.loadBitmap( R.drawable.zombie ) ;}
                    else if(ZombieType ==2){_zombie.loadBitmap( R.drawable.cap_zombie ) ;}
                    else if(ZombieType ==3)
                    {
                        if(jumpOnce == 0){_zombie.loadBitmap(R.drawable.b_jumpzombie);}
                        else{_zombie.loadBitmap(R.drawable.a_jumpzombie);}
                    }
                    attackedFlag = 0 ;
                    attacked_flash_time = 0 ;
                    SlowSpeed = 0 ;
                }
            }
            //  一般子彈打到
            else{
                if( attacked_flash_time >= 3 ){
                    if(ZombieType ==1 ){_zombie.loadBitmap( R.drawable.zombie ) ;}
                    else if(ZombieType ==2){_zombie.loadBitmap(R.drawable.cap_zombie);}
                    else if(ZombieType == 3){
                        if(jumpOnce == 0){_zombie.loadBitmap(R.drawable.b_jumpzombie);}
                        else{_zombie.loadBitmap(R.drawable.a_jumpzombie);}
                    }
                    attackedFlag = 0 ;
                    attacked_flash_time = 0 ;
                }
            }
        }
        // 殭屍攻擊植物
        if( biteFlag == 1 ){
            bite_flash_time ++;
            if( bite_flash_time >= 15 ){
                if(ZombieType ==1 ){
                    if( LevelState == 1 ) _zombie.loadBitmap( R.drawable.zombie ) ;
                }
                else if(ZombieType ==2){
                    if( LevelState == 1 ) _zombie.loadBitmap(R.drawable.cap_zombie);
                }
                else if(ZombieType == 3){
                    if(jumpOnce == 0){_zombie.loadBitmap(R.drawable.b_jumpzombie);}
                    else{_zombie.loadBitmap(R.drawable.a_jumpzombie);}
                }
                biteFlag = 0;
                bite_flash_time = 0;
            }
        }
        if( jumpFlag == 1){
            jump_flash_time ++;
            OnMapPosition.SetPosition(OnMapPosition.GetPositionX() - 4 , OnMapPosition.GetPositionY());
            _zombie.setLocation( OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() );
            if(jump_flash_time >= 15 ){
                _zombie.loadBitmap(R.drawable.a_jumpzombie);
                jumpFlag = 0;
                jump_flash_time = 0;
            }
        }
        _zombie.setLocation( OnMapPosition.GetPositionX() , OnMapPosition.GetPositionY() );
        _zombie.show();
    }
    //  回傳殭屍出現時間
    public int GetTimes(){
        return ZombieAppearTime ;
    }
    public int GetPositionX(){
        return OnMapPosition.GetPositionX();
    }
    public int GetPositionY(){
        return OnMapPosition.GetPositionY();
    }
    //  殭屍受到冰凍子彈傷害
    public void GetHurt( int damage , int Slow ){
        //  當殭屍受到傷害時，改變殭屍圖片
        HP = HP - damage ;
        if(ZombieType ==1 ){_zombie.loadBitmap( R.drawable.attacked_zombie) ;}
        else if(ZombieType == 2 ){_zombie.loadBitmap(R.drawable.attacked_capzombie);}
        else if(ZombieType == 3){
            if(jumpFlag == 1){_zombie.loadBitmap(R.drawable.d_jumpzombie);}
            else{_zombie.loadBitmap(R.drawable.attacked_zombie);}
        }
        if( Slow > 0 ) _zombie.loadBitmap( R.drawable.freezed_zombie01 );
        attackedFlag = 1 ;
        attacked_flash_time = 0 ;
        SlowSpeed = Slow ;

        //  當血 <= 0 殭屍死掉
        if( HP <= 0 ){
            HP = 0;
            ZombieDead = 1 ;
            _zombie.setVisible( false );
        }
    }
    //  殭屍受到一般子彈傷害
    public void GetHurt( int damage ){
        //  當殭屍受到傷害時，改變殭屍圖片
        HP = HP - damage ;
        if(ZombieType ==1 || ZombieType ==3 ){_zombie.loadBitmap( R.drawable.attacked_zombie) ;}
        else if(ZombieType ==2){_zombie.loadBitmap(R.drawable.attacked_capzombie);}
        attackedFlag = 1 ;
        attacked_flash_time = 0 ;

        if( HP <= 0 ){
            HP = 0;
            ZombieDead = 1 ;
            appearOnMap = false ;
            _zombie.setVisible( false );
        }
    }
    //  回傳 殭屍 是否活著
    public int IsDead(){
        return ZombieDead ;
    }

    public int ZombieMeetPlant ( GameMap _gamemap ){
        //  殭屍屎掉，不會遇到植物
        if( ZombieDead == 1 ) return 0 ;
        int number_i , number_j ;
        for( number_i = 0 ; number_i<5 ; number_i++ ) {
            for (number_j = 0; number_j < 8; number_j++) {
                //  如果格子有東西，就判斷殭屍是否有遇到 ( 炸彈例外 )
                if (_gamemap.PlantInSpace(number_i, number_j) >= 1 && _gamemap.PlantInSpace(number_i, number_j) != 5) {
                    if (OnMapPosition.GetPositionX() >= 150 + number_j * 60 + 25 && OnMapPosition.GetPositionX() <= 150 + (number_j + 1) * 60 - 20
                            && OnMapPosition.GetPositionY() >= 66 + number_i * 60 && OnMapPosition.GetPositionY() < 66 + number_i * 60 + 30) {
                        MeetPlants = 1;
                        if(ZombieType == 3 && jumpOnce == 0 ){ zombieJump(); }
                        return MeetPlants ;
                    }
                }
            }
        }
        //  沒有遇到任何植物
        MeetPlants = 0 ;
        return MeetPlants ;
    }

    public void Fire( Plants [][] _DataPlant , GameMap _gamemap ){
        int number_i , number_j ;
        if( MeetPlants == 1 && ZombieDead == 0 ){
            for( number_i = 0 ; number_i < 5 ; number_i ++ ){
                for( number_j = 0 ; number_j < 8 ; number_j ++ ){
                    if (_gamemap.PlantInSpace(number_i, number_j) >= 1 && _gamemap.PlantInSpace(number_i, number_j) != 5)
                    {
                        if( OnMapPosition.GetPositionX() >= 150 + number_j * 60 + 25 && OnMapPosition.GetPositionX() <= 150 + ( number_j + 1 ) * 60 - 20
                                && OnMapPosition.GetPositionY() >= 66 + number_i * 60 && OnMapPosition.GetPositionY() < 66 + number_i * 60 + 30 )
                        {
                            if( jumpFlag == 0 ){
                                _DataPlant[ number_i ][ number_j ].getHurt( damage , number_i , number_j );
                                zombieBitting();
                            }
                            _DataPlant[ number_i ][ number_j ].deadPlantErase(_gamemap , number_i , number_j); //  內有判斷該植物是否 HP =  0   若不等於0不會發動
                            break;
                        }
                    }
                }
            }
        }
    }

    private void zombieBitting(){
        if( biteFlag == 0 ){
            _zombie.loadBitmap(R.drawable.bitting_zombie01);
            bite_flash_time = 0;
            biteFlag = 1;
        }
    }
    private void zombieJump(){
        if( jumpFlag == 0){
            _zombie.loadBitmap(R.drawable.d_jumpzombie);  //during jump
            jump_flash_time = 0;
            jumpFlag = 1;
        }
        jumpOnce = 1 ;
    }
    public void setTombPosition( int positionX , int positionY ){
        OnMapPosition.SetPosition( positionX , positionY );
    }
    public boolean CanHit(){
        return appearOnMap ;
    }
}

