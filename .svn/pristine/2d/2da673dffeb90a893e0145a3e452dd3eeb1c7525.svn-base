package tw.edu.ntut.csie.game.state;

import java.util.Map;

import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.engine.GameEngine;
import tw.edu.ntut.csie.game.extend.BitmapButton;
import tw.edu.ntut.csie.game.extend.ButtonEventHandler;

/**
 * Created by Guan-Hong on 2015/5/1.
 */
public class ReadyInterface_1 extends AbstractGameState {
    private BitmapButton _NextLevelButton;

    public ReadyInterface_1(GameEngine engine) {
        super(engine);
    }

    @Override
    public void initialize(Map<String, Object> data) {
        addGameObject(new MovingBitmap(R.drawable.gamestart_pz));
        _NextLevelButton = new BitmapButton(R.drawable.labelnext, R.drawable.labelnextpressed, 465, 320);
        _NextLevelButton.addButtonEventHandler(new ButtonEventHandler() {
            @Override
            public void perform(BitmapButton button) {
                changeState(Game.RUNNING_STATE);
            }
        });
        addGameObject(_NextLevelButton);
        addPointerEventHandler(_NextLevelButton);
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
