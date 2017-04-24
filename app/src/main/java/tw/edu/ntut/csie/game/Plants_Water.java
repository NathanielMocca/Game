package tw.edu.ntut.csie.game;

import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * Created by Guan-Hong on 2015/5/6.
 */
public class Plants_Water implements GameObject{
    private MovingBitmap LotusLeaves ;
    public Plants_Water(){
        LotusLeaves = new MovingBitmap( R.drawable.lotus_leaf ) ;
        LotusLeaves.setVisible( false );
    }
    public void GrowPlant( int Newtype , int HP , int cost , int [] Modify ){
        //  太陽花
        if( Newtype == 1 ){
            HP += 5 ;
            cost = 50 ;
        }
        //  子彈植物
        else if( Newtype == 2 ){
            HP += 5 ;
            cost = 100 ;
        }
        //  冰凍子彈植物
        else if( Newtype == 3 ){
            HP += 5 ;
            cost = 175 ;
        }
        //  防禦馬鈴薯
        else if( Newtype == 4 ){
            HP += 20 ;
            cost = 50 ;
        }
        //  地雷植物
        else if( Newtype == 5 ){
            HP += 10 ;
            cost = 25 ;
        }
        //  雙發子彈植物
        else if( Newtype == 6 ){
            HP += 10 ;
            cost = 175 ;
        }
        Modify[0] = HP ;
        Modify[1] = cost ;
        Modify[2] = Newtype ;
    }
    public void DigPlant( int PlantType , int HP , int [] Modify ){
        HP -= GetPlantHP( PlantType ) ;
        PlantType = 7 ;
        Modify[0] = HP ;
        Modify[1] = PlantType ;
        LotusLeaves.setVisible( false );
    }
    public int GetPlantHP( int PlantType ){
        if( PlantType == 1 ) return 5 ;
        else if( PlantType == 2 ) return 5 ;
        else if( PlantType == 3 ) return 5 ;
        else if( PlantType == 4 ) return 20 ;
        else if( PlantType == 5 ) return 10 ;
        else if( PlantType == 6 ) return 10 ;
        else if( PlantType == 7 ) return 5 ;
        return 0 ;
    }
    @Override
    public void release() {
        LotusLeaves.release();
        LotusLeaves = null ;
    }

    @Override
    public void move() {

    }

    @Override
    public void show() {

    }
    public void ShowLotus( int  PositionX , int PositionY , int PlantDead ){
        if( PlantDead == 0 ){
            LotusLeaves.setLocation( PositionX , PositionY );
            LotusLeaves.setVisible( true );
            LotusLeaves.show();
        }
        else if( PlantDead == 1 ) LotusLeaves.setVisible( false );
    }
    public boolean OnLotusPlantIsDead( int HP ){
        //  當現在的血量  -   荷葉血量  >  0    ，   代表上面的植物活著
        if( HP - 5 > 0 ) return false ;
        else{
            LotusLeaves.setVisible( false );
            return true ;
        }
    }
}
