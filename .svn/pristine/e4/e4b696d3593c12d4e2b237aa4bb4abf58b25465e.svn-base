package tw.edu.ntut.csie.game;

import tw.edu.ntut.csie.game.core.MovingBitmap;
/**
 * Created by Guan-Hong on 2015/3/26.
 */

public class Bullet{
    //  子彈的種類   :   1 ( 一般子彈 )  、   2 ( 冰凍子彈 ) 、 3 ( 蘑菇子彈 - 毒氣 )
    private int BulletType ;
    // 子彈的傷害
    private int BulletDamage ;
    private MovingBitmap bull ;
    //  子彈的座標
    private int BulletPositionX , BulletPositionY ;
    //  植物的座標   :   用來設定子彈初始位置
    private int PlantPositionX , PlantPositionY ;
    //  射擊狀態    :   0 ( 準備發射 )  、   1 ( 發射中 )   、   2 ( 射擊完成 )
    private int ShootingState ;
    //  發射時間
    private int ShootingTime;
    //  發射距離
    private int distance ;
    public Bullet( int PlantType , int order , int PlantX , int PlantY ){
        //  PlantType : 植物種類 :   1 ( 一般植物 ) 、 2 ( 冰凍植物 ) 、 3 ( 雙發子彈植物 )
        //  子彈上膛
        ShootingState = 0 ;
        //  給予子彈不同的出現時間
        if( PlantType == 3 ){
            if( order == 0 ) ShootingTime = 0 ;
            else if( order == 1 ) ShootingTime = 5 ;
            else if( order == 2 ) ShootingTime = 35 ;
            else if( order == 3 ) ShootingTime = 40 ;
            else if( order == 4 ) ShootingTime = 70 ;
            else if( order == 5 ) ShootingTime = 75 ;
            else if( order == 6 ) ShootingTime = 105 ;
            else if( order == 7 ) ShootingTime = 110 ;
        }
        //  三秒射擊一次
        else ShootingTime = order * 10 * 3 ;

        if( PlantType == 1 || PlantType == 3 ){
            bull = new MovingBitmap(R.drawable.bullet);
            BulletDamage = 2 ;
            BulletType = 1 ;
        }
        else if( PlantType == 2 ){
            bull = new MovingBitmap(R.drawable.bullet_ice);
            BulletDamage = 1 ;
            BulletType = 2 ;
        }
        else if( PlantType == 4 ){
            bull = new MovingBitmap( R.drawable.mush_bullet ) ;
            BulletDamage = 1 ;
            distance = 120 ;
            BulletType = 3 ;
        }
        bull.setLocation( 0 , 0 );
        bull.setVisible( false );
        //  儲存植物位置
        PlantPositionX = PlantX ;
        PlantPositionY = PlantY ;
    }
    public void release(){
        bull.release();
        bull = null;
    }
    //  改變涉及狀態  :   0 ( 準備發射 ) 、 1 ( 發射中 ) 、 2 ( 發射完畢 )
    public void ChangeShootingState( int ChangeState ){
        ShootingState = ChangeState ;
    }
    //  準備發射子彈
    public void ShootingBullet(){
        ShootingState = 1 ;
        BulletPositionX = PlantPositionX + 35 ;
        BulletPositionY = PlantPositionY + 5 ;
        bull.setLocation( BulletPositionX , BulletPositionY );
        bull.setVisible( true );
    }
    public void ShootCompleted(){
        bull.setVisible( false );
        BulletPositionX = 0 ;
        BulletPositionY = 0 ;
        bull.setLocation( BulletPositionX , BulletPositionY );
        ShootingState = 2 ;

    }
    //  子彈移動   :   每一次  X  座標 都 + 10
    public void move(){
        if( BulletType == 3 && BulletPositionX <= 575 && ShootingState == 1 ){
            if( BulletPositionX >= PlantPositionX && BulletPositionX <= PlantPositionX + distance ){
                BulletPositionX += 10 ;
            }
        }
        //  當子彈 沒有超過邊界 、 並且發射中 ， 才需要移動子彈
        else if( BulletPositionX >= PlantPositionX && BulletPositionX <= 575 && ShootingState == 1 && BulletType != 3 ){
            BulletPositionX += 10 ;
        }
        if( BulletType == 3 && ShootingState == 1 ){
            if( BulletPositionX > 575 || BulletPositionX >= PlantPositionX + distance ) ShootCompleted();
        }
        //  當子彈超過地圖範圍 ， 完成發射子彈， 並改變狀態
        else if( BulletPositionX> 575 && ShootingState == 1 && BulletType != 3 ){
            ShootCompleted();
        }
        bull.setLocation( BulletPositionX , BulletPositionY );
    }
    public void show(){
        if( ShootingState == 1 ){
            bull.setLocation( BulletPositionX , BulletPositionY );
            bull.show();
        }
    }
    //  植物屎掉
    public void PlantDead(){
        BulletDamage = 0 ;
        bull.setVisible( false );
        ShootingState = 2 ;
    }
    //  回傳子彈威力
    public int GetBulletDamage(){
        return BulletDamage ;
    }
    public int GetBulletX(){
        return BulletPositionX;
    }
    public int GetBulletY(){
        return BulletPositionY;
    }
    //  回傳 發射狀態
    public int GetShootingState(){
        return ShootingState ;
    }
    //  回傳每個子彈的 發射時間
    public int GetBulletShootingTime(){
        return ShootingTime ;
    }
    //  減低速度
    public int SlowZombieSpeed(){
        if( BulletType == 2 ){
            return 1 ;
        }
        return 0 ;
    }
    //  子彈攻擊殭屍
    public void BulletHitZombie( Zombies zombie ){
        if( zombie.CanHit() )
        {
            if( zombie.GetPositionX() >= ( BulletPositionX - 30 ) && ( zombie.GetPositionX() <= BulletPositionX + bull.getWidth() - 10 )
                    && zombie.GetPositionY() >= BulletPositionY -5 && ( zombie.GetPositionY() <= BulletPositionY + bull.getHeight() ) )
            {
                if( ShootingState == 1 ){
                    //  如果有打到殭屍，殭屍扣血 && 子彈完成發射
                    zombie.GetHurt( BulletDamage , SlowZombieSpeed() );
                    ShootCompleted();
                }
            }
        }
    }
}
